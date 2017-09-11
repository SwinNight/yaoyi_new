package com.xiaojd.service.hospital;
import java.util.List;
import java.util.Map;
import com.xiaojd.entity.hospital.EngPtCf;
public interface EngPtCfService {

	/**
	 * 药店处方
	 * @param id
	 * @return
	 */
	public  EngPtCf loadById(String id);
	
	/**
	 * 社区医院处方
	 * @param id
	 * @return
	 */
	public EngPtCf loadByIdToHospital(String id);
	
	/**药店处方，社区医院处方
	 * @param id
	 * @return
	 */
	public EngPtCf loadByIdToAll(String id);
	
	public void saveOrUpdate(EngPtCf cf);

	public void flush();

	public void clear();
	
	public List executeSQLQuery(String queryString, int firstResult,
			int maxResults, Map<String, String> paramValues);
	public Long countBySql(String sql, Map<String, String> paramValues);

	public List<EngPtCf> loadByCardAndName(String cardNo,String name,String idNo,String phoneNo);



	public List<EngPtCf> loadByPresNos(String presNos);


	public List<EngPtCf> loadByStatus(String cardNo, String name, String idNo,
			String phoneNo, String status);

	

	
	
}