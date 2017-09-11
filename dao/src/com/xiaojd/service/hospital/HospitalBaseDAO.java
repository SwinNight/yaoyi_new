package com.xiaojd.service.hospital;

import java.util.List;

public interface HospitalBaseDAO<T> {
	
	/**
	 * ���queryString(query language, ��sql/hql...)��ѯ��������0..n������
	 * @param queryString
	 * @param firstResult
	 * @param maxResults
	 * @param paramValues
	 * @return
	 */
	public List findByHqlPaged(final String queryString, final int firstResult, final int maxResults, final Object[] paramValues);

	/**
	 * ���HQL��ѯ��������� ��֧��Group By��UNION��ֻ�Ǽ򵥵�ȡFROM��ORDER
	 * BY֮�����䣨ȥ��select��order�Ӿ䣩�� ����Ҫ�����Ƽ�ʹ�ñ���������ʹ��findUnique�������档
	 * 
	 * @param hql
	 * @param paramValues
	 * @return
	 */
	public Long countByHql(final String hql, final Object... paramValues);

	/**
	 * ���queryString(query language, ��sql/hql...)��ѯ��ȡ��һ�����󣬿�����0..n������
	 * 
	 * @param queryString
	 * @param paramValues
	 * @return
	 */
	public Object getFirst(final String queryString, final Object... paramValues);

	/**
	 * ��ݿɱ䲻�����Է���Ψһ���� ��ȡΨһ����,������ݿ�ȷ��Ψһ�����򱨴?
	 * 
	 * @param queryString
	 * @param paramValues
	 *            �ɱ����
	 * @return
	 */
	public Object findUnique(final String queryString, final Object... paramValues);

	/**
	 * ִ��ԭ��SQL
	 * ��ɾ��
	 * @param sql
	 */
	public void executeSQLUpdate(final String sql);

	/**
	 * ���SQL��ѯ
	 * 
	 * @param deleteSql
	 */
	public List executeSQLQuery(final String queryString, final int firstResult,
			final int maxResults, final Object[] paramValues);
	
	/**
	 * 2014-04-04   杨子波 查询报表
	 * 做报表功能因只需要拼接好的sql语句和判断条件，且返回list
	 * **/
	public List executeSQLQuery(String queryString);
}
