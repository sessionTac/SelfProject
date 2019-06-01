package cn.springboot.osbulkparts.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class MFunctionInfoEntity implements Serializable {
    /**
     * 主键id
     */
    private String functionId;

    /**
     * 父节点
     */
    private String parentId;

    /**
     * 权限名称
     */
    private String functionName;

    /**
     * 权限代码
     */
    private String functionCode;

    /**
     * 权限描述
     */
    private String functionDesc;

    /**
     * 菜单排序
     */
    private Integer priority;

    /**
     * 所属菜单
     */
    private String menuId;

    /**
     * 
     */
    private String updateUser;

    /**
     * 
     */
    private String updateTime;

    /**
     * 
     */
    private String createUser;

    /**
     * 
     */
    private String createTime;

    /**
     * 逻辑删除
     */
    private Integer isDelete;

    /**
     * 
     */
    private Integer version;

    private static final long serialVersionUID = 1L;
}