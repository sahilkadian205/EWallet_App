package com.assessment.ewallet.service;

import java.util.List;

import com.assessment.ewallet.entities.UserWallet;
import com.assessment.ewallet.entities.UserWalletTransaction;

public interface EWalletService {
	
	String addUser(UserWallet userWallet);
	
	String loginUser(UserWallet userWallet);
	
	boolean isExistingUser(String phoneNum);
	
	String viewUser(String phoneNum);
	
	String updateUser(UserWallet userWallet);
	
	String addMoney(UserWallet userWallet);
	
	String transferMoney(UserWallet userWallet);
	
	List<UserWalletTransaction> getUserTransactions(String phoneNum);
	
}
