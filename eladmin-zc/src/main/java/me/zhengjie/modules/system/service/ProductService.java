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

import me.zhengjie.modules.system.domain.Product;
import me.zhengjie.modules.system.domain.Project;
import me.zhengjie.modules.system.service.dto.ProductDto;
import me.zhengjie.modules.system.service.dto.ProductQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @description 服务接口
* @author ly
* @date 2021-02-19
**/
public interface ProductService {
    /**
     * 查询数据分页
     * @param pageable 分页参数
     * @return Map<String,Object>
     */
    Map<String,Object> queryAlls(String type,String platformName,String projectId,String name, Pageable pageable);

    /**
     * 新增产品
     */
    Integer InstProduct(Long type,Long projectId,String name,String platformName,String productCode,String specification, Long creatTime,String creatBy);
    /**
     * 修改产品
     */
    Integer updateProduct(Long id,Long projectId,String name,String platformName,String productCode,String specification, Long updateTime,String updateBy);

    /**
     * 新增项目
     */
    Integer InstProject(Long type,String name,Long creatTime,String creatBy);
    /**
     * 根据项目名称查询项目是否存在
     */
    Map selectByname(String name,Long type);

    /**
     * 根据type和名字查询项目是否存在
     * @param type
     * @param name
     * @return
     */
    Long selectProjectByName(Long type,String name);

    /**
     * 根据项目id和产品名字查询产品是否存在
      * @param id
     * @param name
     * @return
     */
    Long selectProductByName(Long id,String name);

    /**
     * 查询所有项目
     */
    List<Map>  selectAllProject(Long type);

    /**
     * 根据项目id 查询产品
     */
    List<Map> selectAllProduct(String projectId);
    /**
     * 查询产品去重
     */
    List<Map> selectAllProduct2();
    /**
     * 查询项目 去重
     */
    List<Map> selectAllProject2();
    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(ProductQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<ProductDto>
    */
    List<ProductDto> queryAll(ProductQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return ProductDto
     */
    ProductDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return ProductDto
    */
    ProductDto create(Product resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(Product resources);

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
    void download(List<ProductDto> all, HttpServletResponse response) throws IOException;
}