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

import me.zhengjie.modules.system.domain.ProductSupplier;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.ProductSupplierRepository;
import me.zhengjie.modules.system.service.ProductSupplierService;
import me.zhengjie.modules.system.service.dto.ProductSupplierDto;
import me.zhengjie.modules.system.service.dto.ProductSupplierQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.ProductSupplierMapper;
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
public class ProductSupplierServiceImpl implements ProductSupplierService {

    private final ProductSupplierRepository productSupplierRepository;
    private final ProductSupplierMapper productSupplierMapper;

    @Override
    public List<Map> selectProductSupplier(Long checkCardId) {
        return productSupplierRepository.selectProductSupplier(checkCardId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map InstProductSupplier(List<ProductSupplier> productSupplierList) {
        Map map=new HashMap();
        for (ProductSupplier resources:productSupplierList) {
            //查询该产品id是否已经关联产品 已有删除再关联
            List<Map> mapList=productSupplierRepository.selectProductSupplier(resources.getCheckCardId());
            if(mapList.size()==0){
                productSupplierRepository.InstProductSupplier(resources.getCheckCardId(),resources.getProduct(),resources.getSupplierId(),resources.getSupplierName());
                map.put("errmsg","success");
            }else {
                for(int i=0;i<mapList.size();i++){
                    productSupplierRepository.deleteById(Long.parseLong(mapList.get(i).get("id").toString()));
                }
                productSupplierRepository.InstProductSupplier(resources.getCheckCardId(),resources.getProduct(),resources.getSupplierId(),resources.getSupplierName());
                map.put("errmsg","success");
            }
        }
        return map;
    }

    @Override
    public Map<String,Object> queryAll(ProductSupplierQueryCriteria criteria, Pageable pageable){
        Page<ProductSupplier> page = productSupplierRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(productSupplierMapper::toDto));
    }

    @Override
    public List<ProductSupplierDto> queryAll(ProductSupplierQueryCriteria criteria){
        return productSupplierMapper.toDto(productSupplierRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ProductSupplierDto findById(Long id) {
        ProductSupplier productSupplier = productSupplierRepository.findById(id).orElseGet(ProductSupplier::new);
        ValidationUtil.isNull(productSupplier.getId(),"ProductSupplier","id",id);
        return productSupplierMapper.toDto(productSupplier);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductSupplierDto create(ProductSupplier resources) {
        return productSupplierMapper.toDto(productSupplierRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProductSupplier resources) {
        ProductSupplier productSupplier = productSupplierRepository.findById(resources.getId()).orElseGet(ProductSupplier::new);
        ValidationUtil.isNull( productSupplier.getId(),"ProductSupplier","id",resources.getId());
        productSupplier.copy(resources);
        productSupplierRepository.save(productSupplier);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            productSupplierRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ProductSupplierDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ProductSupplierDto productSupplier : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("检验卡片id", productSupplier.getCheckCardId());
            map.put("产品名称", productSupplier.getProduct());
            map.put("关联供应商id", productSupplier.getSupplierId());
            map.put("供应商名称", productSupplier.getSupplierName());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}