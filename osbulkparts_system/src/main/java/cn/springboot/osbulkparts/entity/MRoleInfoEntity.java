package cn.springboot.osbulkparts.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MRoleInfoEntity implements Serializable {
    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色说明
     */
    private String roleDesc;

    /**
     * 角色所属
     */
    private String roleAt;

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
     * 逻辑删除
     */
    private Integer isDelete;

    /**
     * 版本
     */
    private Integer version;

    /**
     * 角色权限列表
     */
    private List<MFunctionInfoEntity> functionList = new ArrayList<MFunctionInfoEntity>();
    
    private static final long serialVersionUID = 1L;
}