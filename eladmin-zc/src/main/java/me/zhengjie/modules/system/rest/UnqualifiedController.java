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

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.system.domain.Unqualified;
import me.zhengjie.modules.system.service.UnqualifiedService;
import me.zhengjie.modules.system.service.dto.UnqualifiedQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author ly
* @date 2021-03-01
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "/unqualified管理")
@RequestMapping("/api/unqualified")
public class UnqualifiedController {

    private final UnqualifiedService unqualifiedService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('unqualified:list')")
    public void download(HttpServletResponse response, UnqualifiedQueryCriteria criteria) throws IOException {
        unqualifiedService.download(unqualifiedService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询/unqualified")
    @ApiOperation("查询/unqualified")
    @PreAuthorize("@el.check('unqualified:list')")
    public ResponseEntity<Object> query(String productName, String createBy, String faultName, String faultLevel,Long projectId, Pageable pageable){
        return new ResponseEntity<>(unqualifiedService.findAlls(productName,createBy,faultName,faultLevel,projectId,pageable),HttpStatus.OK);
    }
    @RequestMapping("/findCreateBy")
    public List<Map> findCreateBy(){
      return   unqualifiedService.findCreateBy();
    }
    @PostMapping
    @Log("新增/unqualified")
    @ApiOperation("新增/unqualified")
    @PreAuthorize("@el.check('unqualified:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Unqualified resources){
        return new ResponseEntity<>(unqualifiedService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改/unqualified")
    @ApiOperation("修改/unqualified")
    @PreAuthorize("@el.check('unqualified:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Unqualified resources){
        unqualifiedService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除/unqualified")
    @ApiOperation("删除/unqualified")
    @PreAuthorize("@el.check('unqualified:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        unqualifiedService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}