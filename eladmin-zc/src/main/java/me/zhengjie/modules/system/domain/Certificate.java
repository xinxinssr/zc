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
* @date 2021-02-04
**/
@Entity
@Data
@Table(name="zc_certificate")
public class Certificate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "ID")
    private Long id;

    @Column(name = "supplier_id")
    @ApiModelProperty(value = "供应商id")
    private Long supplierId;

    @Column(name = "name")
    @ApiModelProperty(value = "证书名称")
    private String name;

    @Column(name = "type_id")
    @ApiModelProperty(value = "证书类别id")
    private Long typeId;

    @Column(name = "type_name")
    @ApiModelProperty(value = "证书类别名称")
    private String typeName;

    @Column(name = "start_time")
    @ApiModelProperty(value = "开始时间（时间戳）")
    private Long startTime;

    @Column(name = "end_time")
    @ApiModelProperty(value = "到期时间（时间戳）")
    private Long endTime;

    @Column(name = "upload_time")
    @ApiModelProperty(value = "上传时间")
    private Long uploadTime;

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

    @Column(name = "path")
    @ApiModelProperty(value = "图片路径")
    private String path;

    public void copy(Certificate source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}