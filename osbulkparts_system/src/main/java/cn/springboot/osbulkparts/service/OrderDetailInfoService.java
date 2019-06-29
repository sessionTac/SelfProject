package cn.springboot.osbulkparts.service;

import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.entity.TDictDataEntity;

import java.util.List;
import java.util.Map;

public interface OrderDetailInfoService {

    CommonResultInfo<Map<String, List<TDictDataEntity>>> initViews();
}
