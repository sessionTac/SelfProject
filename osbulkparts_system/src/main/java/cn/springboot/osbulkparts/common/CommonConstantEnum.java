package cn.springboot.osbulkparts.common;

public enum CommonConstantEnum {
	DICT_TYPE("字典分类"),
	DICT_TYPE_CODE("字典分类编码"),
	DICT_DATA("字典数据"),
    ROLE_NAME("角色名"),
    ROLE("角色"),
    POWER("权限"),
    USER("用户"),
	USER_NAME("用户名"),
	SUPPLIER("供应商"),
	MATERIAL_DATA("物料数据");
	
	private String typeName;  
	  
	CommonConstantEnum(String typeName) {  
        this.typeName = typeName;  
    }  

    public String getTypeName() {  
        return this.typeName;  
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
