package com.assessment.ewallet.util;

import com.assessment.ewallet.constants.EWalletConstants;
import com.assessment.ewallet.entities.UserWallet;

public class EWalletUtil {
	
	public static String validateUserWalletSignUp(UserWallet userWallet) {
		String phoneNum = userWallet.getPhoneNum();
		String userName = userWallet.getUserName();
		String userPassword = userWallet.getUserPassword();
		String result = validatePhoneNum(phoneNum); 
		if (EWalletConstants.VALIDATION_PASS.equals(result)) {
			result = validateUserName(userName);
			if (EWalletConstants.VALIDATION_PASS.equals(result)) {
				result = validateUserPassword(userPassword);
			}
		}
		return result;
	}
	
	public static String validateUserWalletSignIn(UserWallet userWallet) {
		String phoneNum = userWallet.getPhoneNum();
		String userPassword = userWallet.getUserPassword();
		String result = validatePhoneNum(phoneNum);
		if (EWalletConstants.VALIDATION_PASS.equals(result)) {
			result = validateUserPassword(userPassword);
		}
		return result;
	}
	
	public static String validatePhoneNum(String phoneNum) {
		if(!phoneNum.matches(EWalletConstants.PHONE_NUM_REGEX)) {
			return "Phone num should be 10 digits and can't start with 0";
		}
		return EWalletConstants.VALIDATION_PASS;
	}	
	
	public static String validateUserName(String userName) {
		if (null == userName || userName.isEmpty()) {
			return "User Name Can't Be Blank";
		}
		return EWalletConstants.VALIDATION_PASS;
	}
	
	public static String validateUserPassword(String password) {
		if (null == password || password.isEmpty()) {
			return "Password Can't Be Blank";
		}
		if (password.length() < EWalletConstants.PASSWORD_MIN_LEN || password.length() > EWalletConstants.PASSWORD_MAX_LEN) {
			return "Password length should be greater than " +EWalletConstants.PASSWORD_MIN_LEN +" and less than "+EWalletConstants.PASSWORD_MAX_LEN;
		}
		return EWalletConstants.VALIDATION_PASS;
	}
	
	public static String validateOnUserUpdate(UserWallet userWallet) {
		String phoneNum = userWallet.getPhoneNum();
		String userName = userWallet.getUserName();
		String userPassword = userWallet.getUserPassword();
		String result = validatePhoneNum(phoneNum);
		if (EWalletConstants.VALIDATION_PASS.equals(result) && ((null != userName && userName.isEmpty()) || 
				(null != userPassword && (userPassword.isEmpty() || userPassword.length() < EWalletConstants.PASSWORD_MIN_LEN || userPassword.length() >EWalletConstants.PASSWORD_MAX_LEN)))) {
			result = "Invalid Input, Can't Update";
		}
		return result;
	}
	
	public static String validateOnAddMoney(UserWallet userWallet) {
		String phoneNum = userWallet.getPhoneNum();
		double moneyToAdd = userWallet.getAmountToAdd();
		String result = validatePhoneNum(phoneNum);
		if(EWalletConstants.VALIDATION_PASS.equals(result)) {
			if(moneyToAdd <= 0) {
				result = EWalletConstants.ADD_MONEY_GREATER_THAN_ZERO;
			}
		}
		return result;
	}
	
	public static String validateOnTransferMoney(UserWallet userWallet) {
		String toUserPhoneNum = userWallet.getToTransferWalletId();
		double amountToTransfer = userWallet.getAmountToTransfer();
		String result = validatePhoneNum(toUserPhoneNum);
		if(EWalletConstants.VALIDATION_PASS.equals(result)) {
			if(amountToTransfer <= 0) {
				result = EWalletConstants.ADD_MONEY_GREATER_THAN_ZERO;
			}
		}
		return result;
	}

}
