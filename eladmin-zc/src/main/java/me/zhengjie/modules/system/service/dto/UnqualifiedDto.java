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
* @date 2021-03-01
**/
@Data
public class UnqualifiedDto implements Serializable {

    /** 自增id */
    private Long id;

    /**供应商id*/
    private Long supplierId;

    /** 检查项id */
    private Long checkId;

    /** 追溯号 */
    private String trackNo;

    /** 故障类型id */
    private Long faultId;

    /** 故障类型名称 */
    private String faultName;

    /** 故障等级 */
    private Integer faultLevel;

    /** 创建人 */
    private String createBy;

    /** 修改时间 */
    private Long updateTime;

    /** 描述 */
    private String describe;

    /** 0 已解决 / 1 未解决 */
    private Integer isSolve;

    /** 解决人 */
    private String solveBy;

    /** 解决时间 */
    private Long solveTime;

    /** 解决人描述 */
    private String solveDescribe;

    /** 0正常 / 1 删除 */
    private Integer state;
}