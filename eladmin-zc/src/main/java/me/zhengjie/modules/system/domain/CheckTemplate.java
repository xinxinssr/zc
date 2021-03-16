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
import java.util.List;

/**
 * @website https://el-admin.vip
 * @description /
 * @author ly
 * @date 2021-02-22
 **/

@Entity
@Data
@Table(name="zc_check_template")
public class CheckTemplate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "自增id")
    private Long id;

    @Column(name = "pid")
    @ApiModelProperty(value = "检验卡片模板id")
    private Long pid;

    @Column(name = "no")
    @ApiModelProperty(value = "序号")
    private Integer no;

    @Column(name = "process")
    @ApiModelProperty(value = "工序说明")
    private String process;

    @Column(name = "notice")
    @ApiModelProperty(value = "注")
    private String notice;

    @Column(name = "ask")
    @ApiModelProperty(value = "质量要求")
    private String ask;

    @Column(name = "check_method")
    @ApiModelProperty(value = "检查方法")
    private String checkMethod;

    @Column(name = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;



    public void copy(CheckTemplate source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}