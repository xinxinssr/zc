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

import me.zhengjie.modules.system.domain.CheckTemplate;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.CheckTemplateRepository;
import me.zhengjie.modules.system.service.CheckTemplateService;
import me.zhengjie.modules.system.service.dto.CheckTemplateDto;
import me.zhengjie.modules.system.service.dto.CheckTemplateQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.CheckTemplateMapper;
import org.springframework.data.domain.PageImpl;
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
 * @date 2021-02-25
 **/
@Service
@RequiredArgsConstructor
public class CheckTemplateServiceImpl implements CheckTemplateService {

    private final CheckTemplateRepository checkTemplateRepository;
    private final CheckTemplateMapper checkTemplateMapper;

    @Override
    public List<Map> selectAccessory(Long pid) {
        return checkTemplateRepository.selectAccessory(pid);
    }

    @Override
    public Map<String,Object> queryAll(CheckTemplateQueryCriteria criteria, Pageable pageable){
        Page<CheckTemplate> page = checkTemplateRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(checkTemplateMapper::toDto));
    }

    @Override
    public Map<String, Object> findAlls(Long type, Long projectId, Long productId, Pageable pageable) {
        Page<Map> page = checkTemplateRepository.findAlls(type,projectId,productId,pageable);
        List<Map> mapList2=new LinkedList<>();
        for(int i = 0; i < page.getContent().size(); i++){
           Map<String, Object> one =  page.getContent().get(i);
           HashMap<String, Object> newOne = new HashMap<>();
           List<Map> mapList3 = checkTemplateRepository.selectAccessory(Long.parseLong(one.get("id").toString()));

           newOne.putAll(one);
           newOne.put("accessory",mapList3);
           mapList2.add(newOne);
       }
        Page<Map> List2 = new PageImpl<>(mapList2, pageable, page.getTotalElements());
        return PageUtil.toPage(List2);
    }


    @Override
    public List<Map> selectCheckCardTemplate(Long type) {
        return checkTemplateRepository.selectCheckCardTemplate(type);
    }

    @Override
    public Integer InstAccessory(Long pid, String name, String path, Long uploadTime, String uploadBy) {
        return checkTemplateRepository.InstAccessory(pid,name,path,uploadTime,uploadBy);
    }

    @Override
    public void deleteCheckTemplate(Long id) {
         checkTemplateRepository.deleteCheckTemplate(id);
    }

    @Override
    public List<CheckTemplateDto> queryAll(CheckTemplateQueryCriteria criteria){
        return checkTemplateMapper.toDto(checkTemplateRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public CheckTemplateDto findById(Long id) {
        CheckTemplate checkTemplate = checkTemplateRepository.findById(id).orElseGet(CheckTemplate::new);
        ValidationUtil.isNull(checkTemplate.getId(),"CheckTemplate","id",id);
        return checkTemplateMapper.toDto(checkTemplate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CheckTemplateDto create(CheckTemplate resources) {
        return checkTemplateMapper.toDto(checkTemplateRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CheckTemplate resources) {
        CheckTemplate checkTemplate = checkTemplateRepository.findById(resources.getId()).orElseGet(CheckTemplate::new);
        ValidationUtil.isNull( checkTemplate.getId(),"CheckTemplate","id",resources.getId());
        checkTemplate.copy(resources);
        checkTemplateRepository.save(checkTemplate);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            checkTemplateRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<CheckTemplateDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CheckTemplateDto checkTemplate : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("检验卡片模板id", checkTemplate.getPid());
            map.put("序号", checkTemplate.getNo());
            map.put("工序说明", checkTemplate.getProcess());
            map.put("注", checkTemplate.getNotice());
            map.put("质量要求", checkTemplate.getAsk());
            map.put("检查方法", checkTemplate.getCheckMethod());
            map.put("备注", checkTemplate.getRemark());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}