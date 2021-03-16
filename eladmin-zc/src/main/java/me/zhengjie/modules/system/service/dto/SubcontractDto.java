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
package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author ly
* @date 2021-02-05
**/
@Data
public class SubcontractDto implements Serializable {

    /** ID */
    private Long id;

    /** 供应商id */
    private Long supplierId;

    /** 供应商名称 */
    private String supplierName;

    /** 提供产品名称/范围（公司合格供方目录） */
    private String supplierScope;

    /** 工序（电气事业部工序承接资质范围） */
    private String processScope;

    /** 项目范围 */
    private String projectScope;

    /** 产品类别 */
    private String productType;

    /** 创建时间 */
    private Long createTime;

    /** 创建者 */
    private String createBy;

    /** 更新时间 */
    private Long updateTime;

    /** 更新者 */
    private String updateBy;

    /** 0正常 / 1 删除 */
    private Integer state;

    /** 产品范围 */
    private String productScope;
}