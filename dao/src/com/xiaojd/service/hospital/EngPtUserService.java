package com.xiaojd.service.hospital;

import java.util.List;
import java.util.Map;
import com.xiaojd.entity.hospital.EngPtUser;

public interface EngPtUserService {
	public void saveOrUpdataUser(EngPtUser orgUser);

	public EngPtUser findOrgRoleById(Long id) ;
	public EngPtUser findByCode(String code) ;
	
	public EngPtUser findById(Long id) ;

	public List<EngPtUser> getUsers(Map<String, String> paremeters, String sort,
			int firstRusult, int maxResult) ;

	public List getUserByCode(String code) ;

	public List<EngPtUser> getUsers() ;
	
	public List<EngPtUser> getUsersByPharmacy(Long pharmacyId);
}
