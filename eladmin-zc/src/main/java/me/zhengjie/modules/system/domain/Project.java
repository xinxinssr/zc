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
 * @date 2021-02-20
 **/
@Entity
@Data
@Table(name="zc_project")
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "ID")
    private Long id;

    @Column(name = "type")
    @ApiModelProperty(value = "类别")
    private Long type;

    @Column(name = "name")
    @ApiModelProperty(value = "项目名称")
    private String name;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Long createTime;

    @Column(name = "create_by")
    @ApiModelProperty(value = "创建者")
    private String createBy;

    @Column(name = "state")
    @ApiModelProperty(value = "0正常 / 1 删除")
    private Integer state;

    public void copy(Project source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}