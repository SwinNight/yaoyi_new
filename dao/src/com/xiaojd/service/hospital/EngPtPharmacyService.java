package com.xiaojd.service.hospital;

import java.util.List;
import java.util.Map;

import com.xiaojd.entity.hospital.EngPtDelivery;
import com.xiaojd.entity.hospital.EngPtPharmacy;
import com.xiaojd.entity.hospital.EngPtUser;


public interface EngPtPharmacyService {
	
	public EngPtPharmacy loadById(long id);

	public void saveOrUpdataUser(EngPtPharmacy orgUser);
	
	public List<EngPtPharmacy> getPharmacysByStatus(String status);

	public List<String> getPharmacysAreas();
	
	public List<EngPtPharmacy> getPharmacysByArea(String area);
    
}
