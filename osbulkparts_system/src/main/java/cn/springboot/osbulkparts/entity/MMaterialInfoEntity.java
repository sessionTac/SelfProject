package cn.springboot.osbulkparts.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class MMaterialInfoEntity implements Serializable {
    /**
     * 主键ID
     */
    private String materialInfoId;

    /**
     * 订单号(订单型号，成品型号)
     */
    private String materialOrderCode;
    
    /**
     * 订单型号描述，成品型号描述
     */
    private String materialOrderCodeDesc;

    /**
     * 物料HNR号
     */
    private String materialHnrCode;

    /**
     * 物料CKD号（ckd型号编码）
     */
    private String materialCkdCode;

    /**
     * 物料号
     */
    private String materialCode;

    /**
     * 物料类别
     */
    private String materialCategory;

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
     * HS海关编码
     */
    private String hsNo;

    /**
     * 供应商编号
     */
    private String supplierCode;

    /**
     * 单位
     */
    private String materialUnit;

    /**
     * 换算关系
     */
    private String materialRelation;

    /**
     * 换算后单位
     */
    private String materialRelationUnit;

    /**
     * 最小包装数量
     */
    private Double materialMinpackageAmt;
    
    /**
     * 最小包装类型
     */
    private String materialMinpackageType;

    /**
     * 未税单价
     */
    private BigDecimal materialTaxPrice;

    /**
     * 含税单价
     */
    private BigDecimal materialVatPrice;

    /**
     * 代理费率
     */
    private BigDecimal materialRate;

    /**
     * 币种
     */
    private String materialCurrency;

    /**
     * 单价
     */
    private BigDecimal materialPrice;

    /**
     * 分级BOM编码
     */
    private Integer levelBomCode;

    /**
     * 物料供货模式分类标识
     */
    private String materialSupplyMode;

    /**
     * 工厂号
     */
    private String factoryCode;

    /**
     * 数据所属
     */
    private String dataRoleAt;

    /**
     * 用户自定义1
     */
    private String userDefined1;

    /**
     * 用户自定义2
     */
    private String userDefined2;

    /**
     * 用户自定义3
     */
    private String userDefined3;

    /**
     * 用户自定义4
     */
    private String userDefined4;

    /**
     * 用户自定义5
     */
    private String userDefined5;

    /**
     * 用户自定义6
     */
    private String userDefined6;

    /**
     * 用户自定义7
     */
    private String userDefined7;

    /**
     * 用户自定义8
     */
    private String userDefined8;

    /**
     * 用户自定义9
     */
    private String userDefined9;

    /**
     * 用户自定义10
     */
    private String userDefined10;
    
    /**
     * 用户自定义10
     */
    private Integer isLocked;

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
    
    /**
 	* 物料类别
	*/
	private TDictDataEntity dictMaterialCategory;
	
	/**
	     * 单位
	*/
	private TDictDataEntity dictMaterialUnit;
	
	/**
	* 换算后单位
	*/
	private TDictDataEntity dictMaterialRelationUnit;
	
	/**
        * 币种
	*/
	private TDictDataEntity dictMaterialCurrency;
	
	/**
	 * 数据锁定状态
	 */
	private TDictDataEntity dictLockStatus;
	
	/**
	 * 包装类型
	 */
	private TDictDataEntity dictMinpackageType;
	
	/**
	 * 物料供货模式分类标识
	*/
	private TDictDataEntity dictMaterialSupplyMode;

    private static final long serialVersionUID = 1L;
}