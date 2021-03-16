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
* @date 2021-02-24
**/
@Entity
@Data
@Table(name="zc_check_card")
public class CheckCard implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "自增id")
    private Long id;

    @Column(name = "name")
    @ApiModelProperty(value = "名称")
    private String name;

    @Column(name = "project_id")
    @ApiModelProperty(value = "项目id")
    private Long projectId;

    @Column(name = "product_id")
    @ApiModelProperty(value = "产品id")
    private Long productId;

    @Column(name = "factory_code")
    @ApiModelProperty(value = "出厂编号")
    private String factoryCode;

    @Column(name = "sri")
    @ApiModelProperty(value = "sri列数")
    private Integer sri;

    @Column(name = "type")
    @ApiModelProperty(value = "0 电器 / 1 焊接 / 2机械")
    private Integer type;

    @Column(name = "approval")
    @ApiModelProperty(value = "0审批中/1审批通过/2不通过")
    private Integer approval;

    @Column(name = "approval_by")
    @ApiModelProperty(value = "审批人")
    private String approvalBy;

    @Column(name = "approval_time")
    @ApiModelProperty(value = "审批时间")
    private Long approvalTime;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @Column(name = "create_by")
    @ApiModelProperty(value = "创建人")
    private String createBy;

    @Column(name = "update_time")
    @ApiModelProperty(value = "修改时间")
    private Long updateTime;

    @Column(name = "update_by")
    @ApiModelProperty(value = "修改人")
    private String updateBy;

    @Column(name = "state")
    @ApiModelProperty(value = "0正常 / 1 删除")
    private Integer state;

    public void copy(CheckCard source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}