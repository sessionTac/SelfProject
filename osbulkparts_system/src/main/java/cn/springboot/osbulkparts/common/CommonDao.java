package cn.springboot.osbulkparts.common;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommonDao {

	
	@Select("SELECT get_trans_num()"
			)
	public String orderNoGenerater();
}
