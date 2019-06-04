package cn.springboot.osbulkparts.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class TDictDataEntity implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 字典分类表主键
     */
    private String dictTypeCode;

    /**
     * 字典值
     */
    private String value;

    /**
     * 字典名称
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
     * 删除标识
     */
    private Integer deleteFlg;

    /**
     * 版本号
     */
    private Integer version;

    private static final long serialVersionUID = 1L;
}