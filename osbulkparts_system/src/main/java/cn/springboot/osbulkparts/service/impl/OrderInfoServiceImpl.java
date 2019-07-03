package cn.springboot.osbulkparts.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.springboot.osbulkparts.common.CommonBusinessException;
import cn.springboot.osbulkparts.common.CommonConstantEnum;
import cn.springboot.osbulkparts.common.CommonDao;
import cn.springboot.osbulkparts.common.CommonResultInfo;
import cn.springboot.osbulkparts.common.entity.CommonEntity;
import cn.springboot.osbulkparts.common.security.entity.SecurityUserInfoEntity;
import cn.springboot.osbulkparts.common.utils.CommonMethods;
import cn.springboot.osbulkparts.common.utils.CommonSqlUtils;
import cn.springboot.osbulkparts.common.utils.excel.CommonExcelConfig;
import cn.springboot.osbulkparts.common.utils.excel.CommonPoiReadUtil;
import cn.springboot.osbulkparts.config.i18n.I18nMessageBean;
import cn.springboot.osbulkparts.dao.basedata.MMaterialInfoDao;
import cn.springboot.osbulkparts.dao.basedata.MSupplierInfoDao;
import cn.springboot.osbulkparts.dao.system.TDictDataDao;
import cn.springboot.osbulkparts.dao.user.MRoleInfoDao;
import cn.springboot.osbulkparts.dao.warehouse.TOrderDetailInfoDao;
import cn.springboot.osbulkparts.dao.warehouse.TOrderInfoDao;
import cn.springboot.osbulkparts.entity.MMaterialInfoEntity;
import cn.springboot.osbulkparts.entity.MRoleInfoEntity;
import cn.springboot.osbulkparts.entity.MSupplierInfoEntity;
import cn.springboot.osbulkparts.entity.TDictDataEntity;
import cn.springboot.osbulkparts.entity.TOrderDetailInfoEntity;
import cn.springboot.osbulkparts.entity.TOrderInfoEntity;
import cn.springboot.osbulkparts.service.OrderInfoService;

@Service
public class OrderInfoServiceImpl implements OrderInfoService{
	
	@Autowired
	private TOrderInfoDao torderInfoDao;
	
	@Autowired
	private TOrderDetailInfoDao torderDetailInfoDao;
	
	@Autowired
	private TDictDataDao tDictDataDao;
	
	@Autowired
	private MRoleInfoDao mroleInfoDao;
	
	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private MSupplierInfoDao msupplierInfoDao;
	
	@Autowired
	private MMaterialInfoDao mmaterialInfoDao;
	
