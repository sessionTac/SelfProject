package cn.springboot.osbulkparts.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.entity.CommonEntity;
import cn.springboot.osbulkparts.entity.TDeliverInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;

public interface GoodsListService {

	CommonResultInfo<Map<String, List<TDictDataEntity>>> initViews(String lang, Locale locale);

	CommonResultInfo<?> getGoodsList(TDeliverInfoEntity stockInfoEntity, int pageNumber, int pageSize, Authentication auth,Locale locale);

	CommonResultInfo<?> sendGoods(CommonEntity commonEntity, Authentication auth,Locale locale);

	ResponseEntity<byte[]> downExcel(TDeliverInfoEntity stockInfoEntity);
}
