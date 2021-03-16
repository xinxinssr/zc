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

import me.zhengjie.modules.system.domain.Check;
import me.zhengjie.modules.system.service.dto.CheckDto;
import me.zhengjie.modules.system.service.dto.CheckQueryCriteria;
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
public interface CheckService {

    /**
     * app
     * 新增检查项
     */
    Integer InstCheck(Long pid,Integer no,String process,String notice,String ask,String checkMethod, String remark,String checkBy,Long checkTime,Integer isGood,Integer isUnqualified);
    /**
     * app
     * 修改检查项
     */
    Integer UpdateCheck(Long id,Long pid,Integer no,String process,String notice,String ask,String checkMethod,String remark,String checkBy,Long checkTime,Integer isGood,Integer isUnqualified);

    /**
     * app
     * 根据卡片id查询检查项
     */
    List<Map>  selectCheck(Long pid);
    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(CheckQueryCriteria criteria, Pageable pageable);

    /**
     *根据pid查询check
     */
    Map<String,Object> findAlls(String pid, Pageable pageable);
    /**
     * 根据pid查询关联产品
     */
    List<Map>  selectProductP(Long pid);
    /**
     * 根据pid查询关联原材料
     */
    List<Map>  selectProductBatch(Long pid);
    /**
     * 根据pid查询关联供应商
     */
    List<Map>  selectProductSupplier(Long pid);
    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<CheckDto>
    */
    List<CheckDto> queryAll(CheckQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return CheckDto
     */
    CheckDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return CheckDto
    */
    CheckDto create(Check resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(Check resources);

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
    void download(List<CheckDto> all, HttpServletResponse response) throws IOException;
    /**
     * 查询合格不合格数量
     */
    List<Map> selectIsGood(String projectId,String productId);
    /**
     * 查询不合格等级
     */
    List<Map> selectUnqualified(String projectId,String productId);
}