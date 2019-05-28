package cn.springboot.osbulkparts.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
public class TUserAttrEntity implements Serializable {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 属性ID
     */
    private String attrId;

    /**
     * 属性值
     */
    private String attrValue;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新者
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 版本号
     */
    private Integer version;

    private static final long serialVersionUID = 1L;
}