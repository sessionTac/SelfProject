package cn.springboot.osbulkparts.common;

import java.util.Locale;

public enum CommonConstantEnum {
	LOCK_TRUE("1,1,1"),
	LOCK_FALSE("0,0,0"),
	DICT_TYPE("字典分类,Dictionary classification,словарь"),
	DICT_TYPE_CODE("字典分类编码,DICT TYPE CODE,Кодировка словаря "),
	DICT_DATA("字典数据,Dict Date,Словарные данные "),
    ROLE_NAME("角色名, Role Name,имя роли "),
    ROLE("角色,Role,роль "),
    POWER("权限,Jurisdiction,role,права "),
    USER("用户,User,пользователь "),
    LOSSRATE("损耗,Loss,износ "),
    RATE("税率,Tax Rate,тариф "),
	USER_NAME("用户名,User name,Имя пользователя "),
	SUPPLIER("供应商,Supplier,поставщик "),
	TO_DELETE("1,1,1"),
    TO_APPROVAL("1,1,1"),
	MATERIAL_DATA("物料数据,Material data,материальные данные "),
	MATERIAL_QUOTA_DATA("物料配额数据,Material quota data,данные о материальной квоте "),
	STOCK_DATA("库存数据,Inventory data,складские данные "),
	ORDERINFO_DATA("订单计划信息,Order Planning Information,Информация о планировании заказа "),
    ORDERDETAILINFO_DATA("订单详情信息,Order details,сведения о заказах "),
	ORDER_DATE("订单日期,Order Date, Дата заказа ");
	
	private String typeName;  
	  
	CommonConstantEnum(String typeName) {  
        this.typeName = typeName;  
    }  

    public String getTypeName(Locale lang) {
    	String[] typeLocalName = this.typeName.split(",");
    	if(lang.equals(Locale.SIMPLIFIED_CHINESE)) {
    		return typeLocalName[0];
    	}
    	else if(lang.equals(Locale.US)) {
    		return typeLocalName[1];
    	}
    	else if(lang.equals(Locale.FRANCE)) {
    		return typeLocalName[2];
    	}
		return typeLocalName[0];
    }
    
	public String getTypeName() {
		String[] typeLocalName = this.typeName.split(",");
		return typeLocalName[0];
	}
    
    /** 
     * 根据类型的名称，返回类型的枚举实例。 
     * 
     * @param typeName 类型名称 
     */  
    public static CommonConstantEnum fromTypeName(String typeName) {  
        for (CommonConstantEnum type : CommonConstantEnum.values()) {  
            if (type.getTypeName().equals(typeName)) {  
                return type;  
            }  
        }  
        return null;  
    }  
}
