package com.exercise.account.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.exercise.account.model.Account;
import com.exercise.account.repository.AccountRepository;

@Service
public class AccountService implements IAccountService {

	private static Logger log = LoggerFactory.getLogger(AccountService.class);

	@Autowired
	AccountRepository repository;

	@Value("${request.count.month}")
	int requestCountForMonth;

	@Value("${request.count.week}")
	int requestCountForWeek;

	@Value("${request.count.days}")
	int requestCountForDay;

	public int getRequestCountForMonth() {
		return requestCountForMonth;
	}

	public void setRequestCountForMonth(Integer requestCountForMonth) {
		this.requestCountForMonth = requestCountForMonth;
	}

	public int getRequestCountForWeek() {
		return requestCountForWeek;
	}

	public void setRequestCountForWeek(Integer requestCountForWeek) {
		this.requestCountForWeek = requestCountForWeek;
	}

	public int getRequestCountForDay() {
		return requestCountForDay;
	}

	public void setRequestCountForDay(Integer requestCountForDay) {
		this.requestCountForDay = requestCountForDay;
	}

	public int countOfRequestsInAMonthForAGivenCustomerAndDateRange(Account account) {
		LocalDate startMonth = account.getDate().withDayOfMonth(1);
		int count = repository.countByCustomerIdAndDateBetween(account.getCustomerId(), startMonth, account.getDate());
		log.debug("{} is noMoreThanTenRequestsInAMonthForAGivenCustomerAndDateRange", count);
		return count;

	}

	public int countOfRequestsInAWeekStartingFromMondayForAGivenCustomer(Account account) {
		LocalDate startWeek = account.getDate().with(DayOfWeek.MONDAY);
		int count = repository.countByCustomerIdAndDateBetween(account.getCustomerId(), startWeek, account.getDate());
		log.debug("{} is noMoreThanFiveRequestsInAWeekStartingFromMondayForAGivenCustomer", count);
		return count;

	}

	public int countOfRequestsInADayForAGivenCustomer(Account account) {
		int count = repository.countByCustomerIdAndDate(account.getCustomerId(), account.getDate());
		log.debug("{} is noMoreThanThreeRequestsInADayForAGivenCustomer", count);
		return count;
	}

	public Boolean noSameMessageForAGivenCustomerInADay(Account account) {
		Account sameMessageObject = repository.findByMessageAndCustomerIdAndDate(account.getMessage(),
				account.getCustomerId(), account.getDate());
		return sameMessageObject == null;
	}

	public Boolean checkAllConditions(Account account) {

		return (countOfRequestsInAMonthForAGivenCustomerAndDateRange(account) < getRequestCountForMonth())
				&& (countOfRequestsInAWeekStartingFromMondayForAGivenCustomer(account) < getRequestCountForWeek())
				&& (countOfRequestsInADayForAGivenCustomer(account) < getRequestCountForDay())
				&& noSameMessageForAGivenCustomerInADay(account);
	}

	@Override
	public List<Account> findByDate(LocalDate date) {
		return repository.findByDate(date);
	}

	@Override
	public Account save(Account persisted) {
		if (checkAllConditions(persisted)) {
			return repository.save(persisted);
		}
		return null;

	}

	@Override
	public List<Account> findAll() {
		return repository.findAll();
	}

	@Override
	public Account findByCustomerIdAndDate(int customerId, LocalDate date) {
		return repository.findByCustomerIdAndDate(customerId, date);
	}

	@Override
	public List<Account> findByDateBetween(LocalDate startDate, LocalDate endDate) {
		return repository.findByDateBetween(startDate, endDate);
	}

	@Override
	public Account findByMessageAndCustomerIdAndDate(String message, int customerId, LocalDate currentDate) {
		return repository.findByMessageAndCustomerIdAndDate(message, customerId, currentDate);

	}

	@Override
	public int countByCustomerIdAndDateBetween(int customerId, LocalDate startDate, LocalDate endDate) {
		return repository.countByCustomerIdAndDateBetween(customerId, startDate, endDate);
	}

	@Override
	public int countByCustomerIdAndDate(int customerId, LocalDate date) {
		return repository.countByCustomerIdAndDate(customerId, date);
	}

}
