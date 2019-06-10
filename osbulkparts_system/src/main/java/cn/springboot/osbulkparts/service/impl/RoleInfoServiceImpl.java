package cn.springboot.osbulkparts.service.impl;

import cn.springboot.osbulkparts.common.CommonConstantEnum;
import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.security.entity.SecurityUserInfoEntity;
import cn.springboot.osbulkparts.common.utils.CommonSqlUtils;
import cn.springboot.osbulkparts.config.i18n.I18nMessageBean;
import cn.springboot.osbulkparts.dao.system.TDictDataDao;
import cn.springboot.osbulkparts.dao.user.*;
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
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.beans.Transient;
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
    private TRoleFunctionRelationDao tRoleFunctionRelationDao;
    @Autowired
    private TUserRoleRelationDao tUserRoleRelationDao;

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
            TDictDataEntity dictDataParam = new TDictDataEntity();
            dictDataParam.setDictTypeCode("roleAt");
            Map<String, List<TDictDataEntity>> map = new HashMap<>();
            map.put("roleAt", tDictDataDao.selectByPrimaryKey(dictDataParam));
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

        Map<Integer, Object> functionMap = new HashMap<>();
        List<MFunctionInfoEntity> roots = new ArrayList<>();//根节点

        List<MFunctionInfoEntity> list = mFunctionInfoDao.selectTree();
        list.forEach(f -> functionMap.put(f.getFunctionId(), f));        //映射
        list.forEach(f -> {
            MFunctionInfoEntity parentFunction = (MFunctionInfoEntity) functionMap.get(f.getParentId());//通过父节点id 取对应实体
            if (parentFunction == null) {
                //根节点
                roots.add(f);
            } else {
                //子节点
                if (parentFunction.getChildren() == null) {
                    parentFunction.setChildren(new ArrayList<>());
                }
                parentFunction.getChildren().add(f);
            }
        });

        //叶子节点Id
        List<Integer> leafNodeIds = list.stream().filter(f -> f.getChildren() == null).map(f -> f.getFunctionId()).collect(Collectors.toList());

        ret.put("tree", roots);
        ret.put("leafNodeIds", leafNodeIds);

        return ret;
    }

    /**
     * 根據id查詢权限
     */
    @Override
    public Object findPowerByRoleId(String roleId) {

        return tRoleFunctionRelationDao.selectPowerByRoleId(roleId);
    }

    /**
     * 添加权限
     */
    @SuppressWarnings("finally")
    @Transactional
    @Override
    public Object insertPower(List<Integer> functionIds, String roleId, HttpServletRequest request, Authentication auth) {
        CommonResultInfo<?> result = new CommonResultInfo<MUserInfoEntity>();
        result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity) auth.getPrincipal();
        try {
            tRoleFunctionRelationDao.deleteById(roleId);
            int r = 0;
            if (!functionIds.isEmpty()) {
                r = tRoleFunctionRelationDao.insertList(functionIds, roleId, principal.getUserId());
            }
            if (r == functionIds.size()) {
                result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
                result.setMessage(messageBean.getMessage("common.update.success", CommonConstantEnum.POWER.getTypeName()));
            }
        } catch (Exception e) {
            result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
            result.setMessage(messageBean.getMessage("common.server.error"));
            result.setException(e.getMessage().toString());
            throw new RuntimeException();
        } finally {
            return result;
        }
    }

    @SuppressWarnings("finally")
    @Override
    public CommonResultInfo<MRoleInfoEntity> getRoleInfo(String roleId) {
        CommonResultInfo<MRoleInfoEntity> result = new CommonResultInfo<MRoleInfoEntity>();
        try {
            MRoleInfoEntity userInfo = mRoleInfoDao.selectRoleInfo(roleId);
            result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
            result.setResult(userInfo);
        } catch (Exception e) {
            result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
            result.setMessage(messageBean.getMessage("common.server.error"));
            result.setException(e.getMessage().toString());
        } finally {
            return result;
        }
    }

    @SuppressWarnings("finally")
    @Override
    public CommonResultInfo<?> checkInfo(MRoleInfoEntity mRoleInfoEntity, String checkFlag) {
        CommonResultInfo<?> result = new CommonResultInfo<MUserInfoEntity>();
        result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
        try {
            MRoleInfoEntity checkEntity = new MRoleInfoEntity();
            checkEntity.setRoleName(mRoleInfoEntity.getRoleName());
            List<MRoleInfoEntity> checkList = mRoleInfoDao.checkInfo(checkEntity);
            if (checkFlag.equals("add")) {
                if (checkList.size() == 0) {
                    result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
                } else {
                    result.setMessage(messageBean.getMessage("common.add.repeat", CommonConstantEnum.ROLE_NAME.getTypeName()));
                }
            } else if (checkFlag.equals("edit")) {
                if (
                        (checkList.size() == 0)
                                ||
                                ((checkList.size() == 1) && (mRoleInfoEntity.getRoleId().equals(checkList.get(0).getRoleId())))
                ) {
                    result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
                } else {
                    result.setMessage(messageBean.getMessage("common.add.repeat", CommonConstantEnum.ROLE_NAME.getTypeName()));
                }
            }
        } catch (Exception e) {
            result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
            result.setMessage(messageBean.getMessage("common.server.error"));
            result.setException(e.getMessage().toString());
        } finally {
            return result;
        }
    }

    @SuppressWarnings("finally")
    @Override
    public CommonResultInfo<?> updateRoleInfo(MRoleInfoEntity mRoleInfoEntity, Authentication auth) {
        CommonResultInfo<?> result = new CommonResultInfo<MRoleInfoEntity>();
        result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity) auth.getPrincipal();
        try {
            MRoleInfoEntity checkEntity = new MRoleInfoEntity();
            checkEntity.setVersion(mRoleInfoEntity.getVersion());
            checkEntity.setRoleId(mRoleInfoEntity.getRoleId());
            List<MRoleInfoEntity> checkList = mRoleInfoDao.checkInfo(checkEntity);
            if (checkList.size() == 1) {
                int returnInt = mRoleInfoDao.updateByPrimaryKeySelective(mRoleInfoEntity);
                if (returnInt > 0) {
                    result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
                    result.setMessage(messageBean.getMessage("common.update.success", CommonConstantEnum.ROLE.getTypeName()));
                }
            } else {
                result.setMessage(messageBean.getMessage("common.update.version", CommonConstantEnum.ROLE.getTypeName()));
            }
        } catch (Exception e) {
            result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
            result.setMessage(messageBean.getMessage("common.server.error"));
            result.setException(e.getMessage().toString());
        } finally {
            return result;
        }
    }

    @SuppressWarnings("finally")
    @Override
    public CommonResultInfo<?> deleteRoleInfo(String roleId, Authentication auth) {
        CommonResultInfo<?> result = new CommonResultInfo<MRoleInfoEntity>();
        result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity) auth.getPrincipal();
        MRoleInfoEntity mRoleInfoEntity = new MRoleInfoEntity();
        try {
            mRoleInfoEntity.setRoleId(roleId);
            mRoleInfoEntity.setUpdateUser(principal.getUserId());
            mRoleInfoEntity.setIsDelete(1);
            int returnInt = mRoleInfoDao.deleteByRoleId(mRoleInfoEntity);
            if (returnInt > 0) {
                //根据roleId把角色权限关系表中的有关该角色的信息删除 物理删除
                tRoleFunctionRelationDao.deleteById(roleId);
                //根据roleId把用户与角色关系表中的有关该角色的信息删除 物理删除
                tUserRoleRelationDao.deleteByRoleId(roleId);
                result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
                result.setMessage(messageBean.getMessage("common.delete.success", CommonConstantEnum.ROLE.getTypeName()));
            }
        } catch (Exception e) {
            result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
            result.setMessage(messageBean.getMessage("common.server.error"));
            result.setException(e.getMessage().toString());
        } finally {
            return result;
        }
    }

    @Override
    public CommonResultInfo<?> addRoleInfo(MRoleInfoEntity mRoleInfoEntity, Authentication auth) {
        CommonResultInfo<?> result = new CommonResultInfo<MRoleInfoEntity>();
        result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
        SecurityUserInfoEntity principal = (SecurityUserInfoEntity) auth.getPrincipal();
        try {
            String userUUID = CommonSqlUtils.getUUID32();
            mRoleInfoEntity.setRoleId(userUUID);
            mRoleInfoEntity.setCreateUser(principal.getUserId());
            mRoleInfoEntity.setIsDelete(0);
            mRoleInfoEntity.setVersion(1);
            int returnInt = mRoleInfoDao.insertSelective(mRoleInfoEntity);
            if (returnInt > 0) {
                result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
                result.setMessage(messageBean.getMessage("common.add.success", CommonConstantEnum.ROLE.getTypeName()));
            }

        } catch (Exception e) {
            result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
            result.setMessage(messageBean.getMessage("common.server.error"));
            result.setException(e.getMessage().toString());
        } finally {
            return result;
        }
    }

}
