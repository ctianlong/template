package com.helloworld.template.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.helloworld.template.common.util.HqlFilter;
import com.helloworld.template.common.util.ReflectionUtils;
import com.helloworld.template.dao.BaseDao;

@Repository
public class BaseDaoImpl<T> implements BaseDao<T> {
	@Autowired
	private SessionFactory sessionFactory;
	private Class<T> type;

	public BaseDaoImpl() {
		type = ReflectionUtils.getSuperGenericType(getClass());
	}

	public Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public Serializable save(T o) {
		if (o != null) {
			return getCurrentSession().save(o);
		}
		return null;
	}

	public T getById(Serializable id) {
		return (T) getCurrentSession().get(type, id);
	}

	public T getByHql(String hql) {
		Query q = getCurrentSession().createQuery(hql);
		List<T> l = q.list();
		if ((l != null) && (l.size() > 0)) {
			return (T) l.get(0);
		}
		return null;
	}

	public T getByHql(String hql, Map<String, Object> params) {
		Query q = getCurrentSession().createQuery(hql);
		if ((params != null) && (!params.isEmpty())) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		List<T> l = q.list();
		if ((l != null) && (l.size() > 0)) {
			return (T) l.get(0);
		}
		return null;
	}

	public void delete(T o) {
		if (o != null) {
			getCurrentSession().delete(o);
		}
	}

	public void update(T o) {
		if (o != null) {
			getCurrentSession().update(o);
		}
	}

	public void saveOrUpdate(T o) {
		if (o != null) {
			getCurrentSession().saveOrUpdate(o);
		}
	}

	public List<T> find(String hql) {
		Query q = getCurrentSession().createQuery(hql);
		return q.list();
	}

	public List<T> find(String hql, Map<String, Object> params) {
		Query q = getCurrentSession().createQuery(hql);
		if ((params != null) && (!params.isEmpty())) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.list();
	}

	public List<T> find(String hql, Map<String, Object> params, int page, int rows) {
		Query q = getCurrentSession().createQuery(hql);
		if ((params != null) && (!params.isEmpty())) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	public List<T> find(String hql, int page, int rows) {
		Query q = getCurrentSession().createQuery(hql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	public Long count(String hql) {
		Query q = getCurrentSession().createQuery(hql);
		return (Long) q.uniqueResult();
	}

	public Long count(String hql, Map<String, Object> params) {
		Query q = getCurrentSession().createQuery(hql);
		if ((params != null) && (!params.isEmpty())) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (Long) q.uniqueResult();
	}

	public int executeHql(String hql) {
		Query q = getCurrentSession().createQuery(hql);
		return q.executeUpdate();
	}

	public int executeHql(String hql, Map<String, Object> params) {
		Query q = getCurrentSession().createQuery(hql);
		if ((params != null) && (!params.isEmpty())) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}

	public List<Map> findBySql(String sql) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		return q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	public List<Map> findBySql(String sql, int page, int rows) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	public List<Map> findBySql(String sql, Map<String, Object> params) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		if ((params != null) && (!params.isEmpty())) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	public List<Map> findBySql(String sql, Map<String, Object> params, int page, int rows) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		if ((params != null) && (!params.isEmpty())) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	public int executeSql(String sql) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		return q.executeUpdate();
	}

	public int executeSql(String sql, Map<String, Object> params) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		if ((params != null) && (!params.isEmpty())) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}

	public BigInteger countBySql(String sql) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		return (BigInteger) q.uniqueResult();
	}

	public BigInteger countBySql(String sql, Map<String, Object> params) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		if ((params != null) && (!params.isEmpty())) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (BigInteger) q.uniqueResult();
	}

	public T getByFilter(HqlFilter hqlFilter) {
		String className = type.getName();
		String hql = "select distinct t from " + className + " t";
		return getByHql(hql + hqlFilter.getWhereAndOrderHql(), hqlFilter.getParams());
	}

	public List<T> find() {
		return findByFilter(new HqlFilter());
	}

	public List<T> findByFilter(HqlFilter hqlFilter) {
		String className = type.getName();
		String hql = "select distinct t from " + className + " t";
		return find(hql + hqlFilter.getWhereAndOrderHql(), hqlFilter.getParams());
	}

	public List<T> findByFilter(HqlFilter hqlFilter, int page, int rows) {
		String className = type.getName();
		String hql = "select distinct t from " + className + " t";
		return find(hql + hqlFilter.getWhereAndOrderHql(), hqlFilter.getParams(), page, rows);
	}

	public List<T> find(int page, int rows) {
		return findByFilter(new HqlFilter(), page, rows);
	}

	public Long countByFilter(HqlFilter hqlFilter) {
		String className = type.getName();
	    String hql = "select count(distinct t) from " + className + " t";
		return count(hql + hqlFilter.getWhereAndOrderHql(), hqlFilter.getParams());
	}

	public Long count() {
		return countByFilter(new HqlFilter());
	}

}
