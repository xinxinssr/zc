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

import me.zhengjie.modules.system.domain.Supplier;
import me.zhengjie.modules.system.service.dto.SupplierDto;
import me.zhengjie.modules.system.service.dto.SupplierQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @description 服务接口
* @author ly
* @date 2021-02-01
**/
public interface SupplierService {

    /**
     * 根据名字查询
     * @param name NAME
     * @return SupplierDto
     */
    Integer findByName(String name,String id);

    Integer findByName(String name);

    Map selectByName(String name);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Supplier selectById(String id);

    /**
     * 查询所有供应商
     * @return
     */
    List<Map> selectAll(Long type);

    /**
     * 删除证书
     * @param id
     * @return
     */
    Integer delCertificate(String id);

    /**
     * 导入
     */
    Integer insertAll(List<Supplier> supplierList);
    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(SupplierQueryCriteria criteria, Pageable pageable);

    /**
     * 查询数据分页
     * @param pageable 分页参数
     * @return Map<String,Object>
     */
    Map<String,Object> queryAlls(String name,String productScope,String  supplierBy,Long type, Pageable pageable);

    /**
     * 根据供应商id查询证书
     * @param id
     * @param pageable
     * @return
     */
    Map<String,Object> findBySId(String id,  Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<SupplierDto>
    */
    List<SupplierDto> queryAll(SupplierQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return SupplierDto
     */
    SupplierDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return SupplierDto
    */
    SupplierDto create(Supplier resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(Supplier resources);

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
    void download(List<SupplierDto> all, HttpServletResponse response) throws IOException;
}