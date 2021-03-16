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

import me.zhengjie.modules.system.domain.Unqualified;
import me.zhengjie.modules.system.service.dto.UnqualifiedDto;
import me.zhengjie.modules.system.service.dto.UnqualifiedQueryCriteria;
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
* @date 2021-03-01
**/
public interface UnqualifiedService {


    /**
     * app
     * 根据checkId查询不合格项
     */
    List<Map> findByCheckId(Long checkId);
    /**
     * 新增 不合格项照片
     */
    Integer InstAccessory(Long pid,String name,String path,Long uploadTime,String uploadBy);
    /**
     * app
     * 查询字典表 根据description
     */
    List<Map> selectDict(String  description);
    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(UnqualifiedQueryCriteria criteria, Pageable pageable);

    /**
     * 查询数据分页
     * @param productName
     * @param createBy
     * @param faultName
     * @param faultLevel
     * @param pageable
     * @return
     */
    Map<String,Object> findAlls(String productName, String createBy, String faultName, String faultLevel,Long projectId, Pageable pageable);

    /**
     * 查询所有责任人
     */
    List<Map> findCreateBy();
    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<UnqualifiedDto>
    */
    List<UnqualifiedDto> queryAll(UnqualifiedQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return UnqualifiedDto
     */
    UnqualifiedDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return UnqualifiedDto
    */
    UnqualifiedDto create(Unqualified resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(Unqualified resources);

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
    void download(List<UnqualifiedDto> all, HttpServletResponse response) throws IOException;
}