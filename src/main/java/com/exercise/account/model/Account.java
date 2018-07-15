package com.exercise.account.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Account")
@Accessors(chain=true)
public class Account {

	@Id
	@GeneratedValue
	@JsonIgnore
	private int id;

	@Column(name = "customerId")
	private int customerId;

	@Column(name = "accountType")
	private String accountType;

	@Column(name = "message")
	private String message;

	@Column(name = "CREATED_DATE")
	@JsonIgnore
	LocalDate date = LocalDate.now();

}
