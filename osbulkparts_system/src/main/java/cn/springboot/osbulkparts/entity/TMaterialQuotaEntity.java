package cn.springboot.osbulkparts.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class TMaterialQuotaEntity implements Serializable {
    /**
     * 物料号
     */
    private String materialCode;

    /**
     * 供应商代码
     */
    private String supplierCode;

    /**
     * 供应商名称
     */
    private String supplierName;

    /**
     * 配额
     */
    private BigDecimal materialQuota;

    /**
     * 数据所属
     */
    private String dataRoleAt;

    /**
     * 
     */
    private String createUser;

    /**
     * 
     */
    private String createTime;

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
    private Integer isDelete;

    /**
     * 
     */
    private Integer version;

    private static final long serialVersionUID = 1L;
}