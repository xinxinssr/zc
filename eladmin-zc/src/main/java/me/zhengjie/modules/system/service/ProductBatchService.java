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

import me.zhengjie.modules.system.domain.ProductBatch;
import me.zhengjie.modules.system.service.dto.ProductBatchDto;
import me.zhengjie.modules.system.service.dto.ProductBatchQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @description 服务接口
* @author ly
* @date 2021-03-15
**/
public interface ProductBatchService {

    /**
     * 根据产品id查询关联的原材料
     */
    List<Map> selectProductBatch(Long checkCardId);

    /**
     * 产品关联原材料
     */
    Map InstProductBatch(List<ProductBatch> productBatchList);

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(ProductBatchQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<ProductBatchDto>
    */
    List<ProductBatchDto> queryAll(ProductBatchQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return ProductBatchDto
     */
    ProductBatchDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return ProductBatchDto
    */
    ProductBatchDto create(ProductBatch resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(ProductBatch resources);

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
    void download(List<ProductBatchDto> all, HttpServletResponse response) throws IOException;
}