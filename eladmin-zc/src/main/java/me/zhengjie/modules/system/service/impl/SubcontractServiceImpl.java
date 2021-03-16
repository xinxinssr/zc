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

import me.zhengjie.modules.system.domain.Subcontract;
import me.zhengjie.modules.system.domain.Supplier;
import me.zhengjie.modules.system.repository.SupplierRepository;
import me.zhengjie.modules.system.service.mapstruct.SupplierMapper;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.SubcontractRepository;
import me.zhengjie.modules.system.service.SubcontractService;
import me.zhengjie.modules.system.service.dto.SubcontractDto;
import me.zhengjie.modules.system.service.dto.SubcontractQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.SubcontractMapper;
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
* @date 2021-02-05
**/
@Service
@RequiredArgsConstructor
public class SubcontractServiceImpl implements SubcontractService {

    private final SubcontractRepository subcontractRepository;
    private final SubcontractMapper subcontractMapper;
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    @Override
    public Map<String,Object> queryAll(SubcontractQueryCriteria criteria, Pageable pageable){
        Page<Subcontract> page = subcontractRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(subcontractMapper::toDto));
    }

    @Override
    public Map<String, Object> queryAlls(String supplierName, String productType, Pageable pageable) {
        Page<Map> page = subcontractRepository.findAlls(supplierName,productType,pageable);

        return PageUtil.toPage(page);
    }

    @Override
    public Integer insertAll(List<Subcontract> subcontractList) {
        int count=0;
        for (Subcontract resources:subcontractList) {
            Supplier supplier=supplierRepository.selectName(resources.getSupplierName());
            if(supplier!=null){
                resources.setSupplierId(supplier.getId());
                subcontractMapper.toDto(subcontractRepository.save(resources));
                count++;
            }else {
                Supplier supplier1=null;
                supplier1.setName(resources.getSupplierName());
                supplierMapper.toDto(supplierRepository.save(supplier1));
                Supplier supplier2=supplierRepository.selectName(supplier1.getName());
                resources.setSupplierId(supplier2.getId());
                subcontractMapper.toDto(subcontractRepository.save(resources));
                count++;
            }
        }
        return count;
    }

    @Override
    public List<SubcontractDto> queryAll(SubcontractQueryCriteria criteria){
        return subcontractMapper.toDto(subcontractRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public SubcontractDto findById(Long id) {
        Subcontract subcontract = subcontractRepository.findById(id).orElseGet(Subcontract::new);
        ValidationUtil.isNull(subcontract.getId(),"Subcontract","id",id);
        return subcontractMapper.toDto(subcontract);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SubcontractDto create(Subcontract resources) {
        return subcontractMapper.toDto(subcontractRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Subcontract resources) {
        Subcontract subcontract = subcontractRepository.findById(resources.getId()).orElseGet(Subcontract::new);
        ValidationUtil.isNull( subcontract.getId(),"Subcontract","id",resources.getId());
        subcontract.copy(resources);
        subcontractRepository.save(subcontract);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            subcontractRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<SubcontractDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SubcontractDto subcontract : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("供应商id", subcontract.getSupplierId());
            map.put("供应商名称", subcontract.getSupplierName());
            map.put("提供产品名称/范围（公司合格供方目录）", subcontract.getSupplierScope());
            map.put("工序（电气事业部工序承接资质范围）", subcontract.getProcessScope());
            map.put("项目范围", subcontract.getProjectScope());
            map.put("产品类别", subcontract.getProductType());
            map.put("创建时间", subcontract.getCreateTime());
            map.put("创建者", subcontract.getCreateBy());
            map.put("更新时间", subcontract.getUpdateTime());
            map.put("更新者", subcontract.getUpdateBy());
            map.put("0正常 / 1 删除", subcontract.getState());
            map.put("产品范围", subcontract.getProductScope());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}