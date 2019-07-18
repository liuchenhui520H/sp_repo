package com.tedu.sp02.item.controller;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tedu.sp01.pojo.Item;
import com.tedu.sp01.service.ItemService;
import com.tedu.web.util.JsonResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController  //包含@RequestBody注解
public class ItemController {
	@Autowired
	private ItemService itemService;
	//为后续测试，观察是那一台服务器执行，把端口号注入到控制器
	@Value("${server.port}")
	private int port;
	
	@GetMapping("/{orderId}")
	public JsonResult<List<Item>> getItems(@PathVariable String orderId) throws InterruptedException {
		log.info("server.port="+port+", orderId="+orderId);
		long t=new Random().nextInt(5000);
		if(Math.random()<0.6) {
			log.info(port+"延迟："+t);
			Thread.sleep(t);
			
		}
		List<Item> items = itemService.getItems(orderId);
		return JsonResult.ok(items).msg("port="+port);
	}
	
	@PostMapping("/decreaseNumber")
	public JsonResult decreaseNumber(@RequestBody List<Item> items) {
		itemService.decreaseNumbers(items);
		return JsonResult.ok();
	}
}