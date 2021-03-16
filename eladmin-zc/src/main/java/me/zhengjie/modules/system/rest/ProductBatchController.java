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
import me.zhengjie.modules.system.domain.ProductBatch;
import me.zhengjie.modules.system.service.ProductBatchService;
import me.zhengjie.modules.system.service.dto.ProductBatchQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author ly
* @date 2021-03-15
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "/productRelation管理")
@RequestMapping("/api/productBatch")
public class ProductBatchController {

    private final ProductBatchService productBatchService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('productBatch:list')")
    public void download(HttpServletResponse response, ProductBatchQueryCriteria criteria) throws IOException {
        productBatchService.download(productBatchService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询/productRelation")
    @ApiOperation("查询/productRelation")
    @PreAuthorize("@el.check('productBatch:list')")
    public ResponseEntity<Object> query(ProductBatchQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(productBatchService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增/productRelation")
    @ApiOperation("新增/productRelation")
    @PreAuthorize("@el.check('productBatch:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody ProductBatch resources){
        return new ResponseEntity<>(productBatchService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改/productRelation")
    @ApiOperation("修改/productRelation")
    @PreAuthorize("@el.check('productBatch:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody ProductBatch resources){
        productBatchService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除/productRelation")
    @ApiOperation("删除/productRelation")
    @PreAuthorize("@el.check('productBatch:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        productBatchService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}