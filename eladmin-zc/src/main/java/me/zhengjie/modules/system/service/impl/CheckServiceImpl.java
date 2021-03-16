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

import me.zhengjie.modules.system.domain.Check;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.CheckRepository;
import me.zhengjie.modules.system.service.CheckService;
import me.zhengjie.modules.system.service.dto.CheckDto;
import me.zhengjie.modules.system.service.dto.CheckQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.CheckMapper;
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
* @date 2021-02-24
**/
@Service
@RequiredArgsConstructor
public class CheckServiceImpl implements CheckService {

    private final CheckRepository checkRepository;
    private final CheckMapper checkMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer InstCheck(Long pid, Integer no, String process, String notice, String ask, String checkMethod, String remark, String checkBy, Long checkTime, Integer isGood, Integer isUnqualified) {
        return checkRepository.InstCheck(pid,no,process,notice,ask,checkMethod,remark,checkBy,checkTime,isGood,isUnqualified);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer UpdateCheck(Long id, Long pid, Integer no, String process, String notice, String ask, String checkMethod, String remark, String checkBy, Long checkTime, Integer isGood, Integer isUnqualified) {
        return checkRepository.UpdateCheck(id,pid,no,process,notice,ask,checkMethod,remark,checkBy,checkTime,isGood,isUnqualified);
    }

    @Override
    public List<Map> selectCheck(Long pid) {
        return checkRepository.selectCheck(pid);
    }

    @Override
    public Map<String,Object> queryAll(CheckQueryCriteria criteria, Pageable pageable){
        Page<Check> page = checkRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(checkMapper::toDto));
    }

    @Override
    public Map<String,Object> findAlls(String pid, Pageable pageable) {
        Page<Map> page = checkRepository.findAlls(pid,pageable);

        return PageUtil.toPage(page);
    }

    @Override
    public List<Map> selectProductP(Long pid) {
        return checkRepository.selectProductP(pid);
    }

    @Override
    public List<Map> selectProductBatch(Long pid) {
        return checkRepository.selectProductBatch(pid);
    }

    @Override
    public List<Map> selectProductSupplier(Long pid) {
        return checkRepository.selectProductSupplier(pid);
    }

    @Override
    public List<CheckDto> queryAll(CheckQueryCriteria criteria){
        return checkMapper.toDto(checkRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public CheckDto findById(Long id) {
        Check check = checkRepository.findById(id).orElseGet(Check::new);
        ValidationUtil.isNull(check.getId(),"Check","id",id);
        return checkMapper.toDto(check);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CheckDto create(Check resources) {
        return checkMapper.toDto(checkRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Check resources) {
        Check check = checkRepository.findById(resources.getId()).orElseGet(Check::new);
        ValidationUtil.isNull( check.getId(),"Check","id",resources.getId());
        check.copy(resources);
        checkRepository.save(check);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            checkRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<CheckDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CheckDto check : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("检验卡片模板id", check.getPid());
            map.put("序号", check.getNo());
            map.put("工序说明", check.getProcess());
            map.put("注", check.getNotice());
            map.put("质量要求", check.getAsk());
            map.put("最终检验人", check.getCheckBy());
            map.put("最终检验时间", check.getCheckTime());
            map.put("0 合格 / 1不合格", check.getIsGood());
            map.put("0 无 / 1 包含不合格项", check.getIsUnqualified());
            map.put("检查方法", check.getCheckMethod());
            map.put("备注", check.getRemark());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public List<Map> selectIsGood(String projectId, String productId) {
        return checkRepository.selectIsGood(projectId,productId);
    }

    @Override
    public  List<Map> selectUnqualified(String projectId, String productId) {
        return checkRepository.selectUnqualified(projectId,productId);
    }
}