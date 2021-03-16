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

import me.zhengjie.modules.system.domain.Unqualified;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.UnqualifiedRepository;
import me.zhengjie.modules.system.service.UnqualifiedService;
import me.zhengjie.modules.system.service.dto.UnqualifiedDto;
import me.zhengjie.modules.system.service.dto.UnqualifiedQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.UnqualifiedMapper;
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
* @date 2021-03-01
**/
@Service
@RequiredArgsConstructor
public class UnqualifiedServiceImpl implements UnqualifiedService {

    private final UnqualifiedRepository unqualifiedRepository;
    private final UnqualifiedMapper unqualifiedMapper;

    @Override
    public List<Map> findByCheckId(Long checkId) {
       List<Map> mapList= unqualifiedRepository.findByCheckId(checkId);
        List<Map> mapList2=new LinkedList<>();
        for(int i = 0; i < mapList.size(); i++){
            Map<String, Object> one =  mapList.get(i);
            HashMap<String, Object> newOne = new HashMap<>();
            List<Map> mapList3 = unqualifiedRepository.selectAccessory(Long.parseLong(one.get("id").toString()));
            newOne.putAll(one);
            newOne.put("accessory",mapList3);
            mapList2.add(newOne);
        }

        return mapList2;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer InstAccessory(Long pid, String name, String path, Long uploadTime, String uploadBy) {
        return unqualifiedRepository.InstAccessory(pid,name,path,uploadTime,uploadBy);
    }

    @Override
    public List<Map> selectDict(String description) {
        return unqualifiedRepository.selectDict(description);
    }

    @Override
    public Map<String,Object> queryAll(UnqualifiedQueryCriteria criteria, Pageable pageable){
        Page<Unqualified> page = unqualifiedRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(unqualifiedMapper::toDto));
    }

    @Override
    public Map<String, Object> findAlls(String productName, String createBy, String faultName, String faultLevel, Long projectId,Pageable pageable) {
        Page<Map> page = unqualifiedRepository.findAlls(productName,createBy,faultName,faultLevel,projectId,pageable);
        List<Map> mapList2=new LinkedList<>();
        for(int i = 0; i < page.getContent().size(); i++){
            Map<String, Object> one =  page.getContent().get(i);
            HashMap<String, Object> newOne = new HashMap<>();
            List<Map> mapList3 = unqualifiedRepository.selectAccessory(Long.parseLong(one.get("id").toString()));
            newOne.putAll(one);
            newOne.put("accessory",mapList3);
            mapList2.add(newOne);
          }
        Page<Map> List2 = new PageImpl<>(mapList2, pageable, page.getTotalElements());
        return PageUtil.toPage(List2);
    }

    @Override
    public List<Map> findCreateBy() {
        return unqualifiedRepository.findCreateBy();
    }

    @Override
    public List<UnqualifiedDto> queryAll(UnqualifiedQueryCriteria criteria){
        return unqualifiedMapper.toDto(unqualifiedRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public UnqualifiedDto findById(Long id) {
        Unqualified unqualified = unqualifiedRepository.findById(id).orElseGet(Unqualified::new);
        ValidationUtil.isNull(unqualified.getId(),"Unqualified","id",id);
        return unqualifiedMapper.toDto(unqualified);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UnqualifiedDto create(Unqualified resources) {
        return unqualifiedMapper.toDto(unqualifiedRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Unqualified resources) {
        Unqualified unqualified = unqualifiedRepository.findById(resources.getId()).orElseGet(Unqualified::new);
        ValidationUtil.isNull( unqualified.getId(),"Unqualified","id",resources.getId());
        unqualified.copy(resources);
        unqualifiedRepository.save(unqualified);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            unqualifiedRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<UnqualifiedDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UnqualifiedDto unqualified : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("检查项id", unqualified.getCheckId());
            map.put("追溯号", unqualified.getTrackNo());
            map.put("故障类型id", unqualified.getFaultId());
            map.put("故障类型名称", unqualified.getFaultName());
            map.put("故障等级", unqualified.getFaultLevel());
            map.put("创建人", unqualified.getCreateBy());
            map.put("修改时间", unqualified.getUpdateTime());
            map.put("描述", unqualified.getDescribe());
            map.put("0 已解决 / 1 未解决", unqualified.getIsSolve());
            map.put("解决人", unqualified.getSolveBy());
            map.put("解决时间", unqualified.getSolveTime());
            map.put("解决人描述", unqualified.getSolveDescribe());
            map.put("0正常 / 1 删除", unqualified.getState());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}