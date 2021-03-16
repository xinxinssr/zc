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

import me.zhengjie.modules.system.domain.Supplier;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.SupplierRepository;
import me.zhengjie.modules.system.service.SupplierService;
import me.zhengjie.modules.system.service.dto.SupplierDto;
import me.zhengjie.modules.system.service.dto.SupplierQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.SupplierMapper;
import org.springframework.data.jpa.repository.Modifying;
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
* @date 2021-02-01
**/
@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    @Override
    @Transactional
    public Integer findByName(String name,String id) {
        Map map=supplierRepository.findByName(name);
        if(map.size()!=0 ){
            if(!map.get("id").toString().equals(id)) {
                System.out.println(map.get("id"));
                return 1;
            }else {
                return 0;
            }
        }else {
            return 0;
        }
    }
    @Override
    @Transactional
    public Integer findByName(String name) {
        Map map=supplierRepository.findByName(name);
        if(map.size()!=0){
            return 1;
        }else {
            return 0;
        }

    }
    @Override
    public Map selectByName(String name) {
        return supplierRepository.findByName(name);
    }


    @Override
    public Supplier selectById(String id) {
        return supplierRepository.selectById(id);
    }

    @Override
    public List<Map> selectAll(Long type) {
        return supplierRepository.selectAll(type);
    }

    @Override
    @Modifying
    @Transactional
    public Integer delCertificate(String id) {
        return supplierRepository.delCertificate(id);
    }

    @Override
    @Transactional
    public Integer insertAll(List<Supplier> supplierList) {
        int count=0;

        for (Supplier resources:supplierList) {
            Supplier supplier=supplierRepository.selectName(resources.getName());
            if(supplier!=null){
                resources.setId(supplier.getId());
                supplierMapper.toDto(supplierRepository.save(resources));
                count++;
            }else {
                supplierMapper.toDto(supplierRepository.save(resources));
                count++;
            }
        }
        return count;
    }


    @Override
    public Map<String,Object> queryAll(SupplierQueryCriteria criteria, Pageable pageable){
        Page<Supplier> page = supplierRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(supplierMapper::toDto));
    }

//    @Override
//    public Map<String, Object> queryAlls(SupplierQueryCriteria criteria, Pageable pageable) {
//        Page<Supplier> page = supplierRepository.findAlls((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
//        System.out.println(criteria+"1111111111111");
//        return PageUtil.toPage(page.map(supplierMapper::toDto));
//    }
    @Override
    public Map<String, Object> queryAlls(String name,String productScope,String  supplierBy,Long type,  Pageable pageable) {
        Page<Map> page = supplierRepository.findAlls(name,productScope,supplierBy,type,pageable);

        return PageUtil.toPage(page);
    }

    @Override
    public Map<String, Object> findBySId(String id, Pageable pageable) {
        Page<Map> page = supplierRepository.findBySId(id,pageable);

        return PageUtil.toPage(page);
    }

    @Override
    public List<SupplierDto> queryAll(SupplierQueryCriteria criteria){
        return supplierMapper.toDto(supplierRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public SupplierDto findById(Long id) {
        Supplier supplier = supplierRepository.findById(id).orElseGet(Supplier::new);
        ValidationUtil.isNull(supplier.getId(),"Supplier","id",id);
        return supplierMapper.toDto(supplier);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SupplierDto create(Supplier resources) {
             return  supplierMapper.toDto(supplierRepository.save(resources));

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Supplier resources) {
        Supplier supplier = supplierRepository.findById(resources.getId()).orElseGet(Supplier::new);
        ValidationUtil.isNull( supplier.getId(),"Supplier","id",resources.getId());
        supplier.copy(resources);
        supplierRepository.save(supplier);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            supplierRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<SupplierDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SupplierDto supplier : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("供应商名称", supplier.getName());
            map.put("提供产品名称/范围（公司合格供方目录）", supplier.getProductScope());
            map.put("供应商联系人", supplier.getSupplierBy());
            map.put("供应商联系电话", supplier.getSupplierPhone());
            map.put("供应商地址", supplier.getSupplierAddres());
            map.put("创建时间", supplier.getCreateTime());
            map.put("创建者", supplier.getCreateBy());
            map.put("更新时间", supplier.getUpdateTime());
            map.put("更新者", supplier.getUpdateBy());
            map.put("0正常 / 1 删除", supplier.getState());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}