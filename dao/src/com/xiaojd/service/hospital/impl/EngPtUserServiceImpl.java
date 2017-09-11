package com.xiaojd.service.hospital.impl;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

import com.xiaojd.entity.hospital.EngPtDeliveryAddress;
import com.xiaojd.entity.hospital.EngPtUser;
import com.xiaojd.service.hospital.EngPtUserService;

@Repository
public class EngPtUserServiceImpl extends HospitalBaseDAOImpl<EngPtUser> implements EngPtUserService {

	public void saveOrUpdataUser(EngPtUser orgUser) {
		saveOrUpdate(orgUser);
	}

	public EngPtUser findOrgRoleById(Long id) {
		if(id != null && !"".equals(id)){
			String hql = " from EngPtUser where id = '" + id + "'";
			List<EngPtUser> list = executeQuery(hql);
			return (list == null&&list.size()<=0) ? null : (list.size() > 0 ? list.get(0) : null);
		}
		return null;
	}
	
	public EngPtUser findById(Long id) {
		if(id != null && id >0){
			String hql = " from EngPtUser where id = '" + id + "'";
			List<EngPtUser> list = executeQuery(hql);
			return (list == null&&list.size()<=0) ? null : (list.size() > 0 ? list.get(0) : null);
		}
		return null;
	}
	
	public EngPtUser findByCode(String code) {
		if(code != null && !"".equals(code)){
			String hql = " from EngPtUser where code = '" + code + "'";
			List<EngPtUser> list = executeQuery(hql);
			return (list == null&&list.size()<=0) ? null : (list.size() > 0 ? list.get(0) : null);
		}
		return null;
	}

	public List<EngPtUser> getUsers(Map<String, String> paremeters, String sort,
			int firstRusult, int maxResult) {
		StringBuffer sql = new StringBuffer();
		sql.append(" from EngPtUser where 1=1 ");
		if(!paremeters.isEmpty()) {
			for(String key : paremeters.keySet()){
				sql.append("and " + key + " like '%" + paremeters.get(key) + "%' ");
			}
		}
		sql.append(sort);
		return executeQuery(sql.toString(), firstRusult, maxResult);
	}

	public List getUserByCode(String code) {
		String sql = "select ou.*,og.pharmacy  from Eng_Pt_User ou left join Eng_pt_pharmacy og on og.id =ou.pharmacy_id where ou.status='1' and  ou.code= '"+code+"'";
		return executeNativeSql(sql);
	}

	/* 有效的用户
	 * @see com.xiaojd.service.hospital.EngPtUserService#getUsers()
	 */
	public List<EngPtUser> getUsers() {
		return executeQuery(" from EngPtUser where status ='1'");
	}
	
	public List<EngPtUser> getUsersByPharmacy(Long pharmacyId) {
		return executeQuery(" from EngPtUser where role='1003' and status ='1' and pharmacyId="+pharmacyId);
	}
	



	
}
