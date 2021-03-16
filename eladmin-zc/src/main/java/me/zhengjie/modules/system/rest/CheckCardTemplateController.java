/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.modules.system.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.system.domain.CheckCardTemplate;
import me.zhengjie.modules.system.domain.CheckTemplate;
import me.zhengjie.modules.system.domain.Supplier;
import me.zhengjie.modules.system.service.CheckCardTemplateService;
import me.zhengjie.modules.system.service.CheckTemplateService;
import me.zhengjie.modules.system.service.dto.CheckCardTemplateQueryCriteria;
import me.zhengjie.modules.system.service.dto.CheckTemplateDto;
import me.zhengjie.modules.system.util.ExcelUtil;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author ly
* @date 2021-02-22
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "/checkCardTemplate管理")
@RequestMapping("/api/checkCardTemplate")
public class CheckCardTemplateController {

    private final CheckCardTemplateService checkCardTemplateService;
    private final CheckTemplateService checkTemplateService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('checkCardTemplate:list')")
    public void download(HttpServletResponse response, CheckCardTemplateQueryCriteria criteria) throws IOException {
        checkCardTemplateService.download(checkCardTemplateService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询/checkCardTemplate")
    @ApiOperation("查询/checkCardTemplate")
    @PreAuthorize("@el.check('checkCardTemplate:list')")
    public ResponseEntity<Object> query(CheckCardTemplateQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(checkCardTemplateService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增/checkCardTemplate")
    @ApiOperation("新增/checkCardTemplate")
    @PreAuthorize("@el.check('checkCardTemplate:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody CheckCardTemplate resources){
        return new ResponseEntity<>(checkCardTemplateService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改/checkCardTemplate")
    @ApiOperation("修改/checkCardTemplate")
    @PreAuthorize("@el.check('checkCardTemplate:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody CheckCardTemplate resources){
        checkCardTemplateService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除/checkCardTemplate")
    @ApiOperation("删除/checkCardTemplate")
    @PreAuthorize("@el.check('checkCardTemplate:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        checkCardTemplateService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @RequestMapping("/InstCheckCard")
    public Map InstCheckCard(Long id, Long type, Long projectId, String project, Long productId, String product, String name, Long createTime, String createBy) {
        Map  map=new HashMap();

       Long pid=checkCardTemplateService.selectCheckCardId(projectId,productId,type);
       if(pid!=null){
           map.put("success","该项目下该产品已存在卡片模板");
       }else {
           if (checkCardTemplateService.InstCheckCard(type, projectId, project, productId, product, name, createTime, createBy) == 1) {
               Long checkCardId = checkCardTemplateService.selectCheckCard(name);
               List<Map> CheckTemplateList = checkCardTemplateService.selectTemplateByPid(id);
               for (Map map1 : CheckTemplateList) {
                   CheckTemplateDto checkTemplateDto = JSON.parseObject(JSON.toJSONString(map1), CheckTemplateDto.class);
                   checkTemplateDto.setPid(checkCardId);
                   Integer s1 = checkCardTemplateService.InstCheckTemplate(checkTemplateDto.getPid(), checkTemplateDto.getNo(), checkTemplateDto.getProcess(), checkTemplateDto.getNotice(), checkTemplateDto.getAsk(), checkTemplateDto.getCheckMethod(), checkTemplateDto.getRemark());

               }
               map.put("success", 200);
           } else {
               map.put("success", "添加失败");

           }

       }
        return map;
    }
    @RequestMapping("/selectCheckTemplate")
    public List<Map> selectCheckTemplate(Long projectId, Long productId,Long type){
        Long pid=checkCardTemplateService.selectCheckCardId(projectId,productId,type);
        List<Map> CheckTemplateList=checkCardTemplateService.selectTemplateByPid(pid);
        return  CheckTemplateList;
    }
    @RequestMapping("/findCheckTemplate")
    public Map findCheckTemplate(Long id){
       Map map=checkCardTemplateService.findCheckTemplate(id);
        return  map;
    }
    @PostMapping(value = "/checkTemplateUpload")
    public Map uploadExcel(@ApiParam(value = "用户信息Excel导入数据", required = true) MultipartFile file,Long projectId,String project,Long productId,String product,String name,Long type,Long createTime,String createBy) throws Exception {
        Map  map =new HashMap();
        if (file == null) {
            map.put("errFlag","导入的表格不能为空");
            return map;
        }
        ExcelUtil excelUtil = new ExcelUtil();
        List<CheckTemplate> checkTemplateList = excelUtil.readExcelFileToDTO(file, CheckTemplate.class);
        System.out.println("长度：" + checkTemplateList.size());
        Long pid=checkCardTemplateService.selectCheckCardId(projectId,productId,type);
        if(pid==null) {
            if (checkCardTemplateService.InstCheckCard(type, projectId, project, productId, product, name, createTime, createBy) == 1) {
                pid=checkCardTemplateService.selectCheckCard(name);
            }
        }else{
            checkCardTemplateService.updateCheckCard(pid,type,projectId,project,productId,product,name,createTime,createBy);
        }
            //将pId加入到列表中
            for (CheckTemplate checkTemplate : checkTemplateList) {
                checkTemplate.setPid(pid);
            }
            //删除已经存在的模板
            List<Map> CheckTemplateList1 = checkCardTemplateService.selectTemplateByPid(pid);
            for (Map map1 : CheckTemplateList1) {
                CheckTemplateDto checkTemplateDto = JSON.parseObject(JSON.toJSONString(map1), CheckTemplateDto.class);
                checkCardTemplateService.deleteCheckTemplate(checkTemplateDto.getId());
            }

        map.put("success", checkCardTemplateService.insertAll(checkTemplateList));
        //TODO 入库的代码自行补充
        return map;
    }

    @RequestMapping("/insertCheckCard")
    public Map insertCheckCard(@RequestBody JSONObject source){

        Map map=new HashMap();
        JSONArray jsonArray=source.getJSONArray("checkTemplateList");
        for(int i=0;i<jsonArray.size();i++){
            CheckTemplate checkTemplate= JSON.toJavaObject(jsonArray.getJSONObject(i),CheckTemplate.class);
            if(checkTemplate.getId()==null){
               Long pid=checkCardTemplateService.selectCheckCardId(source.getLong("projectId"),source.getLong("productId"),source.getLong("type"));
               if(pid==null) {
                   if (checkCardTemplateService.InstCheckCard(source.getLong("type"), source.getLong("projectId"),source.getString("project") , source.getLong("productId") ,source.getString("product") , "无", source.getLong("createTime") ,source.getString("createBy")  ) == 1) {
                       pid=checkCardTemplateService.selectCheckCard("wu");
                   }
               }else{
                   checkCardTemplateService.updateCheckCard(pid,source.getLong("type"), source.getLong("projectId"),source.getString("project") , source.getLong("productId") ,source.getString("product") , "无", source.getLong("createTime") ,source.getString("createBy") );
               }
               checkTemplate.setPid(pid);
               checkTemplateService.create(checkTemplate);
           }else {
               checkTemplateService.update(checkTemplate);
           }
        }
//       for(CheckTemplate checkTemplate : source.getJSONArray("checkTemplateList")){
//           if(checkTemplate.getId()==null){
//               Long pid=checkCardTemplateService.selectCheckCardId(projectId,productId,type);
//               if(pid==null) {
//                   if (checkCardTemplateService.InstCheckCard(type, projectId, project, productId, product, name, createTime, createBy) == 1) {
//                       pid=checkCardTemplateService.selectCheckCard(name);
//                   }
//               }else{
//                   checkCardTemplateService.updateCheckCard(pid,type,projectId,project,productId,product,name,createTime,createBy);
//               }
//               checkTemplate.setPid(pid);
//               checkTemplateService.create(checkTemplate);
//           }else {
//               checkTemplateService.update(checkTemplate);
//           }
//       }
        map.put("success","执行成功");
        return map;
    }

}