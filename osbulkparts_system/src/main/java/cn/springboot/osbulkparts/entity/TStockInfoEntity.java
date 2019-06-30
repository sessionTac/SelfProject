package cn.springboot.osbulkparts.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class TStockInfoEntity implements Serializable {
    /**
     * 主键ID
     */
    private String id;

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
     * 库存数量
     */
    private BigDecimal stockAmount;

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
 	* 物料类别
	*/
	private TDictDataEntity dictMaterialCategory;

    /**
     * 版本
     */
    private Integer version;

    private static final long serialVersionUID = 1L;
}