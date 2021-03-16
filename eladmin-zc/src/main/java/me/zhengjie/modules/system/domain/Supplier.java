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
import javax.persistence.CascadeType;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
* @website https://el-admin.vip
* @description /
* @author ly
* @date 2021-02-01
**/
@Entity
@Data
@Table(name="zc_supplier")

public class Supplier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "ID")
    private Long id;

    @Column(name = "name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "供应商名称")
    private String name;

    @Column(name = "type")
    @ApiModelProperty(value = "供应商类别")
    private Long type;

    @Column(name = "product_scope")
    @ApiModelProperty(value = "提供产品名称/范围（公司合格供方目录）")
    private String productScope;

    @Column(name = "supplier_by")
    @ApiModelProperty(value = "供应商联系人")
    private String supplierBy;

    @Column(name = "supplier_phone")
    @ApiModelProperty(value = "供应商联系电话")
    private Long supplierPhone;

    @Column(name = "supplier_shengshiqu")
    @ApiModelProperty(value = "供应商地址")
    private String supplierShengshiqu;

    @Column(name = "supplier_addres")
    @ApiModelProperty(value = "供应商详细地址")
    private String supplierAddres;

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


//    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval=true)
//    @JoinColumn(name="id")
//    private Certificate certificate;


//    @OneToMany(cascade = {CascadeType.REFRESH,CascadeType.REMOVE})
//    private Set<Certificate> Org= new HashSet<Certificate>();
//    public Set<Certificate> getOrg() {
//        return Org;
//    }
//    @Column(name="id", table="zc_certificate",insertable =false,updatable = false)
//    private Long zsid;
//    @Column(name="type_name", table="zc_certificate")
//    private String typeName;
//    @Column(name="name", table="zc_certificate")
//    private String zhengshuname;


    public void copy(Supplier source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}