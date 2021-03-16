package me.zhengjie.modules.system.util;

import io.swagger.annotations.ApiModelProperty;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ExcelUtil {
    public static final String XLS = ".xls";
    public static final String XLSX = ".xlsx";

    public <T> List<T> readExcelFileToDTO(MultipartFile file, Class<T> clazz) throws IOException {

        return readExcelFileToDTO(file, clazz, 0);
    }

    public <T> List<T> readExcelFileToDTO(MultipartFile file, Class<T> clazz, Integer sheetId) throws IOException {
        //将文件转成workbook类型
        Workbook workbook = buildWorkbook(file);
        //第一个表
        return readSheetToDTO(workbook.getSheetAt(sheetId), clazz);
    }

    public <T> List<T> readSheetToDTO(Sheet sheet, Class<T> clazz) throws IOException {
        List<T> result = new ArrayList<>();
        //读取表格数据
        List<Map<String, String>> sheetValue = changeSheetToMapList(sheet);
        for (Map<String, String> valueMap : sheetValue) {
            T dto = buildDTOByClass(clazz, valueMap);
            if (dto != null) {
                result.add(dto);
            }
        }
        return result;
    }

    //类型转换
    private Workbook buildWorkbook(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        if (filename.endsWith(XLS)) {
            return new HSSFWorkbook(file.getInputStream());
        } else if (filename.endsWith(XLSX)) {
            ZipSecureFile.setMinInflateRatio(-1.0d);
            return new XSSFWorkbook(file.getInputStream());
        } else {
            throw new IOException("unknown file format: " + filename);
        }
    }

    /**
     * 读取表格数据
     * @param sheet
     * @return
     */
    private List<Map<String, String>> changeSheetToMapList(Sheet sheet) {
        List<Map<String, String>> result = new ArrayList<>();
        int rowNumber = sheet.getPhysicalNumberOfRows();
        String[] titles = getSheetRowValues(sheet.getRow(0)); // 第一行作为表头
        for (int i = 1; i < rowNumber; i++) {
            String[] values = getSheetRowValues(sheet.getRow(i));
            Map<String, String> valueMap = new HashMap<>();
            int k=0;
            for (int j = 0; j < titles.length; j++) {
                try{
                    valueMap.put(titles[j], values[j]);
                }catch (Exception e){
                    k=1;
                    break;
                }
            }
            result.add(valueMap);
            if(k==1){
                break;
            }

        }
        return result;
    }

    /**
     *数据类型
     * @param clazz
     * @param valueMap
     * @param <T>
     * @return
     */
    private <T> T buildDTOByClass(Class<T> clazz, Map<String, String> valueMap) {
        try {
            T dto = clazz.newInstance();
                               //HomesDto
            for (Field field : clazz.getDeclaredFields()) {
                ApiModelProperty desc = field.getAnnotation(ApiModelProperty.class);
                String value;
                try{
                     value = valueMap.get(desc.value());
                }catch (Exception e){
                    continue;
                }
                if (value != null&&""!=value&&!("").equals(value)) {
                    if(field.getType().getName().equalsIgnoreCase("java.lang.Integer")) {
                        Method method = clazz.getMethod(getSetMethodName(field.getName()), field.getType());
                        Integer age = Integer.parseInt(value.substring(0,value.indexOf(".")));
                        method.invoke(dto, age);
                    }else if(field.getType().getName().equalsIgnoreCase("java.lang.Long")) {
                        Method method = clazz.getMethod(getSetMethodName(field.getName()), field.getType());
                        DecimalFormat df = new DecimalFormat("#");
                        String s = df.format(new BigDecimal(value));
                        Long age = Long.parseLong(s);
                        method.invoke(dto, age);
                    }else if(field.getType().getName().equalsIgnoreCase("java.lang.Double")) {

                        Method method = clazz.getMethod(getSetMethodName(field.getName()), field.getType());
                        Double age = Double.parseDouble(value.substring(0,value.indexOf(".")));
                        method.invoke(dto, age);
                    } else if(field.getType().getName().equalsIgnoreCase("java.util.Date")) {
                        Method method = clazz.getMethod(getSetMethodName(field.getName()), field.getType());
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date age = simpleDateFormat.parse(value.substring(0,value.indexOf(".")));
                        method.invoke(dto, age);
                    } else{
                        Method method = clazz.getMethod(getSetMethodName(field.getName()), field.getType());
                        method.invoke(dto, value);
                    }
                }else{
//                    Method method = clazz.getMethod(getSetMethodName(field.getName()), field.getType());
//                    method.invoke(dto, value);
                }
            }

            return dto ;
        } catch (Exception e) {
            e.getStackTrace();
            return null;
        }

    }

    private String getSetMethodName(String name) {
        String firstChar = name.substring(0, 1);
        return "set" + firstChar.toUpperCase() + name.substring(1);
    }

    private String[] getSheetRowValues(Row row) {
        if (row == null) {
            return new String[]{};
        } else {
            int cellNumber = row.getLastCellNum();
            List<String> cellValueList = new ArrayList<>();
            for (int i = 0; i < cellNumber; i++) {
                cellValueList.add(getValueOnCell(row.getCell(i)));
            }
            return cellValueList.toArray(new String[0]);
        }
    }

    private String getValueOnCell(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellTypeEnum()) {
            case STRING: return cell.getStringCellValue();
//            case NUMERIC: return String.format("%.2f", cell.getNumericCellValue());
            case NUMERIC: return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN: return cell.getBooleanCellValue() ? "true" : "false";
            case FORMULA:
                try {
                    return cell.getStringCellValue();
                } catch (Exception e) {
//                    return String.format("%.2f", cell.getNumericCellValue());
                    return String.valueOf(cell.getNumericCellValue());
                }
            default: return "";
        }
    }

//    public static void main(String[] args) {
//        int i = Integer.parseInt("12");
//        System.out.println(i);
//        Integer integer = Integer.valueOf("12");
//        System.out.println(integer);
//    }
}