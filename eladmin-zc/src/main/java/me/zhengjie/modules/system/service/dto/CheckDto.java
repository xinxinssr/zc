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
* @date 2021-02-24
**/
@Data
public class CheckDto implements Serializable {

    /** 自增id */
    private Long id;

    /** 检验卡片模板id */
    private Long pid;

    /** 序号 */
    private Integer no;

    /** 工序说明 */
    private String process;

    /** 注 */
    private String notice;

    /** 质量要求 */
    private String ask;

    /** 最终检验人 */
    private String checkBy;

    /** 最终检验时间 */
    private Long checkTime;

    /** 0 合格 / 1不合格 */
    private Integer isGood;

    /** 0 无 / 1 包含不合格项 */
    private Integer isUnqualified;

    /** 检查方法 */
    private String checkMethod;

    /** 备注 */
    private String remark;
}