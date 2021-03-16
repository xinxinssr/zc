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
import me.zhengjie.modules.system.domain.Product;
import me.zhengjie.modules.system.domain.Project;
import me.zhengjie.modules.system.service.ProductService;
import me.zhengjie.modules.system.service.dto.ProductDto;
import me.zhengjie.modules.system.service.dto.ProductQueryCriteria;
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
* @date 2021-02-19
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "/product管理")
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('product:list')")
    public void download(HttpServletResponse response, ProductQueryCriteria criteria) throws IOException {
        productService.download(productService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询/product")
    @ApiOperation("查询/product")
    @PreAuthorize("@el.check('product:list')")
    public ResponseEntity<Object> query(String type,String platformName,String projectId,String name, Pageable pageable){
        if(type==null){
            return new ResponseEntity<>(productService.queryAlls("0", platformName, projectId, name,pageable), HttpStatus.OK);
        }else {
            return new ResponseEntity<>(productService.queryAlls(type, platformName, projectId, name,pageable), HttpStatus.OK);
        }
        }
    @RequestMapping("/insert")
    public Map insertProduct(@RequestBody List<ProductDto> productDtoList){
        Map map=new HashMap();
         Integer result=0;
        for(ProductDto productDto : productDtoList){
          Map map2= productService.selectByname(productDto.getProjectName(),productDto.getType());
            if(map2.size()!=0){
            Integer s1= productService.InstProduct(productDto.getType(),Long.parseLong(map2.get("id").toString()),productDto.getName(),productDto.getPlatformName(),productDto.getProductCode(),productDto.getSpecification(),productDto.getCreateTime(),productDto.getCreateBy());
             if(s1==0){
                 map.put("data","0");
                 return map;
             }else {
                 result=result+s1;
             }
            }else {
               if(productService.InstProject(productDto.getType(),productDto.getProjectName(),productDto.getCreateTime(),productDto.getCreateBy())!=0) {
                   Map map3=productService.selectByname(productDto.getProjectName(),productDto.getType());
                   Integer s2=  productService.InstProduct(productDto.getType(),Long.parseLong(map3.get("id").toString()),productDto.getName(),productDto.getPlatformName(),productDto.getProductCode(),productDto.getSpecification(),productDto.getCreateTime(),productDto.getCreateBy());
                   if(s2==0){
                       map.put("data","0");
                       return map;
                   }else {
                       result=result+s2;
                   }
               }else{
                   map.put("data","0");
                   return map;
                }

            }
        }
        map.put("data",result);
        return map;
    }

    @RequestMapping("/update")
    public Map updateProduct(@RequestBody List<ProductDto> productDtoList){
        Map map=new HashMap();
        Integer result=0;
        for(ProductDto productDto : productDtoList){
            Map map2= productService.selectByname(productDto.getProjectName(),productDto.getType());
            if(map2.size()!=0){
                Integer s1= productService.updateProduct(productDto.getId(),Long.parseLong(map2.get("id").toString()),productDto.getName(),productDto.getPlatformName(),productDto.getProductCode(),productDto.getSpecification(),productDto.getUpdateTime(),productDto.getUpdateBy());
                if(s1==0){
                    map.put("data","0");
                    return map;
                }else {
                    result=result+s1;
                }
            }else {
                if(productService.InstProject(productDto.getType(),productDto.getProjectName(),productDto.getUpdateTime(),productDto.getUpdateBy())!=0) {
                    Map map3=productService.selectByname(productDto.getProjectName(),productDto.getType());
                    Integer s2= productService.updateProduct(productDto.getId(),Long.parseLong(map3.get("id").toString()),productDto.getName(),productDto.getPlatformName(),productDto.getProductCode(),productDto.getSpecification(),productDto.getUpdateTime(),productDto.getUpdateBy());
                    if(s2==0){
                        map.put("data","0");
                        return map;
                    }else {
                        result=result+s2;
                    }
                }else{
                    map.put("data","0");
                    return map;
                }

            }
        }
        map.put("data",result);
        return map;
    }


    @RequestMapping("/selectProduct")
    public  Map selectProduct(Long type,String productName,String projectName,String platformName){
        Map map=new HashMap();
        Long pid=productService.selectProjectByName(type,projectName);
         if(pid!=null){
            Long id=productService.selectProductByName(pid,productName);
            if(id!=null) {
                map.put("success", id);
            }else{
                map.put("success","error");
            }
         }else {
             map.put("success","error");
         }
         return map;
    }

    @RequestMapping("/selectAllProject")
    public  List<Map>  selectAllProject(Long type){
     return  productService.selectAllProject(type);
    }
    @RequestMapping("/selectAllProduct")
    public  List<Map>  selectAllProduct(String projectId){
        return  productService.selectAllProduct(projectId);
    }
    @RequestMapping("/selectAllProduct2")
    public  List<Map>  selectAllProduct2(){
        return  productService.selectAllProduct2();
    }
    @RequestMapping("/selectAllProject2")
    public  List<Map>  selectAllProject2(){
        return  productService.selectAllProject2();
    }
    @PostMapping
    @Log("新增/product")
    @ApiOperation("新增/product")
    @PreAuthorize("@el.check('product:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Product resources){
        return new ResponseEntity<>(productService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改/product")
    @ApiOperation("修改/product")
    @PreAuthorize("@el.check('product:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Product resources){
        productService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除/product")
    @ApiOperation("删除/product")
    @PreAuthorize("@el.check('product:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        productService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}