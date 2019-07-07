package cn.springboot.osbulkparts.service;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.entity.CommonEntity;
import cn.springboot.osbulkparts.entity.TDeliverInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.entity.TStockInfoEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface GoodsListService {

	CommonResultInfo<Map<String, List<TDictDataEntity>>> initViews();

	CommonResultInfo<?> getGoodsList(TDeliverInfoEntity stockInfoEntity, int pageNumber, int pageSize, Authentication auth);


	CommonResultInfo<?> sendGoods(CommonEntity commonEntity, Authentication auth);

	
	ResponseEntity<byte[]> downExcel(TDeliverInfoEntity stockInfoEntity);
}
