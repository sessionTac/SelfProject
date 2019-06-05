package cn.springboot.osbulkparts.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class MFunctionInfoEntity implements Serializable {
    /**
     * 主键id
     */
    private Integer functionId;

    /**
     * 父节点
     */
    private Integer parentId;

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
    private List<MFunctionInfoEntity> children;

    private static final long serialVersionUID = 1L;
}