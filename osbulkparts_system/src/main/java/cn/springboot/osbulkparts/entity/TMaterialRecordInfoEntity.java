package cn.springboot.osbulkparts.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class TMaterialRecordInfoEntity implements Serializable {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 物料号
     */
    private String materialCode;

    /**
     * 超发数量
     */
    private BigDecimal materialAmount;

    /**
     * 数据所属
     */
    private String dataRoleAt;

    /**
     * 代理商
     */
    private String factoryCode;

    private static final long serialVersionUID = 1L;
}