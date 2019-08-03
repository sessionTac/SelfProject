package cn.springboot.osbulkparts.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class TPriceFileEntity implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 物料专用号
     */
    private String materialCode;

    /**
     * 供应商编码
     */
    private String supplierCode;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 文件有效期
     */
    private String validity;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件类型 1：图片
     */
    private Integer fileType;

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
     * 是否删除
     */
    private Integer isDelete;

    /**
     * 
     */
    private Integer version;

    private static final long serialVersionUID = 1L;
}