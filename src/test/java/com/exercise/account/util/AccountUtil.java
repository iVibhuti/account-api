package com.exercise.account.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.assertj.core.util.Lists;

import com.exercise.account.model.Account;

public class AccountUtil {

	static private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

	public static LocalDate assumingTodaysDateIsFifteenJuly2018() {
		return LocalDate.parse("15/07/2018", formatter);
	}

	public static List<Account> getAllData() {

		List<Account> accounts = Lists.newArrayList();
		Account act = new Account().setAccountType("Test").setCustomerId(1234)
				.setDate(LocalDate.parse("10/07/2018", formatter)).setId(1).setMessage("This is a test message");
		Account act1 = new Account().setAccountType("Test").setCustomerId(1234)
				.setDate(LocalDate.parse("10/07/2018", formatter)).setId(2).setMessage("This is a test message1");
		Account act2 = new Account().setAccountType("Test").setCustomerId(1234)
				.setDate(LocalDate.parse("11/07/2018", formatter)).setId(3).setMessage("This is a test message");
		Account act3 = new Account().setAccountType("Test").setCustomerId(123)
				.setDate(LocalDate.parse("11/07/2018", formatter)).setId(4).setMessage("This is a test message");
		Account act4 = new Account().setAccountType("Test").setCustomerId(1234)
				.setDate(LocalDate.parse("12/07/2018", formatter)).setId(5).setMessage("This is a test message");
		Account act5 = new Account().setAccountType("Test").setCustomerId(1234)
				.setDate(LocalDate.parse("1/07/2018", formatter)).setId(6).setMessage("This is a test message");
		Account act6 = new Account().setAccountType("Test").setCustomerId(1234)
				.setDate(LocalDate.parse("2/07/2018", formatter)).setId(7).setMessage("This is a test message");
		Account act7 = new Account().setAccountType("Test").setCustomerId(1234)
				.setDate(LocalDate.parse("3/07/2018", formatter)).setId(8).setMessage("This is a test message");
		Account act8 = new Account().setAccountType("Test").setCustomerId(1234)
				.setDate(LocalDate.parse("4/07/2018", formatter)).setId(9).setMessage("This is a test message");
		Account act9 = new Account().setAccountType("Test").setCustomerId(1234)
				.setDate(LocalDate.parse("10/07/2018", formatter)).setId(10).setMessage("This is a test message");
		Account act10 = new Account().setAccountType("Test").setCustomerId(1237)
				.setDate(LocalDate.parse("5/07/2018", formatter)).setId(11).setMessage("This is a test message");
		Account act11 = new Account().setAccountType("Test").setCustomerId(1235)
				.setDate(LocalDate.parse("14/07/2018", formatter)).setId(12).setMessage("This is a test message");
		Account act12 = new Account().setAccountType("Test").setCustomerId(1235)
				.setDate(LocalDate.parse("14/07/2018", formatter)).setId(13).setMessage("This is a test message1");
		Account act13 = new Account().setAccountType("Test").setCustomerId(1236)
				.setDate(LocalDate.parse("15/07/2018", formatter)).setId(14).setMessage("This is a test message");
		Account act14 = new Account().setAccountType("Test").setCustomerId(1236)
				.setDate(LocalDate.parse("15/07/2018", formatter)).setId(15).setMessage("This is a test message1");
		accounts.add(act);
		accounts.add(act1);
		accounts.add(act2);
		accounts.add(act3);
		accounts.add(act4);
		accounts.add(act5);
		accounts.add(act6);
		accounts.add(act7);
		accounts.add(act8);
		accounts.add(act9);
		accounts.add(act10);
		accounts.add(act11);
		accounts.add(act12);
		accounts.add(act13);
		accounts.add(act14);
		return accounts;
	}

}
