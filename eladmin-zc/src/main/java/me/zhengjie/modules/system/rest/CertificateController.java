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
import me.zhengjie.modules.system.domain.Certificate;
import me.zhengjie.modules.system.service.CertificateService;
import me.zhengjie.modules.system.service.dto.CertificateQueryCriteria;
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
* @date 2021-02-04
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "/certificate管理")
@RequestMapping("/api/certificate")
public class CertificateController {

    private final CertificateService certificateService;


    @RequestMapping("/InstCertificate")
    public Integer InstCertificate(String supplierId, String name, String path, String typeId, String typeName, String startTime, String endTime, String uploadTime, String creatTime, String creatBy) {
        return certificateService.InstCertificate(supplierId,name,path,typeId,typeName,startTime,endTime,uploadTime,creatTime,creatBy);
    }
    @RequestMapping("/UpdateCertificate")
    public Integer UpdateCertificate(String Id, String name, String path, String typeId, String typeName, String startTime, String endTime, String uploadTime, String updateTime, String updateBy) {
        return certificateService.UpdateCertificate(Id,name,path,typeId,typeName,startTime,endTime,uploadTime,updateTime,updateBy);
    }
    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('certificate:list')")
    public void download(HttpServletResponse response, CertificateQueryCriteria criteria) throws IOException {
        certificateService.download(certificateService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询/certificate")
    @ApiOperation("查询/certificate")
    @PreAuthorize("@el.check('certificate:list')")
    public ResponseEntity<Object> query(CertificateQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(certificateService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增/certificate")
    @ApiOperation("新增/certificate")
    @PreAuthorize("@el.check('certificate:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Certificate resources){
        return new ResponseEntity<>(certificateService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改/certificate")
    @ApiOperation("修改/certificate")
    @PreAuthorize("@el.check('certificate:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Certificate resources){
        certificateService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除/certificate")
    @ApiOperation("删除/certificate")
    @PreAuthorize("@el.check('certificate:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        certificateService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}