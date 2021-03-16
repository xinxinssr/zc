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
* @date 2021-02-19
**/
@Data
public class ProductDto implements Serializable {

    /** ID */
    private Long id;

    /** 0 电气 / 1 焊接 / 2机械 */
    private Long type;

    /** 项目id */
    private Long projectId;

    /** 产品名称 */
    private String name;

    /**项目名称*/
    private String projectName;
    /** 项目平台id */
    private Long platformId;

    /** 项目平台名称 */
    private String platformName;

    /** 编号 */
    private String productCode;


    /** 规格 */
    private String specification;

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
}