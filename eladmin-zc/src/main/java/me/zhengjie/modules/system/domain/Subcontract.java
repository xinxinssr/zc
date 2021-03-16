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
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author ly
* @date 2021-02-05
**/
@Entity
@Data
@Table(name="zc_subcontract")
public class Subcontract implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "ID")
    private Long id;

    @Column(name = "supplier_id")
    @ApiModelProperty(value = "供应商id")
    private Long supplierId;

    @Column(name = "supplier_name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    @Column(name = "supplier_scope")
    @ApiModelProperty(value = "提供产品名称/范围（公司合格供方目录）")
    private String supplierScope;

    @Column(name = "process_scope",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "工序（电气事业部工序承接资质范围）")
    private String processScope;

    @Column(name = "project_scope")
    @ApiModelProperty(value = "项目范围")
    private String projectScope;

    @Column(name = "product_type")
    @ApiModelProperty(value = "产品类别")
    private String productType;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @Column(name = "create_by")
    @ApiModelProperty(value = "创建者")
    private String createBy;

    @Column(name = "update_time")
    @ApiModelProperty(value = "更新时间")
    private Long updateTime;

    @Column(name = "update_by")
    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @Column(name = "state")
    @ApiModelProperty(value = "0正常 / 1 删除")
    private Integer state;

    @Column(name = "product_scope")
    @ApiModelProperty(value = "产品范围")
    private String productScope;

    public void copy(Subcontract source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}