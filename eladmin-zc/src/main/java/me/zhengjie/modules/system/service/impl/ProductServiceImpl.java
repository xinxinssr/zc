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
package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.system.domain.Product;
import me.zhengjie.modules.system.domain.Project;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.ProductRepository;
import me.zhengjie.modules.system.service.ProductService;
import me.zhengjie.modules.system.service.dto.ProductDto;
import me.zhengjie.modules.system.service.dto.ProductQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author ly
* @date 2021-02-19
**/
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Map<String, Object> queryAlls(String type,String platformName,String projectId,String name, Pageable pageable) {
        Page<Map> page = productRepository.findAlls(type,platformName,projectId,name,pageable);

        return PageUtil.toPage(page);
    }



    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer InstProduct(Long type,Long projectId, String name, String platformName, String productCode,  String specification, Long creatTime, String creatBy) {
        return productRepository.InstProduct(type,projectId,name,platformName,productCode,specification,creatTime,creatBy);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateProduct(Long id, Long projectId, String name, String platformName, String productCode, String specification, Long updateTime, String updateBy) {
        return productRepository.updateProduct(id,projectId,name,platformName,productCode,specification,updateTime,updateBy);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer InstProject(Long type,String name, Long creatTime, String creatBy) {
        return productRepository.InstProject(type,name,creatTime,creatBy);
    }

    @Override
    public Map selectByname(String name,Long type) {
        return productRepository.selectByname(name,type);
    }

    @Override
    public Long selectProjectByName(Long type, String name) {
        return productRepository.selectProjectByName(type,name);
    }

    @Override
    public Long selectProductByName(Long id, String name) {
        return productRepository.selectProductByName(id,name);
    }

    @Override
    public  List<Map>  selectAllProject(Long type) {
        return productRepository.selectAllProject(type);
    }

    @Override
    public List<Map> selectAllProduct(String projectId) {
        return productRepository.selectAllProduct(projectId);
    }

    @Override
    public List<Map> selectAllProduct2() {
        return productRepository.selectAllProduct2();
    }

    @Override
    public List<Map> selectAllProject2() {
        return productRepository.selectAllProject2();
    }

    @Override
    public Map<String,Object> queryAll(ProductQueryCriteria criteria, Pageable pageable){
        Page<Product> page = productRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(productMapper::toDto));
    }

    @Override
    public List<ProductDto> queryAll(ProductQueryCriteria criteria){
        return productMapper.toDto(productRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ProductDto findById(Long id) {
        Product product = productRepository.findById(id).orElseGet(Product::new);
        ValidationUtil.isNull(product.getId(),"Product","id",id);
        return productMapper.toDto(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductDto create(Product resources) {
        return productMapper.toDto(productRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Product resources) {
        Product product = productRepository.findById(resources.getId()).orElseGet(Product::new);
        ValidationUtil.isNull( product.getId(),"Product","id",resources.getId());
        product.copy(resources);
        productRepository.save(product);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            productRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ProductDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ProductDto product : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("0 电气 / 1 焊接 / 2机械", product.getType());
            map.put("项目id", product.getProjectId());
            map.put("产品名称", product.getName());
            map.put("项目平台id", product.getPlatformId());
            map.put("项目平台名称", product.getPlatformName());
            map.put("编号", product.getProductCode());

            map.put("规格", product.getSpecification());
            map.put("创建时间", product.getCreateTime());
            map.put("创建者", product.getCreateBy());
            map.put("更新时间", product.getUpdateTime());
            map.put("更新者", product.getUpdateBy());
            map.put("0正常 / 1 删除", product.getState());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}