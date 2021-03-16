package me.zhengjie.modules.system.rest.appRest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.system.domain.CheckTemplate;
import me.zhengjie.modules.system.domain.Unqualified;
import me.zhengjie.modules.system.service.UnqualifiedService;
import me.zhengjie.modules.system.service.dto.CheckTemplateDto;
import me.zhengjie.modules.system.service.dto.UnqualifiedDto;
import me.zhengjie.modules.system.util.FileNameUtils;
import me.zhengjie.modules.system.util.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Api(tags = "/unqualified管理")
@RequestMapping("/app/auth")
public class UnqualifiedAppController {
    @Value("${web.upload-path}")
    private String path;
    private final UnqualifiedService unqualifiedService;

    @RequestMapping("/selectAllUnqualified")
    public ResponseEntity<Object> query(String productName, String createBy, String faultName, String faultLevel,Long projectId,  Pageable pageable){
        return new ResponseEntity<>(unqualifiedService.findAlls(productName,createBy,faultName,faultLevel,projectId,pageable),HttpStatus.OK);
    }

    @RequestMapping("/findByCheckId")
    public List<Map> findByCheckId(Long checkId){
        return  unqualifiedService.findByCheckId(checkId);
    }

    @RequestMapping("/insetunqualified")
    public ResponseEntity<Object> create(@Validated @RequestBody JSONObject resources){
        Unqualified resources2 = JSON.parseObject(JSON.toJSONString(resources), Unqualified.class);
        UnqualifiedDto unqualifiedDto=unqualifiedService.create(resources2);
        for(Object path:resources.getJSONArray("pictures")){
            int one = path.toString().lastIndexOf("/");
            String name=path.toString().substring((one+1));
            unqualifiedService.InstAccessory(unqualifiedDto.getId(),name,path.toString(),new Date().getTime(),resources.get("createBy").toString());
        }
        return new ResponseEntity<>(unqualifiedDto, HttpStatus.CREATED);
    }
    @RequestMapping("/selectDict")
    public List<Map> selectDict(String description) {
        return unqualifiedService.selectDict(description);
    }

    @RequestMapping("/upload")
    public Map<String, Object> upload(MultipartFile file, String type){
        Map map = new HashMap();
        // 要上传的目标文件存放路径
        String localPath = path+type+"/";
        // 上传成功或者失败的提示
        String msg = "";
        String dbPath =  FileNameUtils.getFileName(file.getOriginalFilename());
        if (FileUtils.upload(file, localPath, dbPath)){
            // 上传成功，给出页面提示
            msg = "success";
        }else {
            msg = "failed";

        }
        // 显示图片
        //map.put("msg", msg);
        System.out.println("文件上传标识："+msg);
        map.put("dbPath",type+"/"+dbPath);
        return map;
    }
}
