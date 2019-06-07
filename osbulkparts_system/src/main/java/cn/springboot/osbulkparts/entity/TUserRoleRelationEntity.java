package cn.springboot.osbulkparts.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class TUserRoleRelationEntity implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新者
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 逻辑删除
     */
    private Integer isDelete;

    /**
     * 角色菜单
     */
    private String privateMenu;

    private MRoleInfoEntity mRoleInfoEntity;

    private static final long serialVersionUID = 1L;
}