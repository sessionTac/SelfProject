package cn.springboot.osbulkparts.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import cn.springboot.osbulkparts.entity.TOrderDetailInfoEntity;

import java.util.Locale;

public interface ReportOrderDetailService {

	ResponseEntity<byte[]> DownloadReportOrderDetail(TOrderDetailInfoEntity tOrderDetailInfoEntity, Locale locale, Authentication auth);
}
