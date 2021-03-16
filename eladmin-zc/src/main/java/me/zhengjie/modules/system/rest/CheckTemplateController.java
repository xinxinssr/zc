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
import com.alibaba.fastjson.JSONObject;
import me.zhengjie.annotation.AnonymousAccess;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.system.domain.CheckTemplate;
import me.zhengjie.modules.system.service.CheckTemplateService;
import me.zhengjie.modules.system.service.dto.CheckTemplateDto;
import me.zhengjie.modules.system.service.dto.CheckTemplateQueryCriteria;

import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

/**
 * @website https://el-admin.vip
 * @author ly
 * @date 2021-02-25
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "/checkTemplate管理")
@RequestMapping("/api/checkTemplate")
public class CheckTemplateController {

    private final CheckTemplateService checkTemplateService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('checkTemplate:list')")
    public void download(HttpServletResponse response, CheckTemplateQueryCriteria criteria) throws IOException {
        checkTemplateService.download(checkTemplateService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询/checkTemplate")
    @ApiOperation("查询/checkTemplate")
    @PreAuthorize("@el.check('checkTemplate:list')")
    public ResponseEntity<Object> query(Long type,Long projectId,Long productId, Pageable pageable){

        return new ResponseEntity<>(checkTemplateService.findAlls(type,projectId,productId,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增/checkTemplate")
    @ApiOperation("新增/checkTemplate")
    @PreAuthorize("@el.check('checkTemplate:add')")
    public ResponseEntity<Object>  create(@Validated @RequestBody JSONObject resources){

        CheckTemplate resources2 = JSON.parseObject(JSON.toJSONString(resources), CheckTemplate.class);
        CheckTemplateDto checkTemplateDto=checkTemplateService.create(resources2);
         for(Object path:resources.getJSONArray("pictures")){
             int one = path.toString().lastIndexOf("/");
             String name=path.toString().substring((one+1));
            checkTemplateService.InstAccessory(checkTemplateDto.getId(),name,path.toString(),new Date().getTime(),resources.get("createBy").toString());
         }
        return new ResponseEntity<>(checkTemplateDto,HttpStatus.CREATED);
    }

//    @PutMapping
//    @Log("修改/checkTemplate")
//    @ApiOperation("修改/checkTemplate")
//    @PreAuthorize("@el.check('checkTemplate:edit')")
    @RequestMapping("/checkTemplateEdit")
    public ResponseEntity<Object> update(@Validated @RequestBody JSONObject resources){
        CheckTemplate resources2 = JSON.parseObject(JSON.toJSONString(resources), CheckTemplate.class);
        checkTemplateService.update(resources2);
        if(resources.getJSONArray("pictures")!=null) {
            for (Object path : resources.getJSONArray("pictures")) {
                int one = path.toString().lastIndexOf("/");
                String name = path.toString().substring((one + 1));
                checkTemplateService.InstAccessory(resources2.getId(), name, path.toString(), new Date().getTime(), resources.get("updateBy").toString());
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除/checkTemplate")
    @ApiOperation("删除/checkTemplate")
    @PreAuthorize("@el.check('checkTemplate:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        checkTemplateService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping("deleteCardTemplate")
    public Map  deleteCardTemplate(Long id){
        Map map=new HashMap();
        Long[] ids=new Long[1];
        ids[0]=id;
        checkTemplateService.deleteAll(ids);
        map.put("success","删除成功");
        return map;
    }
    @RequestMapping("selectCheckCardTemplate")
    public List<Map> selectCheckCardTemplate(Long type){
        return checkTemplateService.selectCheckCardTemplate(type);
    }
    @RequestMapping("deleteCheckTemplate")
    public void deleteCheckTemplate(Long id){
      checkTemplateService.deleteCheckTemplate(id);
    }
}