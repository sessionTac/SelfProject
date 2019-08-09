package cn.springboot.osbulkparts.service;

import org.springframework.http.ResponseEntity;

import cn.springboot.osbulkparts.entity.TOrderDetailInfoEntity;

import java.util.Locale;

public interface ReportOrderDetailService {

	ResponseEntity<byte[]> DownloadReportOrderDetail(TOrderDetailInfoEntity tOrderDetailInfoEntity, Locale locale);
}
