package com.helloworld.template.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.helloworld.template.entity.User;
import com.helloworld.template.service.UserService;

/**
 * 用户Controller
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	private static final Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @param request
	 * @return
	 */
	@RequestMapping("/login")
	public String login(User user, HttpServletRequest request) {
		User resultUser = userService.login(user);
		if (resultUser == null) {
			request.setAttribute("user", user);
			request.setAttribute("errorMsg", "用户名或密码错误！");
			return "login";
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("currentUser", resultUser);
			logger.info("登录成功：" + resultUser.getUserName());
			return "redirect:/success.jsp";
		}
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request){
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("currentUser");
		userService.logout(user);
		logger.info("退出登录：" + user.getUserName());
		session.removeAttribute("currentUser");
		return "login";
	}
}
