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

import me.zhengjie.modules.system.domain.ProductProduct;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.ProductProductRepository;
import me.zhengjie.modules.system.service.ProductProductService;
import me.zhengjie.modules.system.service.dto.ProductProductDto;
import me.zhengjie.modules.system.service.dto.ProductProductQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.ProductProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;

import java.util.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author ly
* @date 2021-03-15
**/
@Service
@RequiredArgsConstructor
public class ProductProductServiceImpl implements ProductProductService {

    private final ProductProductRepository productProductRepository;
    private final ProductProductMapper productProductMapper;

    @Override
    public List<Map> selectProductP(Long checkCardId) {
        return productProductRepository.selectProductP(checkCardId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map InstProductP(List<ProductProduct> productProductList) {
       Map map=new HashMap();
        for (ProductProduct resources:productProductList) {
            //查询该产品id是否已经关联产品 已有删除再关联
            List<Map> mapList=productProductRepository.selectProductP(resources.getCheckCardId());
            if(mapList.size()==0){
                productProductRepository.InstProductP(resources.getCheckCardId(),resources.getProduct(),resources.getCheckCardId2(),resources.getProduct2());
                map.put("errmsg","success");
            }else {
                for(int i=0;i<mapList.size();i++){
                    productProductRepository.deleteById(Long.parseLong(mapList.get(i).get("id").toString()));
                }
                productProductRepository.InstProductP(resources.getCheckCardId(),resources.getProduct(),resources.getCheckCardId2(),resources.getProduct2());
                map.put("errmsg","success");
            }
        }
        return map;
    }


    @Override
    public Map<String,Object> queryAll(ProductProductQueryCriteria criteria, Pageable pageable){
        Page<ProductProduct> page = productProductRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(productProductMapper::toDto));
    }

    @Override
    public List<ProductProductDto> queryAll(ProductProductQueryCriteria criteria){
        return productProductMapper.toDto(productProductRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ProductProductDto findById(Long id) {
        ProductProduct productProduct = productProductRepository.findById(id).orElseGet(ProductProduct::new);
        ValidationUtil.isNull(productProduct.getId(),"ProductProduct","id",id);
        return productProductMapper.toDto(productProduct);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductProductDto create(ProductProduct resources) {
        return productProductMapper.toDto(productProductRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProductProduct resources) {
        ProductProduct productProduct = productProductRepository.findById(resources.getId()).orElseGet(ProductProduct::new);
        ValidationUtil.isNull( productProduct.getId(),"ProductProduct","id",resources.getId());
        productProduct.copy(resources);
        productProductRepository.save(productProduct);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            productProductRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ProductProductDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ProductProductDto productProduct : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("产品id", productProduct.getCheckCardId());
            map.put("产品名称", productProduct.getProduct());
            map.put("产品id2", productProduct.getCheckCardId2());
            map.put("产品名称2", productProduct.getProduct2());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}