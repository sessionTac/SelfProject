package cn.springboot.osbulkparts.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class MUserInfoEntity implements Serializable {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户真实姓名
     */
    private String userRealName;

    /**
     * 用户类型   1:系统管理员;2:操作用户;3:客户
     */
    private Integer userType;

    /**
     * 用户状态 0:启用;1:停用;
     */
    private Integer userStatus;

    /**
     * 用户手机
     */
    private String userPhone;

    /**
     * 用户邮箱
     */
    private String userMail;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 创建时间   YYYYMMDDHHmmSS
     */
    private String createTime;

    /**
     * 更新者
     */
    private String updateUser;

    /**
     * 更新时间   YYYYMMDDHHmmSS
     */
    private String updateTime;

    /**
     * 逻辑删除  0：正常  1：删除
     */
    private Integer isDelete;

    /**
     * 版本 
     */
    private Integer version;

    /**
     * 用户类型
     */
    private TDictDataEntity dictUserType;
    /**
     * 用户状态
     */
    private TDictDataEntity dictUserStatus;
    
    /**
     * 用户角色
     */
	private List<MRoleInfoEntity> roleList = new ArrayList<MRoleInfoEntity>();

    private String languageFlag;

    private static final long serialVersionUID = 1L;
}