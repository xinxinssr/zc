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
import me.zhengjie.modules.system.domain.CheckCard;
import me.zhengjie.modules.system.service.CheckCardService;
import me.zhengjie.modules.system.service.dto.CheckCardQueryCriteria;
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
* @date 2021-02-24
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "/checkCard管理")
@RequestMapping("/api/checkCard")
public class CheckCardController {

    private final CheckCardService checkCardService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('checkCard:list')")
    public void download(HttpServletResponse response, CheckCardQueryCriteria criteria) throws IOException {
        checkCardService.download(checkCardService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询/checkCard")
    @ApiOperation("查询/checkCard")
    @PreAuthorize("@el.check('checkCard:list')")
    public ResponseEntity<Object> query(String type, String platformName, String projectId, String productId, String factoryCode, String sri, Pageable pageable) {
        return new ResponseEntity<>(checkCardService.findAlls(type,platformName,projectId,productId,factoryCode,sri,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增/checkCard")
    @ApiOperation("新增/checkCard")
    @PreAuthorize("@el.check('checkCard:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody CheckCard resources){
        return new ResponseEntity<>(checkCardService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改/checkCard")
    @ApiOperation("修改/checkCard")
    @PreAuthorize("@el.check('checkCard:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody CheckCard resources){
        checkCardService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除/checkCard")
    @ApiOperation("删除/checkCard")
    @PreAuthorize("@el.check('checkCard:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        checkCardService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}