package com.exercise.account.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.account.model.Account;
import com.exercise.account.service.AccountService;
import com.weddini.throttling.Throttling;
import com.weddini.throttling.ThrottlingType;

@RestController
@RequestMapping("/api")
public class AccountController {
	
	@Autowired
	AccountService service;
	
	@RequestMapping(value = "/account/add", method = RequestMethod.POST)
	@Throttling(limit = 100, timeUnit = TimeUnit.SECONDS, type = ThrottlingType.RemoteAddr)
	public Account add(@RequestBody Account account)  {
		return service.save(account);
	}

	@RequestMapping(value = "/account/all", method = RequestMethod.GET)
	public List<Account> all() {
		return service.findAll();
	}

}
