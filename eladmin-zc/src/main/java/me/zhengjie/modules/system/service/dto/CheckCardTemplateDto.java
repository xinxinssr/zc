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
* @date 2021-02-22
**/
@Data
public class CheckCardTemplateDto implements Serializable {

    /** 自增id */
    private Long id;

    /** 项目id */
    private Long projectId;

    /** 产品id */
    private Long productId;

    /** 名称 */
    private String name;

    /** 0 电器 / 1 焊接 / 2机械 */
    private Integer type;

    /** 创建时间 */
    private Long createTime;

    /** 创建人 */
    private String createBy;

    /** 修改时间 */
    private Long updateTime;

    /** 修改人 */
    private String updateBy;

    /** 0正常 / 1 删除 */
    private Integer state;

    /** 项目名称 */
    private String project;

    /** 产品名称 */
    private String product;
}