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
import me.zhengjie.modules.system.domain.Subcontract;
import me.zhengjie.modules.system.domain.Supplier;
import me.zhengjie.modules.system.service.SubcontractService;
import me.zhengjie.modules.system.service.dto.SubcontractQueryCriteria;
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
* @date 2021-02-05
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "/subcontract管理")
@RequestMapping("/api/subcontract")
public class SubcontractController {

    private final SubcontractService subcontractService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('subcontract:list')")
    public void download(HttpServletResponse response, SubcontractQueryCriteria criteria) throws IOException {
        subcontractService.download(subcontractService.queryAll(criteria), response);
    }

    @PostMapping(value = "/Subcontractupload")
    public Map uploadExcel(@ApiParam(value = "用户信息Excel导入数据", required = true) MultipartFile file) throws Exception {
        Map  map =new HashMap();
        if (file == null) {
            map.put("errFlag","导入的表格不能为空");
            return map;
        }
        ExcelUtil excelUtil = new ExcelUtil();
        List<Subcontract> subcontractList = excelUtil.readExcelFileToDTO(file, Subcontract.class);
        System.out.println("长度：" + subcontractList.size());

        map.put("success", subcontractService.insertAll(subcontractList));
        //TODO 入库的代码自行补充
        return map;
    }



    @GetMapping
    @Log("查询/subcontract")
    @ApiOperation("查询/subcontract")
    @PreAuthorize("@el.check('subcontract:list')")
    public ResponseEntity<Object> query(String supplierName,String productType, Pageable pageable){
        return new ResponseEntity<>(subcontractService.queryAlls(supplierName,productType,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增/subcontract")
    @ApiOperation("新增/subcontract")
    @PreAuthorize("@el.check('subcontract:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Subcontract resources){
        return new ResponseEntity<>(subcontractService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改/subcontract")
    @ApiOperation("修改/subcontract")
    @PreAuthorize("@el.check('subcontract:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Subcontract resources){
        subcontractService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除/subcontract")
    @ApiOperation("删除/subcontract")
    @PreAuthorize("@el.check('subcontract:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        subcontractService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}