package cn.springboot.osbulkparts.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TOrderInfoEntity implements Serializable {
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
     * 计划状态 0已导入未生成，1已生成关联数据字典中计划状态
     */
    private String orderStatus;
    /**
     * 计划类型
     */
    private String orderType;

    /**
     * 数据所属
     */
    private String dataRoleAt;
    
    /**
     * 是否平衡数据
     */
    private String isBalance;
    
    /**
     * 时间标识(周：week；月：month；年：year）
     */
    private String dateFlag;
    
    /**
     * 创建者
     */
    private String createUser;

    /**
     * 创建时间
     */
    private String createTime;
    
    /**
     * 创建时间开始
     */
    private String createTimeStart;
    
    /**
     * 创建时间结束
     */
    private String createTimeEnd;

    /**
     * 更新者
     */
    private String updateUser;

    /**
     * 更新时间
     */
    private String updateTime;
    
    /**
     * 更新时间开始
     */
    private String updateTimeStart;
    
    /**
     * 更新时间结束
     */
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

//    private ArrayList<MMaterialInfoEntity> materialList;

    private MMaterialInfoEntity materialInfoEntity;
    
    /**
     * 订单状态
     */
    private TDictDataEntity dictOrderStatus;

    /**
     * 订单类型
     */
    private TDictDataEntity dictOrderType;
    private String languageFlag;

    private static final long serialVersionUID = 1L;
}