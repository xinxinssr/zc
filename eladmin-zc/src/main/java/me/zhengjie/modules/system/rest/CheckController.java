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
import me.zhengjie.modules.system.domain.Check;
import me.zhengjie.modules.system.service.CheckService;
import me.zhengjie.modules.system.service.dto.CheckQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author ly
* @date 2021-02-24
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "/check管理")
@RequestMapping("/api/check")
public class CheckController {

    private final CheckService checkService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('check:list')")
    public void download(HttpServletResponse response, CheckQueryCriteria criteria) throws IOException {
        checkService.download(checkService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询/check")
    @ApiOperation("查询/check")
    @PreAuthorize("@el.check('check:list')")
    public ResponseEntity<Object> query(CheckQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(checkService.queryAll(criteria,pageable),HttpStatus.OK);
    }
    @RequestMapping("/selectAll")
    public ResponseEntity<Object> selectAll(String pid, Pageable pageable){
        return new ResponseEntity<>(checkService.findAlls(pid,pageable),HttpStatus.OK);
    }

    @RequestMapping("/selectProductP")
    public List<Map> selectProductP(Long pid){
        return  checkService.selectProductP(pid);
    }
    @RequestMapping("/selectProductBatch")
    public List<Map> selectProductBatch(Long pid){

        return  checkService.selectProductBatch(pid);
    }

    @RequestMapping("/selectProductSupplier")
    public List<Map> selectProductSupplier(Long pid){
        return  checkService.selectProductSupplier(pid);
    }


    @RequestMapping("/selectIsGood")
    public List<Map> selectIsGood(String projectId, String productId){
        return  checkService.selectIsGood(projectId,productId);
    }
    @RequestMapping("/selectUnqualified")
    public List<Map> selectUnqualified(String projectId, String productId){

        return  checkService.selectUnqualified(projectId,productId);


    }
    @PostMapping
    @Log("新增/check")
    @ApiOperation("新增/check")
    @PreAuthorize("@el.check('check:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Check resources){
        return new ResponseEntity<>(checkService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改/check")
    @ApiOperation("修改/check")
    @PreAuthorize("@el.check('check:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Check resources){
        checkService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除/check")
    @ApiOperation("删除/check")
    @PreAuthorize("@el.check('check:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        checkService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}