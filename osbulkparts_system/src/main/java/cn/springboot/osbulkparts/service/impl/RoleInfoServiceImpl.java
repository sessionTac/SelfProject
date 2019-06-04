package cn.springboot.osbulkparts.service.impl;

import cn.springboot.osbulkparts.common.CommonConstantEnum;
import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.security.entity.SecurityUserInfoEntity;
import cn.springboot.osbulkparts.common.utils.CommonSqlUtils;
import cn.springboot.osbulkparts.config.i18n.I18nMessageBean;
import cn.springboot.osbulkparts.dao.system.TDictDataDao;
import cn.springboot.osbulkparts.dao.user.MFunctionInfoDao;
import cn.springboot.osbulkparts.dao.user.MRoleInfoDao;
import cn.springboot.osbulkparts.dao.user.MUserInfoDao;
import cn.springboot.osbulkparts.entity.MFunctionInfoEntity;
import cn.springboot.osbulkparts.entity.MRoleInfoEntity;
import cn.springboot.osbulkparts.entity.MUserInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.service.RoleInfoService;
import cn.springboot.osbulkparts.service.UserInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleInfoServiceImpl implements RoleInfoService {

	@Autowired
	private MRoleInfoDao mRoleInfoDao;
	@Autowired
	private TDictDataDao tDictDataDao;
	@Autowired
	private MFunctionInfoDao mFunctionInfoDao;

	@Autowired
	private I18nMessageBean messageBean;

	@Override
	public CommonResultInfo<MRoleInfoEntity> getRoleInfoList(MRoleInfoEntity mRoleInfoEntity, int pageNumber, int pageSize) {
		CommonResultInfo<MRoleInfoEntity> result = new CommonResultInfo<MRoleInfoEntity>();
		try {
			PageHelper.startPage(pageNumber, pageSize);
			PageInfo<MRoleInfoEntity> pageInfo = new PageInfo<>(
					mRoleInfoDao.selectRoleInfoList(mRoleInfoEntity));
			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
			result.setResultInfo(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
			result.setMessage(messageBean.getMessage(""));
			result.setException(e.getMessage().toString());
		} finally {
			return result;
		}
	}
	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<Map<String, List<TDictDataEntity>>> getOptions() {
		CommonResultInfo<Map<String, List<TDictDataEntity>>> result = new CommonResultInfo<Map<String, List<TDictDataEntity>>>();
		try {
			Map<String,List<TDictDataEntity>> map = new HashMap<>();
			map.put("roleAt",tDictDataDao.selectByPrimaryKey("roleAt"));
			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
			result.setResult(map);
		} catch (Exception e) {
			result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
			result.setMessage(messageBean.getMessage("common.server.error"));
			result.setException(e.getMessage().toString());
		} finally {
			return result;
		}
	}

	/**
	 * 查询权限树结构
	 */
	@Override
	public Map<String, Object> getTree() {
		Map<String, Object> ret = new HashMap<>();

		Map<Integer,Object> functionMap = new HashMap<>();
		List<MFunctionInfoEntity> roots = new ArrayList<>();//根节点

		List<MFunctionInfoEntity> list = mFunctionInfoDao.selectTree();
		list.forEach(f->functionMap.put(Integer.parseInt(f.getFunctionId()), f));		//映射
		list.forEach(f->{
			MFunctionInfoEntity parentFunction =(MFunctionInfoEntity) functionMap.get(f.getParentId());//通过父节点id 取对应实体
			if(parentFunction == null) {
				//根节点
				roots.add(f);
			}else {
				//子节点
				if(parentFunction.getChildren() ==null) {
					parentFunction.setChildren(new ArrayList<>());
				}
				parentFunction.getChildren().add(f);
			}
		});

		//叶子节点Id
		List<Integer> leafNodeIds = list.stream().filter(f -> f.getChildren() == null).map(f -> Integer.parseInt(f.getFunctionId())).collect(Collectors.toList());

		ret.put("tree", roots);
		ret.put("leafNodeIds", leafNodeIds);

		return ret;
	}

}
