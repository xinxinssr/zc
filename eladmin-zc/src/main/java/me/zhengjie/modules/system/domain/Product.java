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
* @date 2021-02-19
**/
@Entity
@Data
@Table(name="zc_product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "ID")
    private Long id;

    @Column(name = "type",nullable = false)
    @NotNull
    @ApiModelProperty(value = "0 电气 / 1 焊接 / 2机械")
    private Long type;

    @Column(name = "project_id")
    @ApiModelProperty(value = "项目id")
    private Long projectId;

    @Column(name = "name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "产品名称")
    private String name;

    @Column(name = "platform_id")
    @ApiModelProperty(value = "项目平台id")
    private Long platformId;

    @Column(name = "platform_name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "项目平台名称")
    private String platformName;

    @Column(name = "product_code")
    @ApiModelProperty(value = "编号")
    private String productCode;

    @Column(name = "specification")
    @ApiModelProperty(value = "规格")
    private String specification;

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

    public void copy(Product source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}