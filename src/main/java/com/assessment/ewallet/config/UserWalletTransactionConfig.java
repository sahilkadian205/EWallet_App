package com.assessment.ewallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.assessment.ewallet.entities.UserWalletTransaction;

@Configuration
public class UserWalletTransactionConfig {

	@Bean
	public UserWalletTransaction getUserWalletTransaction() {
		return new UserWalletTransaction();
	}
}
