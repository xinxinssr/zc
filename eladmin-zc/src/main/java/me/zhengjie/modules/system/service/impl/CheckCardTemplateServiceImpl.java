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

import me.zhengjie.modules.system.domain.CheckCardTemplate;
import me.zhengjie.modules.system.domain.CheckTemplate;
import me.zhengjie.modules.system.repository.CheckTemplateRepository;
import me.zhengjie.modules.system.service.dto.CheckTemplateDto;
import me.zhengjie.modules.system.service.mapstruct.CheckTemplateMapper;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.CheckCardTemplateRepository;
import me.zhengjie.modules.system.service.CheckCardTemplateService;
import me.zhengjie.modules.system.service.dto.CheckCardTemplateDto;
import me.zhengjie.modules.system.service.dto.CheckCardTemplateQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.CheckCardTemplateMapper;
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
* @date 2021-02-22
**/
@Service
@RequiredArgsConstructor
public class CheckCardTemplateServiceImpl implements CheckCardTemplateService {

    private final CheckCardTemplateRepository checkCardTemplateRepository;
    private final CheckTemplateRepository checkTemplateRepository;
    private final CheckCardTemplateMapper checkCardTemplateMapper;
    private final CheckTemplateMapper checkTemplateMapper;

    @Override
    public Map<String,Object> queryAll(CheckCardTemplateQueryCriteria criteria, Pageable pageable){
        Page<CheckCardTemplate> page = checkCardTemplateRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(checkCardTemplateMapper::toDto));
    }

    @Override
    public List<CheckCardTemplateDto> queryAll(CheckCardTemplateQueryCriteria criteria){
        return checkCardTemplateMapper.toDto(checkCardTemplateRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public CheckCardTemplateDto findById(Long id) {
        CheckCardTemplate checkCardTemplate = checkCardTemplateRepository.findById(id).orElseGet(CheckCardTemplate::new);
        ValidationUtil.isNull(checkCardTemplate.getId(),"CheckCardTemplate","id",id);
        return checkCardTemplateMapper.toDto(checkCardTemplate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CheckCardTemplateDto create(CheckCardTemplate resources) {
        return checkCardTemplateMapper.toDto(checkCardTemplateRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CheckCardTemplate resources) {
        CheckCardTemplate checkCardTemplate = checkCardTemplateRepository.findById(resources.getId()).orElseGet(CheckCardTemplate::new);
        ValidationUtil.isNull( checkCardTemplate.getId(),"CheckCardTemplate","id",resources.getId());
        checkCardTemplate.copy(resources);
        checkCardTemplateRepository.save(checkCardTemplate);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            checkCardTemplateRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<CheckCardTemplateDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CheckCardTemplateDto checkCardTemplate : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("项目id", checkCardTemplate.getProjectId());
            map.put("产品id", checkCardTemplate.getProductId());
            map.put("名称", checkCardTemplate.getName());
            map.put("0 电器 / 1 焊接 / 2机械", checkCardTemplate.getType());
            map.put("创建时间", checkCardTemplate.getCreateTime());
            map.put("创建人", checkCardTemplate.getCreateBy());
            map.put("修改时间", checkCardTemplate.getUpdateTime());
            map.put("修改人", checkCardTemplate.getUpdateBy());
            map.put("0正常 / 1 删除", checkCardTemplate.getState());
            map.put("项目名称", checkCardTemplate.getProject());
            map.put("产品名称", checkCardTemplate.getProduct());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer InstCheckCard(Long type, Long projectId, String project, Long productId, String product, String name, Long createTime, String createBy) {
        return checkCardTemplateRepository.InstCheckCard(type,projectId,project,productId,product,name,createTime,createBy);
    }

    @Override
    public Long selectCheckCard(String name) {
        return checkCardTemplateRepository.selectCheckCard(name);
    }

    @Override
    public List<Map> selectTemplateByPid(Long pid) {
        return checkCardTemplateRepository.selectTemplateByPid(pid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer InstCheckTemplate(Long pid,Integer no, String process, String notice, String ask, String checkMethod, String remark) {
        return checkCardTemplateRepository.InstCheckTemplate(pid,no,process,notice,ask,checkMethod,remark);
    }

    @Override
    public Long selectCheckCardId(Long projectId, Long productId,Long type){
        return checkCardTemplateRepository.selectCheckCardId(projectId,productId,type);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCheckTemplate(Long id) {
        checkCardTemplateRepository.deleteCheckTemplate(id);
    }

    @Override
    public Integer insertAll(List<CheckTemplate> checkTemplateList) {
        Integer result=0;
        for(CheckTemplate checkTemplate : checkTemplateList){
            checkTemplateMapper.toDto(checkTemplateRepository.save(checkTemplate));
            result++;
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateCheckCard(Long id, Long type, Long projectId, String project, Long productId, String product, String name, Long createTime, String createBy) {
        return checkCardTemplateRepository.updateCheckCard(id,type,projectId,project,productId,product,name,createTime,createBy);
    }

    @Override
    public Map findCheckTemplate(Long id) {
       Map map = checkCardTemplateRepository.findCheckTemplate(id);
       List<Map> mapList3 = checkTemplateRepository.selectAccessory(id);
       HashMap<String, Object> newOne = new HashMap<>();
       newOne.putAll(map);
       newOne.put("accessory",mapList3);
        return  newOne;
    }


}