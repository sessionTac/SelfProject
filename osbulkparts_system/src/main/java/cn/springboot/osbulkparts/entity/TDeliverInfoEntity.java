package cn.springboot.osbulkparts.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class TDeliverInfoEntity implements Serializable {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 订单产品型号
     */
    private String orderCode;

    /**
     * 物料号
     */
    private String materialCode;

    /**
     * 发货数量
     */
    private BigDecimal sendAmount;

    /**
     * 航次
     */
    private String shipNo;

    /**
     * 集装箱号
     */
    private String containerNo;

    /**
     * 合同号
     */
    private String contractNo;

    /**
     * 数据所属
     */
    private String dataRoleAt;

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
     * 逻辑删除
     */
    private Integer isDelete;

    /**
     * 版本
     */
    private Integer version;

    private static final long serialVersionUID = 1L;
}