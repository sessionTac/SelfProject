package cn.springboot.osbulkparts.common;

public enum CommonConstantEnum {
	DICT_TYPE("字典分类"),
	DICT_TYPE_CODE("字典分类编码"),
	USER_NAME("用户名");
	
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
