package cn.springboot.osbulkparts.common.security.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import cn.springboot.osbulkparts.entity.MRoleInfoEntity;
import lombok.Data;

@Data
public class SecurityUserInfoEntity {
	
	private String userId;
	
	private String userName;
	
	private String passWord;
	
	private Integer userType;
	
	private String userRealName;
	
	private Integer userStauts;
	
	private String roleIdSelected;
	
	private List<MRoleInfoEntity> roleList = new ArrayList<MRoleInfoEntity>();
	
	private Collection<? extends GrantedAuthority> authorities;

}
