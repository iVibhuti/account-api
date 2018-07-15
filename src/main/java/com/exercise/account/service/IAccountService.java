package com.exercise.account.service;

import java.time.LocalDate;
import java.util.List;

import com.exercise.account.model.Account;

public interface IAccountService {

	List<Account> findByDate(LocalDate date);

	Account save(Account persisted);

	List<Account> findAll();

	Account findByCustomerIdAndDate(final int customerId, final LocalDate date);

	List<Account> findByDateBetween(final LocalDate startDate, final LocalDate endDate);

	Account findByMessageAndCustomerIdAndDate(final String message, final int customerId, final LocalDate currentDate);

	int countByCustomerIdAndDateBetween(final int customerId, final LocalDate startDate, final LocalDate endDate);

	int countByCustomerIdAndDate(final int customerId, final LocalDate date);

}
