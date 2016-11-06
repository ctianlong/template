package com.helloworld.template.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.helloworld.template.common.util.HqlFilter;

public abstract interface BaseDao<T>
{
  public abstract Serializable save(T paramT);
  
  public abstract void delete(T paramT);
  
  public abstract void update(T paramT);
  
  public abstract void saveOrUpdate(T paramT);
  
  public abstract T getById(Serializable paramSerializable);
  
  public abstract T getByHql(String paramString);
  
  public abstract T getByHql(String paramString, Map<String, Object> paramMap);
  
  public abstract T getByFilter(HqlFilter paramHqlFilter);
  
  public abstract List<T> find();
  
  public abstract List<T> findByFilter(HqlFilter paramHqlFilter);
  
  public abstract List<T> findByFilter(HqlFilter paramHqlFilter, int paramInt1, int paramInt2);
  
  public abstract List<T> find(int paramInt1, int paramInt2);
  
  public abstract List<T> find(String paramString);
  
  public abstract List<T> find(String paramString, Map<String, Object> paramMap);
  
  public abstract List<T> find(String paramString, int paramInt1, int paramInt2);
  
  public abstract List<T> find(String paramString, Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract Long count(String paramString);
  
  public abstract Long count(String paramString, Map<String, Object> paramMap);
  
  public abstract Long countByFilter(HqlFilter paramHqlFilter);
  
  public abstract Long count();
  
  public abstract int executeHql(String paramString);
  
  public abstract int executeHql(String paramString, Map<String, Object> paramMap);
  
  public abstract List<Map> findBySql(String paramString);
  
  public abstract List<Map> findBySql(String paramString, int paramInt1, int paramInt2);
  
  public abstract List<Map> findBySql(String paramString, Map<String, Object> paramMap);
  
  public abstract List<Map> findBySql(String paramString, Map<String, Object> paramMap, int paramInt1, int paramInt2);
  
  public abstract int executeSql(String paramString);
  
  public abstract int executeSql(String paramString, Map<String, Object> paramMap);
  
  public abstract BigInteger countBySql(String paramString);
  
  public abstract BigInteger countBySql(String paramString, Map<String, Object> paramMap);
}

