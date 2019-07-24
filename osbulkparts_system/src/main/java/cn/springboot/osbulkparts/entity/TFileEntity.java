package cn.springboot.osbulkparts.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class TFileEntity implements Serializable {
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
     * 文件名
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 是否删除
     */
    private String isDelete;

    /**
     * 文件有效期
     */
    private String validity;

    /**
     * 文件类型 1：图片
     */
    private Integer fileType;

    private static final long serialVersionUID = 1L;
}