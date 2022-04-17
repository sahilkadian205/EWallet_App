package com.assessment.ewallet.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assessment.ewallet.constants.EWalletConstants;
import com.assessment.ewallet.entities.UserWallet;
import com.assessment.ewallet.entities.UserWalletTransaction;
import com.assessment.ewallet.service.EWalletService;
import com.assessment.ewallet.util.EWalletUtil;

@RestController
public class EWalletController {
	
	Logger logger = LoggerFactory.getLogger(EWalletController.class);
	
	@Autowired
	private EWalletService ewalletService;
	
	@PostMapping("/ewallet/signup")
	public String addUserWallet(@RequestBody UserWallet userWallet) {
		logger.trace("Entered addUserWallet() method.....");
		String response =  EWalletUtil.validateUserWalletSignUp(userWallet);
		logger.trace(response);
		if(EWalletConstants.VALIDATION_PASS.equals(response)) {
			response = ewalletService.addUser(userWallet);
		}
		logger.trace("addUserWallet() method execution completion....");
		return response;
	}
	
	@GetMapping("/ewallet/login")
	public String loginUser(@RequestBody UserWallet userWallet) {
		logger.trace("Entered loginUser() method.....");
		String response = EWalletUtil.validateUserWalletSignIn(userWallet);
		logger.trace(response);
		if(EWalletConstants.VALIDATION_PASS.equals(response)) {
			response = ewalletService.loginUser(userWallet);
		}
		logger.trace("loginUser() method execution completion....");
		return response;
	}
	
	@GetMapping("/ewallet/user/{phoneNum}")
	public String getUser(@PathVariable String phoneNum) {
		logger.trace("Entered getUser() method.....");
		String response = EWalletUtil.validatePhoneNum(phoneNum);
		logger.trace(response);
		if(EWalletConstants.VALIDATION_PASS.equals(response)) {
			response = ewalletService.viewUser(phoneNum);
		}
		logger.trace("getUser() method execution completion....");
		return response;
	}
	
	@PutMapping("/ewallet/user")
	public String updateUser(@RequestBody UserWallet userWallet) {
		logger.trace("Entered updateUser() method.....");
		String response = EWalletUtil.validateOnUserUpdate(userWallet);
		logger.trace(response);
		if(EWalletConstants.VALIDATION_PASS.equals(response)) {
			response = ewalletService.updateUser(userWallet);
		}
		logger.trace("updateUser() method execution completion....");
		return response;
	}
	
	@PutMapping("/ewallet/addmoney")
	public String addMoney(@RequestBody UserWallet userWallet) {
		logger.trace("Entered addMoney() method.....");
		String response = EWalletUtil.validateOnAddMoney(userWallet);
		logger.trace(response);
		if(EWalletConstants.VALIDATION_PASS.equals(response)) {
			response = ewalletService.addMoney(userWallet);
		}
		logger.trace("addMoney() method execution completion....");
		return response;
	}
	
	@PutMapping("/ewallet/transfermoney")
	public String transferMoney(@RequestBody UserWallet userWallet) {
		logger.trace("Entered transferMoney() method.....");
		String response = EWalletUtil.validateOnTransferMoney(userWallet);
		logger.trace(response);
		if(EWalletConstants.VALIDATION_PASS.equals(response)) {
			response = ewalletService.transferMoney(userWallet);
		}
		logger.trace("transferMoney() method execution completion....");
		return response;
	}
	
	@GetMapping("/ewallet/transactions/{phoneNum}")
	public List<UserWalletTransaction> getUserTransactions(@PathVariable String phoneNum){
		logger.trace("Entered getUserTransactions() method.....");
		List<UserWalletTransaction> list = ewalletService.getUserTransactions(phoneNum);
		logger.trace("getUserTransactions() method execution completion....");
		return list;
	}
	
}
