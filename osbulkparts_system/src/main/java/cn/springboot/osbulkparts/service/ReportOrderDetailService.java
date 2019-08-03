package cn.springboot.osbulkparts.service;

import org.springframework.http.ResponseEntity;

import cn.springboot.osbulkparts.entity.TOrderDetailInfoEntity;

public interface ReportOrderDetailService {

	ResponseEntity<byte[]> DownloadReportOrderDetail(TOrderDetailInfoEntity tOrderDetailInfoEntity);
}
