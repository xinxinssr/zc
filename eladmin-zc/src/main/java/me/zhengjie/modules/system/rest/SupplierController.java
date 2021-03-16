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
import me.zhengjie.modules.system.domain.Supplier;
import me.zhengjie.modules.system.service.SupplierService;
import me.zhengjie.modules.system.service.dto.SupplierQueryCriteria;
import me.zhengjie.modules.system.util.ExcelUtil;
import me.zhengjie.modules.system.util.FileNameUtils;
import me.zhengjie.modules.system.util.FileUtils;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ly
 * @website https://el-admin.vip
 * @date 2021-02-01
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "/supplier管理")
@RequestMapping("/api/supplier")
public class SupplierController {


    private final SupplierService supplierService;


    @PostMapping(value = "/Supplierupload")
    public Map uploadExcel(@ApiParam(value = "用户信息Excel导入数据", required = true) MultipartFile file) throws Exception {
        Map  map =new HashMap();
        if (file == null) {
            map.put("errFlag","导入的表格不能为空");
            return map;
        }
        ExcelUtil excelUtil = new ExcelUtil();
        List<Supplier> supplierList = excelUtil.readExcelFileToDTO(file, Supplier.class);
        System.out.println("长度：" + supplierList.size());

        map.put("success", supplierService.insertAll(supplierList));
        //TODO 入库的代码自行补充
        return map;
    }

    @RequestMapping("/findByName")
    public Integer findByName(String name, String id) {
        if (!id.equals("null")) {
            return supplierService.findByName(name, id);
        } else {
            return supplierService.findByName(name);
        }

    }

    @RequestMapping("/selectByName")
    public Map selectByName(String name) {

        return supplierService.selectByName(name);


    }

    @RequestMapping("/selectById")
    public Supplier selectById(String id) {
        return supplierService.selectById(id);

    }

    @RequestMapping("/selectAll")
    //    @AnonymousAccess
    public List<Map> selectAll(Long type) {
        return supplierService.selectAll(type);

    }

    @RequestMapping("/findBySId")
    public Map findBySId(String id, Pageable pageable) {

        return supplierService.findBySId(id, pageable);
    }

    @RequestMapping("/delCertificate")
    public Integer delCertificate(String id) {
        return supplierService.delCertificate(id);
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('supplier:list')")
    public void download(HttpServletResponse response, SupplierQueryCriteria criteria) throws IOException {
        supplierService.download(supplierService.queryAll(criteria), response);
    }


    @GetMapping
    @Log("查询/supplier")
    @ApiOperation("查询/supplier")
    @PreAuthorize("@el.check('supplier:list')")
    public ResponseEntity<Object> query(String name, String productScope, String supplierBy,Long type, Pageable pageable) {

        return new ResponseEntity<>(supplierService.queryAlls(name, productScope, supplierBy,type, pageable), HttpStatus.OK);
    }


    @PostMapping
    @Log("新增/supplier")
    @ApiOperation("新增/supplier")
    @PreAuthorize("@el.check('supplier:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Supplier resources) {
        return new ResponseEntity<>(supplierService.create(resources), HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改/supplier")
    @ApiOperation("修改/supplier")
    @PreAuthorize("@el.check('supplier:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Supplier resources) {
        supplierService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除/supplier")
    @ApiOperation("删除/supplier")
    @PreAuthorize("@el.check('supplier:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        supplierService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}