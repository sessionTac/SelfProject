package cn.springboot.osbulkparts.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class TRoleFunctionRelationEntity implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 权限id
     */
    private String functionId;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 逻辑删除  默认‘0’
     */
    private Integer isDelete;

    /**
     * 排他版本号
     */
    private Integer version;

    private static final long serialVersionUID = 1L;
}