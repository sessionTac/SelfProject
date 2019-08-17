package cn.springboot.osbulkparts.dao.report;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.springboot.osbulkparts.entity.ReportOrderDetailInfoEntity;
import cn.springboot.osbulkparts.entity.TOrderDetailInfoEntity;

@Mapper
public interface ReportOrderDetailInfoDao {
	
	List<ReportOrderDetailInfoEntity> getReportOrderDetailInfo(TOrderDetailInfoEntity orderDetailRecord);
	
	List<ReportOrderDetailInfoEntity> getOrderDate(TOrderDetailInfoEntity orderDetailRecord);

}
