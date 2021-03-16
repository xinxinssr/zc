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
package me.zhengjie.modules.system.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author ly
* @date 2021-03-01
**/
@Entity
@Data
@Table(name="zc_unqualified")
public class Unqualified implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "自增id")
    private Long id;

    @Column(name = "supplier_id")
    @ApiModelProperty(value = "供应商id")
    private Long supplierId;

    @Column(name = "check_id")
    @ApiModelProperty(value = "检查项id")
    private Long checkId;

    @Column(name = "track_no")
    @ApiModelProperty(value = "追溯号")
    private String trackNo;

    @Column(name = "fault_id")
    @ApiModelProperty(value = "故障类型id")
    private Long faultId;

    @Column(name = "fault_name")
    @ApiModelProperty(value = "故障类型名称")
    private String faultName;

    @Column(name = "fault_level")
    @ApiModelProperty(value = "故障等级")
    private Integer faultLevel;

    @Column(name = "create_by")
    @ApiModelProperty(value = "创建人")
    private String createBy;

    @Column(name = "update_time")
    @ApiModelProperty(value = "修改时间")
    private Long updateTime;

    @Column(name = "describe")
    @ApiModelProperty(value = "描述")
    private String describe;

    @Column(name = "is_solve")
    @ApiModelProperty(value = "0 已解决 / 1 未解决")
    private Integer isSolve;

    @Column(name = "solve_by")
    @ApiModelProperty(value = "解决人")
    private String solveBy;

    @Column(name = "solve_time")
    @ApiModelProperty(value = "解决时间")
    private Long solveTime;

    @Column(name = "solve_describe")
    @ApiModelProperty(value = "解决人描述")
    private String solveDescribe;

    @Column(name = "state")
    @ApiModelProperty(value = "0正常 / 1 删除")
    private Integer state;

    public void copy(Unqualified source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}