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

import me.zhengjie.modules.system.domain.ProductBatch;
import me.zhengjie.modules.system.domain.ProductProduct;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.ProductBatchRepository;
import me.zhengjie.modules.system.service.ProductBatchService;
import me.zhengjie.modules.system.service.dto.ProductBatchDto;
import me.zhengjie.modules.system.service.dto.ProductBatchQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.ProductBatchMapper;
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
public class ProductBatchServiceImpl implements ProductBatchService {

    private final ProductBatchRepository productBatchRepository;
    private final ProductBatchMapper productBatchMapper;


    @Override
    public List<Map> selectProductBatch(Long checkCardId) {
        return productBatchRepository.selectProductBatch(checkCardId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map InstProductBatch(List<ProductBatch> productBatchList) {
        Map map=new HashMap();
        for (ProductBatch resources:productBatchList) {
            //查询该产品id是否已经关联产品 已有删除再关联
            List<Map> mapList=productBatchRepository.selectProductBatch(resources.getCheckCardId());
            if(mapList.size()==0){
                productBatchRepository.InstProductBatch(resources.getCheckCardId(),resources.getProduct(),resources.getMateriaBatchId(),resources.getMateriaName());
                map.put("errmsg","success");
            }else {
                for(int i=0;i<mapList.size();i++){
                    productBatchRepository.deleteById(Long.parseLong(mapList.get(i).get("id").toString()));
                }
                productBatchRepository.InstProductBatch(resources.getCheckCardId(),resources.getProduct(),resources.getMateriaBatchId(),resources.getMateriaName());
                map.put("errmsg","success");
            }
        }
        return map;
    }

    @Override
    public Map<String,Object> queryAll(ProductBatchQueryCriteria criteria, Pageable pageable){
        Page<ProductBatch> page = productBatchRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(productBatchMapper::toDto));
    }

    @Override
    public List<ProductBatchDto> queryAll(ProductBatchQueryCriteria criteria){
        return productBatchMapper.toDto(productBatchRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ProductBatchDto findById(Long id) {
        ProductBatch productBatch = productBatchRepository.findById(id).orElseGet(ProductBatch::new);
        ValidationUtil.isNull(productBatch.getId(),"ProductBatch","id",id);
        return productBatchMapper.toDto(productBatch);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductBatchDto create(ProductBatch resources) {
        return productBatchMapper.toDto(productBatchRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ProductBatch resources) {
        ProductBatch productBatch = productBatchRepository.findById(resources.getId()).orElseGet(ProductBatch::new);
        ValidationUtil.isNull( productBatch.getId(),"ProductBatch","id",resources.getId());
        productBatch.copy(resources);
        productBatchRepository.save(productBatch);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            productBatchRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<ProductBatchDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ProductBatchDto productBatch : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("产品id", productBatch.getCheckCardId());
            map.put("产品名称", productBatch.getProduct());
            map.put("关联原材料批号id", productBatch.getMateriaBatchId());
            map.put("原材料名称", productBatch.getMateriaName());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}