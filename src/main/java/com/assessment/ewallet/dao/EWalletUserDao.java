package com.assessment.ewallet.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assessment.ewallet.entities.UserWallet;

public interface EWalletUserDao extends JpaRepository<UserWallet, String>{
	

}
