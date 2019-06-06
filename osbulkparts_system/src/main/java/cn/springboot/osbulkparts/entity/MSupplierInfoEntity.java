package cn.springboot.osbulkparts.entity;

import java.io.Serializable;
import lombok.Data;

@Data
public class MSupplierInfoEntity implements Serializable {
    /**
     * 供应商编号
     */
    private String supplierId;

    /**
     * 供应商代码
     */
    private String supplierCode;

    /**
     * 供应商中文名称
     */
    private String supplierNameCn;

    /**
     * 供应商英文名称
     */
    private String supplierNameEn;

    /**
     * 供应商中文说明
     */
    private String supplierDescCn;

    /**
     * 供应商英文说明
     */
    private String supplierDescEn;

    /**
     * 地址
     */
    private String address;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 开户银行
     */
    private String accountBank;

    /**
     * 开户银行地址
     */
    private String bankAddress;

    /**
     * 帐号信息
     */
    private String accountNo;

    /**
     * 账户人名
     */
    private String accountant;

    /**
     * 联系方式
     */
    private String contactWays;

    /**
     * 供应商分类
     */
    private String supplierCata;

    /**
     * 供应商等级
     */
    private String supplierLevel;

    /**
     * 供应商所属
     */
    private String supplierAt;

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
          * 供应商分类
     */
    private TDictDataEntity dictSupplierCata;
    
    /**
          * 供应商等级
     */
    private TDictDataEntity dictSupplierLevel;
    
    /**
          * 供应商所属
     */
    private TDictDataEntity dictSupplierAt;

    private static final long serialVersionUID = 1L;
}