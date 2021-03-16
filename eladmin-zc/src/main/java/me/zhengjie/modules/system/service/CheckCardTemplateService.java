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

import me.zhengjie.modules.system.domain.CheckCardTemplate;
import me.zhengjie.modules.system.domain.CheckTemplate;
import me.zhengjie.modules.system.service.dto.CheckCardTemplateDto;
import me.zhengjie.modules.system.service.dto.CheckCardTemplateQueryCriteria;
import me.zhengjie.modules.system.service.dto.CheckTemplateDto;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @description 服务接口
* @author ly
* @date 2021-02-22
**/
public interface CheckCardTemplateService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(CheckCardTemplateQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<CheckCardTemplateDto>
    */
    List<CheckCardTemplateDto> queryAll(CheckCardTemplateQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return CheckCardTemplateDto
     */
    CheckCardTemplateDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return CheckCardTemplateDto
    */
    CheckCardTemplateDto create(CheckCardTemplate resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(CheckCardTemplate resources);

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
    void download(List<CheckCardTemplateDto> all, HttpServletResponse response) throws IOException;

    /**
     * 新增一条
     */
    Integer InstCheckCard(Long type,Long projectId,String project,Long productId,String product,String name, Long createTime,String createBy);

    /**
     * 根据名字查询最新添加的卡片id
     */
    Long selectCheckCard(String name);
    /**
     * 根据pid查询检验模板
     */
    List<Map> selectTemplateByPid(Long pid);

    /**
     * 增加检验模板
     */
    Integer InstCheckTemplate(Long pid,Integer no,String process,String notice,String ask,String checkMethod, String remark);

    /**
     * 查询检验卡片id
     * @param projectId
     * @param productId
     * @return
     */
    Long selectCheckCardId(Long projectId,Long productId,Long type);

    /**
     * 删除检验模板
     */
    void deleteCheckTemplate(Long id);

    /**
     * 导入检验模板
     */
    Integer insertAll(List<CheckTemplate> checkTemplateList);
    /**
     * 修改检验卡片
     */
    Integer updateCheckCard(Long id,Long type,Long projectId,String project,Long productId,String product,String name, Long createTime,String createBy);

    /**
     * 查询检验项
     */
     Map findCheckTemplate(Long id);
}