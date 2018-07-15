package com.exercise.account.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.exercise.account.AccountApplication;
import com.exercise.account.model.Account;
import com.exercise.account.repository.AccountRepository;
import com.exercise.account.service.AccountService;
import com.exercise.account.util.AccountUtil;
import com.exercise.account.util.JsonUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = AccountApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class AccountControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private AccountRepository repository;
	
	@Autowired
	private AccountService service;


	@After
	public void resetDb() {
		repository.deleteAll();
	}

	@Before
	public void setUp() throws Exception {
		AccountUtil.getAllData().forEach((account) -> repository.save(account));
	}

	@Test
	public void whenSameMessageAndCustomerIdSameDay_thenDontSave() throws Exception {
		Account account = new Account().setAccountType("test").setCustomerId(1234).setMessage("test message").setDate(AccountUtil.assumingTodaysDateIsFifteenJuly2018());
		List<Account> found = addAccount(account);
		assertEquals(AccountUtil.getAllData().size(), found.size());
	}
	
	private List<Account> addAccount(Account account) throws Exception {
		mvc.perform(post("/api/account/add").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(account)));
		return repository.findAll();
	}
	
	@Test
	public void positiveTestwhenCountOfRequestsInADayForAGivenCustomerLessThanDesired() throws Exception {
		Account account = new Account().setAccountType("test").setCustomerId(1236).setMessage("test message2").setDate(AccountUtil.assumingTodaysDateIsFifteenJuly2018());;
		List<Account> found = addAccount(account);
		assertEquals(AccountUtil.getAllData().size()+1, found.size());
	}
	
	@Test
	public void negativeTestwhenCountOfRequestsInADayForAGivenCustomerLessThanDesired() throws Exception {
		Account account = new Account().setAccountType("test").setCustomerId(1236).setMessage("test message2").setDate(AccountUtil.assumingTodaysDateIsFifteenJuly2018());;
		List<Account> found = addAccount(account);
		account.setMessage("34");
		found = addAccount(account);
		assertEquals(AccountUtil.getAllData().size()+1, found.size());
	}
	
	@Test
	public void countOfRequestsInAWeekStartingFromMondayForAGivenCustomer() throws Exception{
		Account account = new Account().setAccountType("test").setCustomerId(1234).setMessage("test message2").setDate(AccountUtil.assumingTodaysDateIsFifteenJuly2018());;
		int count = service.countOfRequestsInAWeekStartingFromMondayForAGivenCustomer(account);
		assertEquals(count, service.getRequestCountForWeek());
	}
	
	@Test
	public void countOfRequestsInADayForAGivenCustomer() throws Exception{
		Account account = new Account().setAccountType("test").setCustomerId(1236).setMessage("test message2").setDate(AccountUtil.assumingTodaysDateIsFifteenJuly2018());
		addAccount(account);
		account.setMessage("tell me");
		addAccount(account); // Won't be added
		int count = service.countOfRequestsInADayForAGivenCustomer(account);
		assertEquals(count, service.getRequestCountForDay());
	}
	
	@Test
	public void countOfRequestsInAMonthForAGivenCustomerAndDateRange() throws Exception{
		Account account = new Account().setAccountType("test").setCustomerId(1234).setMessage("test message 7").setDate(AccountUtil.assumingTodaysDateIsFifteenJuly2018());;
		addAccount(account); // 9 will be added
		account.setMessage("dummy");
		addAccount(account); // 10th Won't be added
		account.setMessage("dummy 2");
		int count = service.countOfRequestsInAMonthForAGivenCustomerAndDateRange(account);
		assertEquals(count, service.getRequestCountForMonth() -1 );
	}

	@Test
	public void noSameMessaageInAd() throws Exception{
		Account account = new Account().setAccountType("test").setCustomerId(1236).setMessage("This is a test message").setDate(AccountUtil.assumingTodaysDateIsFifteenJuly2018());;
		addAccount(account); // 9 will be added
		Boolean condition = service.noSameMessageForAGivenCustomerInADay(account);
		assertEquals(condition, Boolean.FALSE);
	}
	
	@Test
	public void totalCountOfRequestsInAMonthForAGivenCustomerAndDateRange() throws Exception{
		Account account = new Account().setAccountType("test").setCustomerId(1236).setMessage("This is a test message").setDate(AccountUtil.assumingTodaysDateIsFifteenJuly2018());;
		int count =service.countOfRequestsInAMonthForAGivenCustomerAndDateRange(account);
		assertEquals(count, 2);
	}

}