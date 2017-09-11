package com.xiaojd.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.xiaojd.core.util.HibernateUtils;
import com.xiaojd.dao.BaseDao;

@Transactional
public abstract class AbstractBaseDao<T> implements BaseDao<T> {
	
	@Resource
	private SessionFactory sessionFactory;
	protected Class<T> clazz;

	@SuppressWarnings("unchecked")
	public AbstractBaseDao() {
		ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
		this.clazz = (Class<T>) type.getActualTypeArguments()[0];
	}

	public void clear() {
		getSession().clear();
	}
	
	public void merge(T entity) {
		getSession().merge(entity);
	}
	
	public void merge(Collection<T> entities) {
		if(entities != null && entities.size() > 0) {
			Iterator<T> it = entities.iterator();
			for(T t = null; it.hasNext();){
				t = it.next();
				merge(t);
			}
		}
	}
	
	public Integer save(T entity) {
		Integer id = (Integer) getSession().save(entity);
		return id;
	}
	
	public void save(Collection<T> entities) {
		if(entities != null && entities.size() > 0) {
			Iterator<T> it = entities.iterator();
			for(T t = null; it.hasNext();){
				t = it.next();
				save(t);
			}
		}
	}
	
	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
	}
	
	public void saveOrUpdate(Collection<T> entities) {
		if(entities != null && entities.size() > 0) {
			Iterator<T> it = entities.iterator();
			for(T t = null; it.hasNext();){
				t = it.next();
				saveOrUpdate(t);
			}
		}
	}

	public void update(T entity) {
		getSession().update(entity);
	}
	
	public void update(Collection<T> entities) {
		if(entities != null && entities.size() > 0) {
			Iterator<T> it = entities.iterator();
			for(T t = null; it.hasNext();){
				t = it.next();
				update(t);
			}
		}
	}
	
	public int update(String hql, Object... field) {
		Query query = getSession().createQuery(hql);
		if (field.length != 0) {
			for (int i = 0; i < field.length; i++) {
				query.setParameter(i, field[i]);
			}
		}
		return query.executeUpdate();
	}
	
	public void delete(Serializable id) {
		Object obj = getSession().get(clazz, id);
		if(obj != null) {
			getSession().delete(obj);
		}
	}
	
	public void delete(T entity) {
		getSession().delete(entity);
	}

	public void delete(Collection<T> entities) {
		if(entities != null && entities.size() > 0) {
			Iterator<T> it = entities.iterator();
			for(T t = null; it.hasNext();){
				t = it.next();
				delete(t);
			}
		}
	}
	
	public void evit(T entity) {
		getSession().evict(entity);
	}
	
	public void flush() {
		getSession().flush();
	}
	
	@SuppressWarnings("unchecked")
	public T get(Serializable id) {
		return (T) getSession().get(clazz, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return getSession().createQuery("from " + clazz.getSimpleName()).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> getByIds(String[] ids) {
		if(ids == null || ids.length == 0){
			return Collections.EMPTY_LIST;
		}
		return getSession().createQuery("from " + clazz.getSimpleName() + " where id IN(:ids)")
				.setParameter("ids", ids).list();
	}
	
	@SuppressWarnings("unchecked")
	public T load(Serializable id) {
		return (T) getSession().load(clazz, id);
	}
	
	@SuppressWarnings("rawtypes")
	public List executeNativeSql(String sql) {
		return getSession().createSQLQuery(sql).list();
	}

	@SuppressWarnings("unchecked")
	public List<T> executeNativeSqlToClass(String sql) {
		return getSession().createSQLQuery(sql).addEntity(clazz).list();
    }
	
	@SuppressWarnings("unchecked")
	public List<T> executeQuery(String queryString) {
		return getSession().createQuery(queryString).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> executeQuery(String queryString, Object...paramValues) {
		Query query = getSession().createQuery(queryString);
		if (paramValues != null) {
			for (int i = 0; i < countQlParameters(queryString, paramValues); i++) {
				query.setParameter(i, filterSpecial(paramValues[i]));
			}
		}
		return query.list();
	}
	
	public void executeUpdate(String hql) {
		getSession().createQuery(hql).executeUpdate();
	}
	
	public int getTotalCount() {
		int totalCount = ((Long) getSession()
				.createQuery("select count(*) from " + clazz.getSimpleName())
				.list().get(0)).intValue();
		return totalCount;
	}
	
	@SuppressWarnings("rawtypes")
	public List findByHqlPaged(String queryString, int firstResult, int maxResults, Object[] paramValues) {
		String hql = (String) filterSpecial(queryString);
		
		Query query = getSession().createQuery(hql);
		
		if (maxResults > 0) {
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResults);
			query.setFetchSize(maxResults);
		}
		
		if (paramValues != null) {
			for (int i = 0; i < countQlParameters(queryString, paramValues); i++) {
				query.setParameter(i, filterSpecial(paramValues[i]));
			}
		}

		return query.list();
	}

	public List executeSQLQuery(String queryString, Object[] paramValues) {
		String sql = (String) filterSpecial(queryString);
		
		Query query = getSession().createSQLQuery(sql);
		
		if (paramValues != null) {
			for (int i = 0; i < countQlParameters(queryString, paramValues); i++) {
				query.setParameter(i, filterSpecial(paramValues[i]));
			}
		}
		
		return query.list();
	}
	
	// 计算记录总数
	public Long countByHql(String hql, Object... paramValues) {
		Session session = getSession();
		if (hql.toLowerCase().indexOf("distinct") != -1) {
			String orderByDeleted = this.removeOrderBy(hql);
			String sql = HibernateUtils.hqlToSql(orderByDeleted, session);
			SQLQuery sqlQuery = session.createSQLQuery("select count(*) from (" + sql + ") tempTable_probiz");// tempTable_probiz别名
			if (paramValues != null) {
				for (int i = 0; i < paramValues.length; i++) {
					sqlQuery.setParameter(i, filterSpecial(paramValues[i]));
				}
			}
			return new Long(sqlQuery.uniqueResult().toString());
		}

		return new Long(findUnique("select count(*) " + getCountAllQl(hql), paramValues).toString());
	}

	public Object getFirst(String queryString, Object... paramValues) {
		List list = findByHqlPaged(queryString, 0, 1, paramValues) ;
		if(list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}

	public Object findUnique(String queryString, Object... paramValues) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 过滤特殊字符(mysql)
	 * @param obj
	 * @return
	 */
	private Object filterSpecial(Object obj) {
		Class type = obj.getClass();
		String typeName = type.getSimpleName();
		if (typeName.equals("String")) {
			String paramValue = obj.toString();
			Pattern p = Pattern.compile("^%(.*)%$");
			Matcher m = p.matcher(paramValue);
			if (m.matches()) {
				String first = paramValue.substring(0, 1);
				String last = paramValue.substring(paramValue.length() - 1);
				if (paramValue.indexOf("%") != -1) {
					paramValue = paramValue.substring(1, paramValue.length() - 1);
					paramValue = paramValue.replace("%", "\\%");
					paramValue = first + paramValue + last;
				}
				if (paramValue.indexOf("_") != -1) {
					paramValue = paramValue.replace("_", "\\_");
				}
			}
			// 将关键词转变为sql或hql中可识别的字符串
			paramValue = paramValue.trim(); // 去掉前后空格
			paramValue = paramValue.replaceAll("\\s+", "%"); // 将中间空格替换为%
			return paramValue;
		} else {
			return obj;
		}
	}
	
	/**
	 * 简单的取FROM和ORDER BY之间的语句（去除select和order字句)
	 * 不支持Group By和UNION
	 * @param queryString
	 * @return
	 */
	private final String getCountAllQl(final String queryString) {
		String formatedHql = queryString.toUpperCase();
		int idx = formatedHql.indexOf("FROM ");
		Assert.isTrue(idx != -1, " queryString : " + queryString + " must has a keyword 'from'");
		int idx2 = formatedHql.indexOf(" ORDER BY ");
		if (idx2 == -1) {
			idx2 = queryString.length();
		}
		return queryString.substring(idx, idx2);
	}
	
	/**
	 * 计算查询语句的可变参数个数
	 * @param queryString 查询语句
	 * @param paramValues 可变参数
	 * @return
	 */
	private final int countQlParameters(final String queryString, final Object... paramValues) {
		int count = 0;
		int idx = queryString.indexOf('?');
		while (idx > 0) {
			count++;
			idx = queryString.indexOf('?', idx + 1);
		}
		if (count != paramValues.length) {
			// throw Exception 传入参数存在问题
		}
		return count;
	}
	
	/**  
	 * 去掉order by的HQL
	 * @param hql
	 * @return
	 */
	private String removeOrderBy(String hql) {
		String formatedHql = hql.toUpperCase();
		int idx = formatedHql.indexOf(" ORDER BY ");
		if (idx != -1)
			return hql.substring(0, idx);

		return hql;
	}
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
}
