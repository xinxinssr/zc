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
* @date 2021-02-01
**/
@Data
public class SupplierDto implements Serializable {

    /** ID */
    private Long id;

    /** 供应商名称 */
    private String name;

    /**供应商类别*/
    private Long type;

    /** 提供产品名称/范围（公司合格供方目录） */
    private String productScope;

    /** 供应商联系人 */
    private String supplierBy;

    /** 供应商联系电话 */
    private Long supplierPhone;

    /**供应商地址*/
    private String supplierShengshiqu;

    /** 供应商详细地址 */
    private String supplierAddres;

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

//    /** 证书id*/
//    private Integer zsid;
//
//    /**证书类别名称*/
//    private String typeName;
//
//    /**证书名称*/
//    private String zhengshuname;
//
//    /**开始时间*/
//    private Integer  startTime;
//
//    /**到期时间*/
//    private Integer endTime;



}