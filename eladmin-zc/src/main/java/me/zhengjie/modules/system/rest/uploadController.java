package me.zhengjie.modules.system.rest;

import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.util.FileNameUtils;
import me.zhengjie.modules.system.util.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/upload")
public class uploadController {
    @Value("${web.upload-path}")
    private String path;
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