    @Autowired
    private I18nMessageBean messageBean;
    
	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<Map<String, List<TDictDataEntity>>> initViews() {
		CommonResultInfo<Map<String, List<TDictDataEntity>>> result = new CommonResultInfo<Map<String, List<TDictDataEntity>>>();
		try {
			Map<String,List<TDictDataEntity>> map = new HashMap<>();
			TDictDataEntity tDictDataEntity = new TDictDataEntity();
			tDictDataEntity.setDictTypeCode("unit");
			map.put("orderUnits",tDictDataDao.selectByPrimaryKey(tDictDataEntity));
			tDictDataEntity.setDictTypeCode("planStatus");
			map.put("orderStatus",tDictDataDao.selectByPrimaryKey(tDictDataEntity));
			result.setResult(map);
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
	public CommonResultInfo<?> importExcel(MultipartFile excleFile, HttpServletRequest request, Authentication auth,int type,int isBalance) {
        CommonResultInfo<?> result = new CommonResultInfo<TOrderInfoEntity>();
        result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
        try {
        	int resultInt = 0;
        	List<TOrderInfoEntity> orderInfoParams = resolvExcelToDb(excleFile,auth,type,isBalance);
        	if(orderInfoParams.size() == 0) {
        		result.setMessage(messageBean.getMessage("common.excel.error"));
        	}else {
        		resultInt = torderInfoDao.insertList(orderInfoParams);
            	if(resultInt >0) {
            		result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
            		result.setMessage(messageBean.getMessage("common.excel.success"));
            	}
        	}
        } catch(ParseException pe) {
            result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
            result.setMessage(pe.getMessage().toString());
        } 
        catch (NullPointerException se) {
            result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
            result.setMessage(se.getMessage().toString());
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
	public CommonResultInfo<TOrderInfoEntity> selectOrderInfoList(TOrderInfoEntity torderInfoEntity, int pageNumber,
			int pageSize, Authentication auth) {
		CommonResultInfo<TOrderInfoEntity> result = new CommonResultInfo<TOrderInfoEntity>();
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected());
		try {
			torderInfoEntity.setDataRoleAt(roleInfoEntity.getRoleAt());
			PageHelper.startPage(pageNumber, pageSize);
			PageInfo<TOrderInfoEntity> pageInfo = new PageInfo<>(
					torderInfoDao.selectOrderInfoListByKeys(torderInfoEntity));
			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
			result.setResultInfo(pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
			result.setMessage(messageBean.getMessage("common.server.error"));
			result.setException(e.getMessage().toString());
		} finally {
			return result;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<?> checkOrderInfo(TOrderInfoEntity torderInfoEntity) {
		CommonResultInfo<?> result = new CommonResultInfo<TOrderInfoEntity>();
		try {
			List<TOrderInfoEntity> resultList = torderInfoDao.selectOrderInfoListByKeys(torderInfoEntity);
			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
			if(resultList.size()>0) {
				result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
			result.setMessage(messageBean.getMessage("common.server.error"));
			result.setException(e.getMessage().toString());
		} finally {
			return result;
		}
	}
	
	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<TOrderInfoEntity> selectOrderInfo(TOrderInfoEntity torderInfoEntity) {
		CommonResultInfo<TOrderInfoEntity> result = new CommonResultInfo<TOrderInfoEntity>();
		try {
			List<TOrderInfoEntity> resultList = torderInfoDao.selectOrderInfoListByKeys(torderInfoEntity);
			result.setCode(ResponseEntity.ok().build().getStatusCodeValue());
			if(resultList.size()>0) {
				result.setResult(resultList.get(0));
			}
			else {
				result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
				result.setMessage(messageBean.getMessage("common.info.empty"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
			result.setMessage(messageBean.getMessage("common.server.error"));
			result.setException(e.getMessage().toString());
		} finally {
			return result;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<?> insertOrderInfo(TOrderInfoEntity torderInfoEntity, Authentication auth) {
		CommonResultInfo<?> result = new CommonResultInfo<TOrderInfoEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected());
		try {
			torderInfoEntity.setDataRoleAt(roleInfoEntity.getRoleAt());
			torderInfoEntity.setId(CommonSqlUtils.getUUID32());
			torderInfoEntity.setCreateUser(principal.getUserName());
			torderInfoEntity.setIsDelete(0);
			torderInfoEntity.setOrderStatus("0");
			torderInfoEntity.setVersion(1);
			int returnInt = torderInfoDao.insertSelective(torderInfoEntity);
			if (returnInt > 0) {
				result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
				result.setMessage(messageBean.getMessage("common.add.success", CommonConstantEnum.ORDERINFO_DATA.getTypeName()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
			result.setMessage(messageBean.getMessage("common.server.error"));
			result.setException(e.getMessage().toString());
		} finally {
			return result;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<?> updateOrderInfo(TOrderInfoEntity torderInfoEntity, Authentication auth) {
		CommonResultInfo<?> result = new CommonResultInfo<TOrderInfoEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		try {
			TOrderInfoEntity orderInfoList = torderInfoDao.selectByPrimaryKey(torderInfoEntity.getId());
			if(orderInfoList != null && (orderInfoList.getVersion() != torderInfoEntity.getVersion())) {
				result.setMessage(messageBean.getMessage("common.update.version", CommonConstantEnum.ORDERINFO_DATA.getTypeName()));
			}else {
				torderInfoEntity.setUpdateUser(principal.getUserName());
				torderInfoEntity.setVersion(torderInfoEntity.getVersion()+1);
				torderInfoEntity.setIsDelete(0);
				torderInfoEntity.setOrderStatus("0");
				int returnInt = torderInfoDao.updateByPrimaryKey(torderInfoEntity);
				if (returnInt > 0) {
					result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
					result.setMessage(messageBean.getMessage("common.update.success", CommonConstantEnum.ORDERINFO_DATA.getTypeName()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
			result.setMessage(messageBean.getMessage("common.server.error"));
			result.setException(e.getMessage().toString());
		} finally {
			return result;
		}
	}

	@SuppressWarnings("finally")
	@Override
	public CommonResultInfo<?> deleteBatchOrderInfo(CommonEntity commonEntity, Authentication auth) {
		CommonResultInfo<?> result = new CommonResultInfo<TOrderInfoEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
		try {
			int returnInt = torderInfoDao.deleteBatchData(commonEntity.getIdsStr(),principal.getUserName(),CommonConstantEnum.TO_DELETE.getTypeName());
			if (returnInt > 0) {
				result.setMessage(messageBean.getMessage("common.delete.success", CommonConstantEnum.ORDERINFO_DATA.getTypeName()));
				result.setCode(ResponseEntity.status(HttpStatus.CREATED).build().getStatusCodeValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
			result.setMessage(messageBean.getMessage("common.server.error"));
			result.setException(e.getMessage().toString());
		} finally {
			return result;
		}
	}

	@Override
	public ResponseEntity<byte[]> downloadExcel(TOrderInfoEntity torderInfoEntity) {
		String[] title = messageBean.getMessage("file.title.orderinfo").split(",");
		List<TOrderInfoEntity> resultList = torderInfoDao.selectOrderInfoListByKeys(torderInfoEntity);
		ResponseEntity<byte[]> result = educeExcel(title,resultList);
		return result;
	}
	
	@Override
	public CommonResultInfo<?> generateOrderDetailInfo(CommonEntity commonEntity, Authentication auth) {
		CommonResultInfo<?> result = new CommonResultInfo<TOrderInfoEntity>();
		result.setCode(ResponseEntity.badRequest().build().getStatusCodeValue());
		try {
			List<TOrderDetailInfoEntity> torderDetailInfoList = new ArrayList<TOrderDetailInfoEntity>();
			MMaterialInfoEntity mmaterialInfoParam = new MMaterialInfoEntity();
			List<TOrderInfoEntity> orderInfoList = torderInfoDao.selectByIds(commonEntity.getIdsStr());
			
			SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
			MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected());
			for(TOrderInfoEntity torderInfo:orderInfoList) {
				// 系统自动生成订单号：当前日期+000000
				String orderNo = commonDao.orderNoGenerater();
				// 订单型号+物料号判定详细信息中是否存在
				mmaterialInfoParam.setMaterialOrderCode(torderInfo.getOrderCode());
				mmaterialInfoParam.setDataRoleAt(roleInfoEntity.getRoleAt());
				List<MMaterialInfoEntity> materilInfoLst = mmaterialInfoDao.selectByPrimaryKey(mmaterialInfoParam);
				if(materilInfoLst.size() == 0) {
					// 不存在：提示【当前订单型号无物料BOM信息，请确认数据。】
					result.setMessage(messageBean.getMessage("bussiness.order.material.empty",torderInfo.getOrderCode()));
					return result;
				}else {
					int itemI = 0;
					for(MMaterialInfoEntity materialInfo:materilInfoLst) {
						TOrderDetailInfoEntity orderDetailParam = new TOrderDetailInfoEntity();
						// 主键ID
						orderDetailParam.setId(CommonSqlUtils.getUUID32());
						// 订单号
						orderDetailParam.setOrderId(orderNo);
						itemI = itemI + 1;
						// 物料号的供应商信息是否存在
						// 不存在：提示【当前订单型号中物料无供应商信息，请确认数据。】
						MSupplierInfoEntity msupplierInfo = msupplierInfoDao.selectByCode(materialInfo.getSupplierCode());
						if(msupplierInfo == null) {
							result.setMessage(messageBean.getMessage("bussiness.order.supplier.empty",
									materialInfo.getMaterialOrderCode(),
									materialInfo.getMaterialCode(),
									materialInfo.getSupplierCode()));
							return result;
						}else {
							// 判断供应商的配额是否存在
							// 不存在：提示【当前订单型号的供应商没有设置配额，请确认。】
							if(torderInfo.getIsBalance().equals("0")) {
								if(msupplierInfo.getSupplierQuo() == null) {
									result.setMessage(messageBean.getMessage("bussiness.order.supplierQuo.empty",
											materialInfo.getMaterialOrderCode(),
											materialInfo.getMaterialCode(),
											materialInfo.getSupplierCode()));
									return result;
								}
							}
							// 订单产品型号
							orderDetailParam.setOrderCode(torderInfo.getOrderCode());
							// 订单产品型号描述
							orderDetailParam.setOrderCodeDesc(torderInfo.getOrderCodeDesc());
							// 订单数量
							orderDetailParam.setOrderAmount(torderInfo.getOrderAmount());
							// 订单日期
							orderDetailParam.setOrderDate(torderInfo.getOrderDate());
							// 订单型号单位
							orderDetailParam.setOrderUnit(torderInfo.getOrderUnit());
							// 订单行项目
							String itemNo = String.format("%04d", itemI);
							orderDetailParam.setOrderIdItem(itemNo);
							// 物料号
							orderDetailParam.setMaterialCode(materialInfo.getMaterialCode());
							// 物料中文描述
							orderDetailParam.setMaterialDescCn(materialInfo.getMaterialDescCn());
							// 物料英文描述
							orderDetailParam.setMaterialDescEn(materialInfo.getMaterialDescEn());
							// 物料俄文描述
							orderDetailParam.setMaterialDescRn(materialInfo.getMaterialDescRn());
							// 物料单位
							orderDetailParam.setMaterialUnit(materialInfo.getMaterialUnit());
							// 物料数量
							BigDecimal orderMaterAmount= new BigDecimal("0");
							if(materialInfo.getMaterialAmount() == null) {
								result.setMessage(messageBean.getMessage("bussiness.material.amount.empty",
										materialInfo.getMaterialOrderCode(),
										materialInfo.getMaterialCode()));
								return result;
							}
							if(torderInfo.getIsBalance().equals("1")) {
								// 按照供应商配额计算数量
								orderMaterAmount = torderInfo.getOrderAmount().multiply(
										materialInfo.getMaterialAmount()).setScale(2, BigDecimal.ROUND_HALF_UP);
							}else if(torderInfo.getIsBalance().equals("0")) {
								// 按照供应商配额计算数量
								orderMaterAmount = torderInfo.getOrderAmount().multiply(
										materialInfo.getMaterialAmount()).multiply(
												msupplierInfo.getSupplierQuo()).setScale(2, BigDecimal.ROUND_HALF_UP);
							}
							// 订单物料数量
							orderDetailParam.setMaterialAmount(orderMaterAmount);
							// 物料类别
							orderDetailParam.setMaterialCategory(materialInfo.getMaterialCategory());
							// 换算关系
							orderDetailParam.setMaterialRelation(materialInfo.getMaterialRelation());
							// 换算后单位
							orderDetailParam.setMaterialRelationUnit(materialInfo.getMaterialRelationUnit());
							// 换算后数量
							BigDecimal relation = new BigDecimal(orderDetailParam.getMaterialRelation().length() > 0 ? orderDetailParam.getMaterialRelation():"1");
							BigDecimal relationAmount = orderMaterAmount.multiply(relation);
							orderDetailParam.setMaterialRelationQuantity(relationAmount);
							// 最小包装类型
							orderDetailParam.setMaterialMinpackageType(materialInfo.getMaterialMinpackageType());
							// 最小包装数量
							orderDetailParam.setMaterialMinpackageAmt(materialInfo.getMaterialMinpackageAmt());
							// 最小包装总量
							if(materialInfo.getMaterialMinpackageAmt() == null || materialInfo.getMaterialMinpackageAmt().equals(BigDecimal.ZERO)) {
								orderDetailParam.setMaterialMinpackageTotalamt(BigDecimal.ZERO);
							}else {
								orderDetailParam.setMaterialMinpackageTotalamt(orderMaterAmount.divide(materialInfo.getMaterialMinpackageAmt(), 2, BigDecimal.ROUND_HALF_UP));
							}
							if(materialInfo.getMaterialTaxPrice() == null) {
								orderDetailParam.setMaterialTaxPrice(BigDecimal.ZERO);
								orderDetailParam.setMaterialTaxTotalprice(BigDecimal.ZERO);
								orderDetailParam.setMaterialVatPrice(BigDecimal.ZERO);
								orderDetailParam.setMaterialVatTotalprice(BigDecimal.ZERO);
							}else {
								if(materialInfo.getTax() == null) {
									result.setMessage(messageBean.getMessage("bussiness.material.tax.empty",
											materialInfo.getMaterialOrderCode(),
											materialInfo.getMaterialCode()));
									return result;
								}
								// 未税单价
								orderDetailParam.setMaterialTaxPrice(materialInfo.getMaterialTaxPrice());
								// 未税总价
								orderDetailParam.setMaterialTaxTotalprice(materialInfo.getMaterialTaxPrice().multiply(orderMaterAmount).setScale(2,BigDecimal.ROUND_HALF_UP));
								// 含税单价
								BigDecimal vatPrice = materialInfo.getMaterialTaxPrice().add(
										materialInfo.getMaterialTaxPrice().multiply(materialInfo.getTax()));
								orderDetailParam.setMaterialVatPrice(vatPrice);
								// 含税总价
								orderDetailParam.setMaterialVatTotalprice(vatPrice.multiply(orderMaterAmount).setScale(2,BigDecimal.ROUND_HALF_UP));
							}
							// 代理费率
							orderDetailParam.setMaterialRate(materialInfo.getMaterialRate());
							// 币种
							orderDetailParam.setMaterialCurrency(materialInfo.getMaterialCurrency());
							// 国家标志
							// 状态
							orderDetailParam.setConfirmStatus("0");
							// 型号发货总数量
							orderDetailParam.setOrderOutTotalAmount(BigDecimal.ZERO);
							// 子件发货总数量
							orderDetailParam.setMaterOutTotalAmount(BigDecimal.ZERO);
							// 订单剩余数量
							orderDetailParam.setResidualAmount(BigDecimal.ZERO);
							// 物料剩余数量
							orderDetailParam.setSurplusAmount(BigDecimal.ZERO);
							// 库存数量
							orderDetailParam.setStockAmount(BigDecimal.ZERO);
							// 差异数量
							orderDetailParam.setDifferAmount(BigDecimal.ZERO);
							// 收货数量
							orderDetailParam.setTakeOverAmount(BigDecimal.ZERO);
							// 发货数量
							orderDetailParam.setDeliveryAmount(BigDecimal.ZERO);
							// 数据所属
							orderDetailParam.setDataRoleAt(roleInfoEntity.getRoleAt());
							// 创建者
							orderDetailParam.setCreateUser(principal.getUserName());
							// 逻辑删除
							orderDetailParam.setIsDelete(0);
							// 版本
							orderDetailParam.setVersion(1);
							// 是否平衡表数据
							orderDetailParam.setIsBalance(torderInfo.getIsBalance());
						}
						torderDetailInfoList.add(orderDetailParam);
					}
				}
				torderInfo.setOrderStatus("1");
				torderInfo.setUpdateUser(principal.getUserName());
				torderInfoDao.updateByPrimaryKeySelective(torderInfo);
			}
			torderDetailInfoDao.insertList(torderDetailInfoList);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build().getStatusCodeValue());
			result.setMessage(messageBean.getMessage("common.server.error"));
			result.setException(e.getMessage().toString());
			return result;
		} 
	}
	
	@Override
	public CommonResultInfo<?> deleteOrderInfo(String orderNo, Authentication auth) {
		// TODO Auto-generated method stub
		return null;
	}

	/****Private Methods****/
	/**
	 * 
	 * @param titles 第一列名
	 * @param list 向单元格插入数据
	 * @return
	 */
	private ResponseEntity<byte[]> educeExcel(String[] titles,List<TOrderInfoEntity> list){
		ResponseEntity<byte[]> response = null;
		//创建Excel对象
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		HSSFWorkbook workbook = new HSSFWorkbook();
		//输出Excel文件  
		try {
			//创建工作表单
			HSSFSheet sheet = workbook.createSheet(messageBean.getMessage("file.name.orderinfo"));  
			//创建HSSFRow对象 （行）第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
			HSSFRow row = sheet.createRow(0); 	
			row.setHeightInPoints(20);// 设备标题的高度
			//创建HSSFCell对象  （单元格）
			HSSFCell cell=null; 
			//设置第一列单元格的列
			for(int i = 0; i < titles.length; i++){
				cell = row.createCell(i);//列索引从0开始
				cell.setCellValue(titles[i]);//列名1
			};
			CellStyle  style =  workbook.createCellStyle();
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
			style.setFillForegroundColor(IndexedColors.RED.getIndex());     
			CellStyle textStyle = workbook.createCellStyle();
			DataFormat format = workbook.createDataFormat();
			textStyle.setDataFormat(format.getFormat("@"));
			//设置单元格的值  
			for (int i = 0; i < list.size(); i++) {
				row = sheet.createRow(i+1);
				TOrderInfoEntity example = list.get(i);
				//订单型号
				row.createCell(0).setCellValue(example.getOrderCode());
				//订单型号描述
				row.createCell(1).setCellValue(example.getOrderCodeDesc());
				//订单型号单位
				row.createCell(2).setCellValue(example.getDictOrderUnit().getName());
				//日期
				row.createCell(3).setCellValue(parseDate(example.getOrderDate(),"yyyy/MM/dd"));
				//订单数量
				row.createCell(4).setCellValue(example.getOrderAmount().toString());
			}
			workbook.write(os);
			workbook.close();
			String filename_enc = UriUtils.encode(messageBean.getMessage("file.name.orderinfo"), "UTF-8");
			response = ResponseEntity.ok()
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.header("Access-Control-Expose-Headers","Content-Disposition")
				.header("Content-Disposition","attachment; filename*=UTF-8''" + filename_enc+".xlsx")
				.body(os.toByteArray());
			return response;
		} catch (Exception e) {
			throw new CommonBusinessException(e.getMessage().toString());
		}
	}
	
	/***
	 * Excel文件解析
	 * @throws Exception 
	 */
	private List<TOrderInfoEntity> resolvExcelToDb(MultipartFile excleFile,Authentication auth,int type,int isBalance) throws ParseException,NullPointerException,Exception{
		try {
			SecurityUserInfoEntity principal = (SecurityUserInfoEntity)auth.getPrincipal();
			MRoleInfoEntity roleInfoEntity = mroleInfoDao.selectRoleInfo(principal.getRoleIdSelected());
			List<TOrderInfoEntity> insertResultLst = new ArrayList<TOrderInfoEntity>();
			
			File file = CommonPoiReadUtil.MultipartFileToFile(excleFile);
			CommonPoiReadUtil poiUtil=new CommonPoiReadUtil(file,true);
			CommonExcelConfig config=new CommonExcelConfig();
			
			//设置读取参数
			poiUtil.setReplenishNull(false);
			poiUtil.setPrintLog(false);
			
			// 必须项
			if(type == 1) {
				//按周导入时：订单型号，型号描述，数量（1）
				config.setNotNullColumn(new int[]{1,2,3});
				config.setSheetNum(2);
			}
			else if(type == 2) {
				//按月导入时：订单型号，型号描述，数量（4）
				config.setNotNullColumn(new int[]{1,2,3});
				config.setSheetNum(1);
			}
			else if(type == 3) {
				config.setNotNullColumn(new int[] {1,2,3});
			}
			// 不需要快读
			config.setBriefRead(false);
			config.setStartRow(2);
			
			List<Map<String, Object>> dataLst=poiUtil.readExcel(config);
			//文件解析后数据放入实体对象List
			for(Map<String, Object> mapData:dataLst) {
				TOrderInfoEntity torderInfoEntity = new TOrderInfoEntity();
				// 按周导入
				if(type == 1) {
					// 主键ID
					torderInfoEntity.setId(CommonSqlUtils.getUUID32());
					// 订单型号，成品型号
					torderInfoEntity.setOrderCode((String)mapData.get("订单型号"));
					// 型号描述
					torderInfoEntity.setOrderCodeDesc((String)mapData.get("型号描述"));
					// 订单型号单位
					String ordeUnit = getFromDictDataByName(
							(String)mapData.get("订单型号单位"),"unit","订单型号单位");
					torderInfoEntity.setOrderUnit(ordeUnit);
					// 订单日期
					torderInfoEntity.setOrderDate(parseDate((String)mapData.get("周"),"yyyyMMddhhmmss"));
					// 数量
					String orderAmount = (String)mapData.get("数量");
					torderInfoEntity.setOrderAmount(CommonMethods.changeToBigdecimal(orderAmount.trim()));
					// 数据所属
					torderInfoEntity.setDataRoleAt(roleInfoEntity.getRoleAt());
					// 计划状态
					torderInfoEntity.setOrderStatus("0");
					// 创建者
					torderInfoEntity.setCreateUser(principal.getUserName());
					// 删除
					torderInfoEntity.setIsDelete(0);
					// 版本
					torderInfoEntity.setVersion(1);
					// 是否平衡表数据
					torderInfoEntity.setIsBalance(String.valueOf(isBalance));
					insertResultLst.add(torderInfoEntity);
				}
				else if(type ==2) {
					// 按月导入
					// 主键ID
					torderInfoEntity.setId(CommonSqlUtils.getUUID32());
					// 订单型号，成品型号
					torderInfoEntity.setOrderCode((String)mapData.get("订单型号"));
					// 型号描述
					torderInfoEntity.setOrderCodeDesc((String)mapData.get("型号描述"));
					// 订单型号单位
					String ordeUnit = getFromDictDataByName(
							(String)mapData.get("订单型号单位"),"unit","订单型号单位");
					torderInfoEntity.setOrderUnit(ordeUnit);
					// 是否平衡表数据
					torderInfoEntity.setIsBalance(String.valueOf(isBalance));
					// 订单日期
					torderInfoEntity.setOrderDate(parseDate((String)mapData.get("第一周"),"yyyyMMddhhmmss"));
					// 数量
					torderInfoEntity.setOrderAmount(CommonMethods.changeToBigdecimal((String)mapData.get("数量1")));
					// 数据所属
					torderInfoEntity.setDataRoleAt(roleInfoEntity.getRoleAt());
					// 计划状态
					torderInfoEntity.setOrderStatus("0");
					// 创建者
					torderInfoEntity.setCreateUser(principal.getUserName());
					// 删除
					torderInfoEntity.setIsDelete(0);
					// 版本
					torderInfoEntity.setVersion(1);
					insertResultLst.add(torderInfoEntity);
					// 主键ID
					torderInfoEntity.setId(CommonSqlUtils.getUUID32());
					// 订单日期
					torderInfoEntity.setOrderDate(parseDate((String)mapData.get("第二周"),"yyyyMMddhhmmss"));
					// 数量
					torderInfoEntity.setOrderAmount(CommonMethods.changeToBigdecimal((String)mapData.get("数量2")));
					insertResultLst.add(torderInfoEntity);
					// 主键ID
					torderInfoEntity.setId(CommonSqlUtils.getUUID32());
					// 订单日期
					torderInfoEntity.setOrderDate(parseDate((String)mapData.get("第三周"),"yyyyMMddhhmmss"));
					// 数量
					torderInfoEntity.setOrderAmount(CommonMethods.changeToBigdecimal((String)mapData.get("数量3")));
					insertResultLst.add(torderInfoEntity);
					// 主键ID
					torderInfoEntity.setId(CommonSqlUtils.getUUID32());
					// 订单日期
					torderInfoEntity.setOrderDate(parseDate((String)mapData.get("第四周"),"yyyyMMddhhmmss"));
					// 数量
					torderInfoEntity.setOrderAmount(CommonMethods.changeToBigdecimal((String)mapData.get("数量4")));
					insertResultLst.add(torderInfoEntity);
				}else if(type ==3) {
					// 按年导入
					// 主键ID
					torderInfoEntity.setId(CommonSqlUtils.getUUID32());
					// 订单型号，成品型号
					torderInfoEntity.setOrderCode((String)mapData.get("订单型号"));
					// 型号描述
					torderInfoEntity.setOrderCodeDesc((String)mapData.get("型号描述"));
					// 订单型号单位
					String ordeUnit = getFromDictDataByName(
							(String)mapData.get("订单型号单位"),"unit","订单型号单位");
					torderInfoEntity.setOrderUnit(ordeUnit);
					// 是否平衡表数据
					torderInfoEntity.setIsBalance(String.valueOf(isBalance));
					// 订单日期
					torderInfoEntity.setOrderDate(parseDate((String)mapData.get("1月"),"yyyyMMddhhmmss"));
					// 数量
					torderInfoEntity.setOrderAmount(CommonMethods.changeToBigdecimal((String)mapData.get("数量1")));
					// 数据所属
					torderInfoEntity.setDataRoleAt(roleInfoEntity.getRoleAt());
					// 计划状态
					torderInfoEntity.setOrderStatus("0");
					// 创建者
					torderInfoEntity.setCreateUser(principal.getUserName());
					// 删除
					torderInfoEntity.setIsDelete(0);
					// 版本
					torderInfoEntity.setVersion(1);
					insertResultLst.add(torderInfoEntity);
					// 主键ID
					torderInfoEntity.setId(CommonSqlUtils.getUUID32());
					// 订单日期
					torderInfoEntity.setOrderDate(parseDate((String)mapData.get("2月"),"yyyyMMddhhmmss"));
					// 数量
					torderInfoEntity.setOrderAmount(CommonMethods.changeToBigdecimal((String)mapData.get("数量2")));
					insertResultLst.add(torderInfoEntity);
					// 主键ID
					torderInfoEntity.setId(CommonSqlUtils.getUUID32());
					// 订单日期
					torderInfoEntity.setOrderDate(parseDate((String)mapData.get("3月"),"yyyyMMddhhmmss"));
					// 数量
					torderInfoEntity.setOrderAmount(CommonMethods.changeToBigdecimal((String)mapData.get("数量3")));
					insertResultLst.add(torderInfoEntity);
					// 主键ID
					torderInfoEntity.setId(CommonSqlUtils.getUUID32());
					// 订单日期
					torderInfoEntity.setOrderDate(parseDate((String)mapData.get("4月"),"yyyyMMddhhmmss"));
					// 数量
					torderInfoEntity.setOrderAmount(CommonMethods.changeToBigdecimal((String)mapData.get("数量4")));
					insertResultLst.add(torderInfoEntity);
					// 主键ID
					torderInfoEntity.setId(CommonSqlUtils.getUUID32());
					// 订单日期
					torderInfoEntity.setOrderDate(parseDate((String)mapData.get("5月"),"yyyyMMddhhmmss"));
					// 数量
					torderInfoEntity.setOrderAmount(CommonMethods.changeToBigdecimal((String)mapData.get("数量5")));
					insertResultLst.add(torderInfoEntity);
					// 主键ID
					torderInfoEntity.setId(CommonSqlUtils.getUUID32());
					// 订单日期
					torderInfoEntity.setOrderDate(parseDate((String)mapData.get("6月"),"yyyyMMddhhmmss"));
					// 数量
					torderInfoEntity.setOrderAmount(CommonMethods.changeToBigdecimal((String)mapData.get("数量6")));
					insertResultLst.add(torderInfoEntity);
					// 主键ID
					torderInfoEntity.setId(CommonSqlUtils.getUUID32());
					// 订单日期
					torderInfoEntity.setOrderDate(parseDate((String)mapData.get("7月"),"yyyyMMddhhmmss"));
					// 数量
					torderInfoEntity.setOrderAmount(CommonMethods.changeToBigdecimal((String)mapData.get("数量7")));
					insertResultLst.add(torderInfoEntity);
					// 主键ID
					torderInfoEntity.setId(CommonSqlUtils.getUUID32());
					// 订单日期
					torderInfoEntity.setOrderDate(parseDate((String)mapData.get("8月"),"yyyyMMddhhmmss"));
					// 数量
					torderInfoEntity.setOrderAmount(CommonMethods.changeToBigdecimal((String)mapData.get("数量8")));
					insertResultLst.add(torderInfoEntity);
					// 主键ID
					torderInfoEntity.setId(CommonSqlUtils.getUUID32());
					// 订单日期
					torderInfoEntity.setOrderDate(parseDate((String)mapData.get("9月"),"yyyyMMddhhmmss"));
					// 数量
					torderInfoEntity.setOrderAmount(CommonMethods.changeToBigdecimal((String)mapData.get("数量9")));
					insertResultLst.add(torderInfoEntity);
					// 主键ID
					torderInfoEntity.setId(CommonSqlUtils.getUUID32());
					// 订单日期
					torderInfoEntity.setOrderDate(parseDate((String)mapData.get("10月"),"yyyyMMddhhmmss"));
					// 数量
					torderInfoEntity.setOrderAmount(CommonMethods.changeToBigdecimal((String)mapData.get("数量10")));
					insertResultLst.add(torderInfoEntity);
					// 主键ID
					torderInfoEntity.setId(CommonSqlUtils.getUUID32());
					// 订单日期
					torderInfoEntity.setOrderDate(parseDate((String)mapData.get("11月"),"yyyyMMddhhmmss"));
					// 数量
					torderInfoEntity.setOrderAmount(CommonMethods.changeToBigdecimal((String)mapData.get("数量11")));
					insertResultLst.add(torderInfoEntity);
					// 主键ID
					torderInfoEntity.setId(CommonSqlUtils.getUUID32());
					// 订单日期
					torderInfoEntity.setOrderDate(parseDate((String)mapData.get("12月"),"yyyyMMddhhmmss"));
					// 数量
					torderInfoEntity.setOrderAmount(CommonMethods.changeToBigdecimal((String)mapData.get("数量12")));
					insertResultLst.add(torderInfoEntity);
				}
				// 成品型号和子件型号组合判定是否存在，存在时更新，不存在时插入
				int rnt = isExist(torderInfoEntity.getOrderCode());
				if(rnt == 1) {
					// 删掉已存在但未生成的订单计划
					torderInfoDao.deleteByOrderNo(torderInfoEntity.getOrderCode());
				}
				else if(rnt == 2) {
					throw new NullPointerException(messageBean.getMessage("bussiness.order.exist"));
				}
			}
			return insertResultLst;
		}
		catch(ParseException pe) {
			throw pe;
		}
		catch(NullPointerException se) {
			throw se;
		}catch(Exception e) {
			throw e;
		}
	}
	

	/***
	 * 日期转换
	 * @param str
	 * @param pattern
	 * @return date
	 * @throws ParseException 
	 */
    private String parseDate(String str, String pattern) throws ParseException {
    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = null;
        String dateString = null;
        try {
    		date = dateFormat.parse(str);
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            dateString = formatter.format(date);
        } catch (ParseException e) {
            throw new ParseException(messageBean.getMessage("common.excel.date.error",CommonConstantEnum.ORDER_DATE.getTypeName()),0);
        }
        return dateString;
    }
	
	/***
	 * 订单型号是否存在
	 * @param OrderOrderCode
	 * @param OrderCode
	 * @return 1：已存在但未生成；2：已存在且已生成；3：不存在
	 */
	private int isExist(String OrderCode) {
		TOrderInfoEntity torderInfoEntity = new TOrderInfoEntity();
		torderInfoEntity.setOrderCode(OrderCode);
		List<TOrderInfoEntity> resultList = torderInfoDao.selectOrderInfoListByKeys(torderInfoEntity);
		if(resultList.size()>0) {
			if(resultList.get(0).getOrderStatus().equals("0")) {
				// 已存在但未生成
				return 1;
			}else {
				// 已存在且已生成
				return 2;
			}
		}
		return 3;
	}
	
	/***
	 * 根据Excel中的文字内容匹配字典数据，取得对应的值
	 * @param nameValue
	 * @param dictType
	 * @return
	 */
	private String getFromDictDataByName(String nameValue,String dictType,String dictTypeCn) {
		TDictDataEntity dictDataParam = new TDictDataEntity();
		try {
			dictDataParam.setName(nameValue);
			dictDataParam.setDictTypeCode(dictType);
			List<TDictDataEntity> dictDataLst = tDictDataDao.selectByPrimaryKey(dictDataParam);
			if(dictDataLst.size()>0) {
				return dictDataLst.get(0).getValue();
			}else {
				throw new NullPointerException(messageBean.getMessage("common.dict.emptyerror", dictTypeCn));
			}
		}
		catch(Exception e) {
			throw e;
		}
	}
}
