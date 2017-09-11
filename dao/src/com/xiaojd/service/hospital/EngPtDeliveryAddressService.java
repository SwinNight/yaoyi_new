package com.xiaojd.service.hospital;

import java.util.List;

import com.xiaojd.entity.hospital.EngPtDeliveryAddress;
import com.xiaojd.entity.hospital.EngPtCf;

public interface EngPtDeliveryAddressService {

	public List<EngPtDeliveryAddress> loadByPatientNo(String patientNo);

	public List<EngPtDeliveryAddress> loadByPhoneNo(String phoneNo);
	
	public EngPtDeliveryAddress loadById(Long id);

	public void deleteById(Long id);
	
	public void saveOrUpdate(EngPtDeliveryAddress patient);
	
	public Long saveAddress(EngPtDeliveryAddress patient);
}