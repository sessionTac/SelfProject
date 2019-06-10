package cn.springboot.osbulkparts.common.utils;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.util.StringUtil;

import cn.springboot.osbulkparts.config.i18n.I18nMessageBean;
import cn.springboot.osbulkparts.dao.system.TDictDataDao;
import cn.springboot.osbulkparts.entity.TDictDataEntity;

public class CommonMethods {
	
	@Autowired
	private static TDictDataDao tdictDataDao;
	
    @Autowired
    private static I18nMessageBean messageBean;
	
	/***
	 * 将excel中的文字转为Bigdecimal格式数据
	 * @param value
	 * @return
	 */
	public static BigDecimal changeToBigdecimal(String value) {
		if(StringUtil.isNotEmpty(value)) {
			BigDecimal returnValue = new BigDecimal(value);
			return returnValue;
		}
		else {
			BigDecimal returnValueNull = new BigDecimal(0);
			return returnValueNull;
		}
	}
	
	/***
	 * 将excel中的文字转换为Double格式数据，文字为空返回0.0
	 * @param value
	 * @return
	 */
	public static Double changeToDouble(String value) {
		if(StringUtil.isNotEmpty(value)) {
			return Double.valueOf(value);
		}
		else {
			return new Double(0);
		}
	}
	
	/***
	 * 根据Excel中的文字内容匹配字典数据，取得对应的值
	 * @param nameValue
	 * @param dictType
	 * @return
	 */
	public static String getFromDictDataByName(String nameValue,String dictType,String dictTypeCn) {
		TDictDataEntity dictDataParam = new TDictDataEntity();
		try {
			dictDataParam.setName(nameValue);
			dictDataParam.setDictTypeCode(dictType);
			List<TDictDataEntity> dictDataLst = tdictDataDao.selectByPrimaryKey(dictDataParam);
			if(dictDataLst.size()>0) {
				return dictDataLst.get(0).getValue();
			}else {
				throw new NullPointerException(messageBean.getMessage("common.dict.emptyerror", dictTypeCn));
			}
		}
		catch(Exception e) {
			throw e;
		}
	}

}
