package com.xiaojd.service.hospital.impl;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.xiaojd.entity.hospital.EngPtDrug;
import com.xiaojd.service.hospital.EngPtDrugService;

@Repository
public class EngPtDrugServiceImpl extends HospitalBaseDAOImpl<EngPtDrug> implements  EngPtDrugService {

	@Override
	public List< EngPtDrug> loadByCfid(String cfid){
		if(cfid != null && !"".equals(cfid)){
			String hql = " from EngPtDrug where cfid = '" + cfid+"' order by groupNo";
			List< EngPtDrug> list = executeQuery(hql);
			return list;
		}
		return null;
	}


}
