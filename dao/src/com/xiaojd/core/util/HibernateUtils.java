package com.xiaojd.core.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.hibernate.engine.query.spi.HQLQueryPlan;
import org.hibernate.hql.spi.QueryTranslator;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.internal.SessionImpl;

public class HibernateUtils {
	
	public static String hqlToSql(String hql,Session session){
		org.hibernate.internal.SessionImpl sessionImpl=(SessionImpl)session;
		SessionFactoryImpl sessionFactoryImpl=(SessionFactoryImpl)sessionImpl.getFactory();
		HQLQueryPlan hqlQueryPlan=sessionFactoryImpl.getQueryPlanCache().getHQLQueryPlan(hql, false, sessionImpl.getEnabledFilters());
		QueryTranslator translators[]=hqlQueryPlan.getTranslators();
		if(translators.length>0){
			return translators[0].getSQLString();
		}
		return null;
	}
	
	public static String appendWhereAndClauseToHql(String hql, String operator,String clause ){
		String upperHql = hql.toUpperCase();
		int idxSelect = upperHql.indexOf("SELECT ");
		int idxFrom = upperHql.indexOf("FROM ");
		int idxWhere = upperHql.indexOf(" WHERE ");
		int idxOrderBy = upperHql.indexOf(" ORDER BY ");

		String selectClause="";
		String fromClause="";
		String whereClause="";
		String orderByClause="";
		
		if (idxSelect >= 0) {
			selectClause = hql.substring(idxSelect, idxFrom);
		}

		int idxEndOfFrom = (idxWhere > 0) ? idxWhere : (idxOrderBy > 0 ? idxOrderBy : hql.length());
		fromClause = hql.substring(idxFrom, idxEndOfFrom);

		if (idxWhere > 0) {
			whereClause = hql.substring(idxWhere, idxOrderBy > 0 ? idxOrderBy : hql.length());
		}

		if (idxOrderBy > 0) {
			orderByClause = hql.substring(idxOrderBy , hql.length());
		}
		if(StringUtils.isNotBlank(whereClause)){
			whereClause +=  operator + " " + clause;
		}else{
			whereClause  = "where" + clause;
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(selectClause).append(fromClause).append(whereClause).append(orderByClause);
		
		return buffer.toString();
		
	}
	
	public static String toSubQueryFormat(List list){
		StringBuffer buffer = new StringBuffer();
		buffer.append("(");
		for(Object o : list) {
			buffer.append(o).append(",");
		}
		buffer.deleteCharAt(buffer.length()-1);
		buffer.append(")");
		return buffer.toString();
	}
}
