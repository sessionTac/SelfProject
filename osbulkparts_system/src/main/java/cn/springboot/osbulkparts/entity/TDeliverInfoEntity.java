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
     * 提单号
     */
    private String billNo;

    /**
     * 运输方式
     */
    private String transportation;
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
     * 物料数据信息
     */
    private MMaterialInfoEntity materialInfoEntity;

    /**
     * 订单计划信息信息
     */
    private TOrderDetailInfoEntity orderDetailInfo;

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
     * 发货状态dictGoodsStatus
     */
    private TDictDataEntity dictGoodsStatus;

    /**
     * 运输方式 dictTransportation
     */
    private TDictDataEntity dictTransportation;
    /**
     * 发货状态
     */
    private String state;

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