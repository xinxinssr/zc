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

import me.zhengjie.modules.system.domain.CheckCard;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.CheckCardRepository;
import me.zhengjie.modules.system.service.CheckCardService;
import me.zhengjie.modules.system.service.dto.CheckCardDto;
import me.zhengjie.modules.system.service.dto.CheckCardQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.CheckCardMapper;
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
* @date 2021-02-24
**/
@Service
@RequiredArgsConstructor
public class CheckCardServiceImpl implements CheckCardService {

    private final CheckCardRepository checkCardRepository;
    private final CheckCardMapper checkCardMapper;

    @Override
    public Map selectCheckCard(Long projectId, Long productId, Integer sri, String factoryCode, Long type) {
        return checkCardRepository.selectCheckCard(projectId,productId,sri,factoryCode,type);
    }

    @Override
    public Map<String, Object> selectByApproval(Long type, Long approval, Pageable pageable) {
        Page<Map> page = checkCardRepository.selectByApproval(type,approval,pageable);

        return PageUtil.toPage(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateApproval(Long id, Integer approval, String approvalBy, Long approvalTime) {
        return checkCardRepository.updateApproval(id,approval,approvalBy,approvalTime);
    }

    @Override
    public List<Map> selectCheckCardByproductId(Long productId) {
        return checkCardRepository.selectCheckCardByproductId(productId);
    }

    @Override
    public Map<String,Object> queryAll(CheckCardQueryCriteria criteria, Pageable pageable){
        Page<CheckCard> page = checkCardRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(checkCardMapper::toDto));
    }

    @Override
    public Map<String, Object>  findAlls(String type, String platformName, String projectId, String productId, String factoryCode, String sri, Pageable pageable) {
        Page<Map> page = checkCardRepository.findAlls(type,platformName,projectId,productId,factoryCode,sri,pageable);

        return PageUtil.toPage(page);
    }

    @Override
    public List<CheckCardDto> queryAll(CheckCardQueryCriteria criteria){
        return checkCardMapper.toDto(checkCardRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public CheckCardDto findById(Long id) {
        CheckCard checkCard = checkCardRepository.findById(id).orElseGet(CheckCard::new);
        ValidationUtil.isNull(checkCard.getId(),"CheckCard","id",id);
        return checkCardMapper.toDto(checkCard);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CheckCardDto create(CheckCard resources) {
        return checkCardMapper.toDto(checkCardRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(CheckCard resources) {
        CheckCard checkCard = checkCardRepository.findById(resources.getId()).orElseGet(CheckCard::new);
        ValidationUtil.isNull( checkCard.getId(),"CheckCard","id",resources.getId());
        checkCard.copy(resources);
        checkCardRepository.save(checkCard);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            checkCardRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<CheckCardDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CheckCardDto checkCard : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("名称", checkCard.getName());
            map.put("项目id", checkCard.getProjectId());
            map.put("产品id", checkCard.getProductId());
            map.put("出厂编号", checkCard.getFactoryCode());
            map.put("sri列数", checkCard.getSri());
            map.put("0 电器 / 1 焊接 / 2机械", checkCard.getType());
            map.put("创建时间", checkCard.getCreateTime());
            map.put("创建人", checkCard.getCreateBy());
            map.put("修改时间", checkCard.getUpdateTime());
            map.put("修改人", checkCard.getUpdateBy());
            map.put("0正常 / 1 删除", checkCard.getState());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}