package cn.springboot.osbulkparts.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class TOrderDetailInfoEntity implements Serializable {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 订单产品型号
     */
    private String orderCode;

    /**
     * 订单产品型号描述
     */
    private String orderCodeDesc;

    /**
     * 订单数量
     */
    private BigDecimal orderAmount;

    /**
     * 订单日期
     */
    private String orderDate;
    
    /**
     * 订单日期开始
     */
    private String orderDateStart;
    
    /**
     * 订单日期结束
     */
    private String orderDateEnd;

    /**
     * 订单型号单位
     */
    private String orderUnit;

    /**
     * 订单号 格式：2019070100001
     */
    private String orderId;

    /**
     * 订单行项目
     */
    private String orderIdItem;

    /**
     * 物料号
     */
    private String materialCode;

    /**
     * 物料中文描述
     */
    private String materialDescCn;

    /**
     * 物料英文描述
     */
    private String materialDescEn;

    /**
     * 物料俄文描述
     */
    private String materialDescRn;

    /**
     * 物料单位
     */
    private String materialUnit;

    /**
     * 物料数量
     */
    private BigDecimal materialAmount;

    /**
     * 物料类别
     */
    private String materialCategory;

    /**
     * 换算关系
     */
    private String materialRelation;

    /**
     * 换算后单位
     */
    private String materialRelationUnit;

    /**
     * 换算后数量
     */
    private BigDecimal materialRelationQuantity;

    /**
     * 最小包装类型
     */
    private String materialMinpackageType;

    /**
     * 最小包装数量
     */
    private BigDecimal materialMinpackageAmt;

    /**
     * 最小包装总量
     */
    private BigDecimal materialMinpackageTotalamt;

    /**
     * 未税单价
     */
    private BigDecimal materialTaxPrice;

    /**
     * 未税总价
     */
    private BigDecimal materialTaxTotalprice;

    /**
     * 含税单价
     */
    private BigDecimal materialVatPrice;

    /**
     * 含税总价
     */
    private BigDecimal materialVatTotalprice;
    
    /**
     * 供应商编码
     */
    private String materialSupplierNo;
    
    /**
     * 税率
     */
    private BigDecimal tax;

    /**
     * 代理费率
     */
    private BigDecimal materialRate;

    /**
     * 币种
     */
    private String materialCurrency;

    /**
     * 国家标志
     */
    private String countryCode;

    /**
     * 状态
     */
    private String confirmStatus;

    /**
     * 型号发货总数量
     */
    private BigDecimal orderOutTotalAmount;

    /**
     * 子件发货总数量
     */
    private BigDecimal materOutTotalAmount;

    /**
     * 剩余数量
     */
    private BigDecimal residualAmount;

    /**
     * 调整后数量
     */
    private BigDecimal trimAmount;

    /**
     * 库存数量
     */
    private BigDecimal stockAmount;

    /**
     * 差异数量
     */
    private BigDecimal differAmount;

    /**
     * 收货数量
     */
    private BigDecimal takeOverAmount;

    /**
     * 发货数量
     */
    private BigDecimal deliveryAmount;

    /**
     * 剩余数量
     */
    private BigDecimal surplusAmount;
    
    /**
     * 超发数量
     */
    private TMaterialRecordInfoEntity supperAmountQut;

    /**
     * 时间标识
     */
    private String dateFlag;

    /**
     * 数据所属
     */
    private String dataRoleAt;
    
    /**
     * 是否平衡数据
     */
    private String isBalance;

    /**
     * 订单详细类型
     */
    private String orderDetailType;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 创建时间
     */
    private String createTime;

    private String createTimeStart;

    private String createTimeEnd;

    /**
     * 更新者
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private String updateTime;

    private String updateTimeStart;

    private String updateTimeEnd;

    /**
     * 逻辑删除
     */
    private Integer isDelete;

    /**
     * 版本
     */
    private Integer version;
    /**
     * 订单型号单位
     */
    private TDictDataEntity dictOrderUnit;

    /**
     * 物料单位
     */
    private TDictDataEntity dictMaterialUnit;

    /**
     * 物料类别
     */
    private TDictDataEntity dictMaterialCategory;

    /**
     * 换算后单位
     */
    private TDictDataEntity dictRelationUnit;

    /**
     * 包装类型
     */
    private TDictDataEntity dictMinPackageType;

    /**
     * 币种
     */
    private TDictDataEntity dictMaterialCurrency;

    /**
     * 国家标志
     */
    private TDictDataEntity dictCountryCode;

    /**
     * 状态
     */
    private TDictDataEntity dictConfirmStatus;

    /**
     * 订单详细类型
     */
    private TDictDataEntity dictOrderDetailType;

    private static final long serialVersionUID = 1L;
}