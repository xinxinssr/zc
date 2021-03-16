package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import java.io.Serializable;

/**
 * @website https://el-admin.vip
 * @description /
 * @author ly
 * @date 2021-02-20
 **/
@Data
public class ProjectDto implements Serializable {

    /** ID */
    private Long id;

    /**类别*/
    private Long type;
    /** 项目名称 */
    private String name;

    /** 创建时间 */
    private Long createTime;

    /** 创建者 */
    private String createBy;

    /** 0正常 / 1 删除 */
    private Integer state;
}