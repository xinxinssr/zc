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

import me.zhengjie.modules.system.domain.Certificate;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.repository.CertificateRepository;
import me.zhengjie.modules.system.service.CertificateService;
import me.zhengjie.modules.system.service.dto.CertificateDto;
import me.zhengjie.modules.system.service.dto.CertificateQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.CertificateMapper;
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
* @date 2021-02-04
**/
@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final CertificateMapper certificateMapper;

    @Override
    public Map<String,Object> queryAll(CertificateQueryCriteria criteria, Pageable pageable){
        Page<Certificate> page = certificateRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(certificateMapper::toDto));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer InstCertificate(String supplierId, String name, String path, String typeId, String typeName, String startTime, String endTime, String uploadTime, String creatTime, String creatBy) {
        return certificateRepository.InstCertificate(supplierId,name,path,typeId,typeName,startTime,endTime,uploadTime,creatTime,creatBy);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer UpdateCertificate(String Id, String name, String path, String typeId, String typeName, String startTime, String endTime, String uploadTime, String updateTime, String updateBy) {
        return certificateRepository.UpdateCertificate(Id,name,path,typeId,typeName,startTime,endTime,uploadTime,updateTime,updateBy);
    }

    @Override
    public List<CertificateDto> queryAll(CertificateQueryCriteria criteria){
        return certificateMapper.toDto(certificateRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public CertificateDto findById(Long id) {
        Certificate certificate = certificateRepository.findById(id).orElseGet(Certificate::new);
        ValidationUtil.isNull(certificate.getId(),"Certificate","id",id);
        return certificateMapper.toDto(certificate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CertificateDto create(Certificate resources) {
        return certificateMapper.toDto(certificateRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Certificate resources) {
        Certificate certificate = certificateRepository.findById(resources.getId()).orElseGet(Certificate::new);
        ValidationUtil.isNull( certificate.getId(),"Certificate","id",resources.getId());
        certificate.copy(resources);
        certificateRepository.save(certificate);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            certificateRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<CertificateDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CertificateDto certificate : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("供应商id", certificate.getSupplierId());
            map.put("证书名称", certificate.getName());
            map.put("证书类别id", certificate.getTypeId());
            map.put("证书类别名称", certificate.getTypeName());
            map.put("开始时间（时间戳）", certificate.getStartTime());
            map.put("到期时间（时间戳）", certificate.getEndTime());
            map.put("上传时间", certificate.getUploadTime());
            map.put("创建时间", certificate.getCreateTime());
            map.put("创建者", certificate.getCreateBy());
            map.put("更新时间", certificate.getUpdateTime());
            map.put("更新者", certificate.getUpdateBy());
            map.put("0正常 / 1 删除", certificate.getState());
            map.put("图片路径", certificate.getPath());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}