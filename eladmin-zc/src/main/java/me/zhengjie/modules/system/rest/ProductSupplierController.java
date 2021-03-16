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
import me.zhengjie.modules.system.domain.ProductSupplier;
import me.zhengjie.modules.system.service.ProductSupplierService;
import me.zhengjie.modules.system.service.dto.ProductSupplierQueryCriteria;
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
@Api(tags = "/productSupplier管理")
@RequestMapping("/api/productSupplier")
public class ProductSupplierController {

    private final ProductSupplierService productSupplierService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('productSupplier:list')")
    public void download(HttpServletResponse response, ProductSupplierQueryCriteria criteria) throws IOException {
        productSupplierService.download(productSupplierService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询/productSupplier")
    @ApiOperation("查询/productSupplier")
    @PreAuthorize("@el.check('productSupplier:list')")
    public ResponseEntity<Object> query(ProductSupplierQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(productSupplierService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增/productSupplier")
    @ApiOperation("新增/productSupplier")
    @PreAuthorize("@el.check('productSupplier:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody ProductSupplier resources){
        return new ResponseEntity<>(productSupplierService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改/productSupplier")
    @ApiOperation("修改/productSupplier")
    @PreAuthorize("@el.check('productSupplier:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody ProductSupplier resources){
        productSupplierService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除/productSupplier")
    @ApiOperation("删除/productSupplier")
    @PreAuthorize("@el.check('productSupplier:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        productSupplierService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}