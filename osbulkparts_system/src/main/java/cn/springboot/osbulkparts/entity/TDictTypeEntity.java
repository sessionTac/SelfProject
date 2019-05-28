package cn.springboot.osbulkparts.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class TDictTypeEntity implements Serializable {
    /**
     * 主键
     */
    private String dictTypeId;

    /**
     * 父节点
     */
    private String parentId;

    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 
     */
    private String desc;

    /**
     * 排序
     */
    private Integer sortCode;

    /**
     * 是否有效(0:无效 1:有效)
     */
    private Integer isEnable;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新人
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private String updateTime;

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