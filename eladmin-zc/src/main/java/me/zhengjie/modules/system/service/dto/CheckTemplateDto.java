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
import me.zhengjie.modules.system.domain.CheckTemplate;

import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.SqlResultSetMapping;
import java.io.Serializable;

/**
 * @website https://el-admin.vip
 * @description /
 * @author ly
 * @date 2021-02-22
 **/

@Data
public class CheckTemplateDto implements Serializable {

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

    /** 检查方法 */
    private String checkMethod;

    /** 备注 */
    private String remark;
}