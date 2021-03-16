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
package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.CheckCard;
import me.zhengjie.modules.system.service.dto.CheckCardDto;
import me.zhengjie.modules.system.service.dto.CheckCardQueryCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @description 服务接口
* @author ly
* @date 2021-02-24
**/
public interface CheckCardService {

    /**
     * app
     * 查询检查卡片id
     */
    Map selectCheckCard(Long projectId,Long productId,Integer sri,String factoryCode,Long type);

    /**
     * app
     * 根据审批查询数据
     */
    Map<String,Object> selectByApproval(Long type,Long approval,Pageable pageable);
    /**
     * app
     * 审批检查卡片
     */
    Integer updateApproval(Long id,Integer approval,String approvalBy,Long approvalTime);
    /**
     * app 根据产品id查询产品详情
     */
    List<Map> selectCheckCardByproductId(Long productId);
    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(CheckCardQueryCriteria criteria, Pageable pageable);


    /**
     * 分页查询数据
     * @param type
     * @param platformName
     * @param projectId
     * @param productId
     * @param factoryCode
     * @param sri
     * @param pageable
     * @return
     */
    Map<String, Object> findAlls(String type, String platformName, String projectId, String productId, String factoryCode, String sri, Pageable pageable);
    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<CheckCardDto>
    */
    List<CheckCardDto> queryAll(CheckCardQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return CheckCardDto
     */
    CheckCardDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return CheckCardDto
    */
    CheckCardDto create(CheckCard resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(CheckCard resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Long[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<CheckCardDto> all, HttpServletResponse response) throws IOException;


}