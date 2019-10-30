package com.eveong.myim.route.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eveong.myim.route.service.RouteService;
import com.eveong.myim.route.vo.RouteAddAndGetVO;

@RestController
@RequestMapping("route")
public class RouteController {
	
	@Resource
	RouteService routeService;
	
	/**
	 * 
	 * 添加路由
	 * @return
	 * @author:fangyunhe
	 * @time:2019年10月30日 下午2:51:06
	 */
	@PostMapping(value = "add")
	public String add(RouteAddAndGetVO routeAddAndGetVO){
		routeService.add(routeAddAndGetVO);
		return "添加路由成功";
	}
	
	/**
	 * 删除路由
	 * @return
	 * @author:fangyunhe
	 * @time:2019年10月30日 下午2:51:21
	 */
	@DeleteMapping
	public String del(Integer userId){
		routeService.del(userId);
		return "删除路由成功";
	}
	
	/**
	 * 
	 * @param userId
	 * @return
	 * @author:fangyunhe
	 * @time:2019年10月30日 下午4:42:05
	 */
	@GetMapping
	public RouteAddAndGetVO get(Integer userId){
		RouteAddAndGetVO routeAddAndGetVO = routeService.get(userId);
		return routeAddAndGetVO;
	}
}
