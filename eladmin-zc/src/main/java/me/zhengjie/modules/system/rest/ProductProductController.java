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
import me.zhengjie.modules.system.domain.ProductProduct;
import me.zhengjie.modules.system.service.ProductProductService;
import me.zhengjie.modules.system.service.dto.ProductProductQueryCriteria;
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
@Api(tags = "/productp管理")
@RequestMapping("/api/productProduct")
public class ProductProductController {

    private final ProductProductService productProductService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('productProduct:list')")
    public void download(HttpServletResponse response, ProductProductQueryCriteria criteria) throws IOException {
        productProductService.download(productProductService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询/productp")
    @ApiOperation("查询/productp")
    @PreAuthorize("@el.check('productProduct:list')")
    public ResponseEntity<Object> query(ProductProductQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(productProductService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增/productp")
    @ApiOperation("新增/productp")
    @PreAuthorize("@el.check('productProduct:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody ProductProduct resources){
        return new ResponseEntity<>(productProductService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改/productp")
    @ApiOperation("修改/productp")
    @PreAuthorize("@el.check('productProduct:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody ProductProduct resources){
        productProductService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除/productp")
    @ApiOperation("删除/productp")
    @PreAuthorize("@el.check('productProduct:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        productProductService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}