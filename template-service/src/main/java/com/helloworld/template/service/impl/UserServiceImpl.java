package com.helloworld.template.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.helloworld.template.dao.BaseDao;
import com.helloworld.template.entity.User;
import com.helloworld.template.service.UserService;

/**
 * Cacheable注解负责将方法的返回值加入到缓存中 
 * CacheEvict注解负责清除缓存(它的三个参数与@Cacheable的意思是一样的) 
 * @see ---------------------------------------------------------------------------------------------------------- 
 * @see value------缓存位置的名称,不能为空,若使用EHCache则其值为ehcache.xml中的<cache name="myCache"/> 
 * @see key--------缓存的Key,默认为空(表示使用方法的参数类型及参数值作为key),支持SpEL 
 * @see condition--只有满足条件的情况才会加入缓存,默认为空(表示全部都加入缓存),支持SpEL 
 * @see ---------------------------------------------------------------------------------------------------------- 
 * @see 该注解的源码位于spring-context-3.2.4.RELEASE-sources.jar中 
 * @see Spring针对Ehcache支持的Java源码位于spring-context-support-3.2.4.RELEASE-sources.jar中
 * @author tianlong
 *
 */

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private BaseDao<User> userDao;
	
	//将查询到的数据缓存到myCache中,并使用方法名称加上参数中的userNo作为缓存的key  
    //通常更新操作只需刷新缓存中的某个值,所以为了准确的清除特定的缓存,故定义了这个唯一的key,从而不会影响其它缓存值 
	@Override
	@Cacheable(value="myCache", key="'user.'+#user.getUserName()+'.'+#user.getPassword()")
	public User login(User user) {
		System.out.println("测试ehcache,从数据库查询用户");
		String userName = user.getUserName();
		String password = user.getPassword();
		String hql = "from User u where u.userName = :userName";
		Map<String, Object> params = new HashMap<>();
		params.put("userName", userName);
		User user2 = userDao.getByHql(hql, params);
		if(user2 != null && password.equals(user2.getPassword())){
			return user2;
		}
		return null;
	}

	//allEntries为true表示清除value中的全部缓存,默认为false,@CacheEvict(value="myCache", allEntries=true)
	
	@Override
	@CacheEvict(value="myCache", key="'user.'+#user.getUserName()+'.'+#user.getPassword()")
	public void logout(User user) {
		System.out.println("测试ehcache,删除指定缓存");
	}
	

}
