package com.assessment.ewallet.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import com.assessment.ewallet.config.UserWalletTransactionConfig;
import com.assessment.ewallet.constants.EWalletConstants;
import com.assessment.ewallet.dao.EWalletTranscationDao;
import com.assessment.ewallet.dao.EWalletUserDao;
import com.assessment.ewallet.entities.UserWallet;
import com.assessment.ewallet.entities.UserWalletTransaction;
import com.assessment.ewallet.exception.UserNotFoundException;

@Service
public class EWalletServiceImpl implements EWalletService {

	Logger logger = LoggerFactory.getLogger(EWalletServiceImpl.class);
	
	@Autowired
	private EWalletUserDao ewalletUserDao;
	 
	@Autowired 
	private EWalletTranscationDao ewalletTranscationDao;
	 
	/**
	 *This method will add a non-existing user
	 */
	@Override
	public String addUser(UserWallet userWallet) {
		logger.trace("Entered addUser() method");
		String phoneNum = userWallet.getPhoneNum();
		if(isExistingUser(phoneNum)) {
			logger.trace("User already have an account");
			return "You already have an account on EWallet"; 
		}
		ewalletUserDao.save(userWallet);
		logger.trace("User Added Successfully");
		logger.trace("addUser() method execution completion....");
		return userWallet.getUserName()+", Welcome To EWallet";
	}
	
	/**
	 *This method will login a user
	 */ 
	@Override
	public String loginUser(UserWallet userWallet) {
		logger.trace("Entered loginUser() method");
		String phoneNum = userWallet.getPhoneNum();
		String userPassword = userWallet.getUserPassword();
		if(!isExistingUser(phoneNum)) {
			logger.trace("User Not Found Exception Occured...");
			throw new UserNotFoundException(EWalletConstants.NON_EXISTING_USER);
		}
		UserWallet userWalletValidation = ewalletUserDao.getById(phoneNum);
		if(userPassword.equals(userWalletValidation.getUserPassword())) {
			logger.trace("Successful login");
			return "You have successfully logged-in";
		}
		logger.trace("loginUser() method execution completion....");
		return "Your password is incorrect";
	}

	/**
	 *This method will check if user is existing or not
	 */
	@Override
	public boolean isExistingUser(String phoneNum) {
		logger.trace("Entered isExistingUser() method");
		if(ewalletUserDao.existsById(phoneNum)) {
			logger.trace("Existing User");
			return true;
		}
		logger.trace("isExistingUser() method execution completion....");
		return false;
	}

	/**
	 *This method will view a user details
	 */
	@Override
	public String viewUser(String phoneNum) {
		logger.trace("Entered viewUser() method");
		if(!isExistingUser(phoneNum)) {
			logger.trace("User Not Found Exception Occured...");
			throw new UserNotFoundException(EWalletConstants.NON_EXISTING_USER);
		}
		logger.trace("viewUser() method execution completion....");
		return ewalletUserDao.getById(phoneNum).toString();
	}

	/**
	 *This method will update a user
	 */
	@Override
	public String updateUser(UserWallet userWallet) {
		logger.trace("Entered updateUser() method");
		String phoneNum = userWallet.getPhoneNum();
		String userName = userWallet.getUserName();
		String userPassword = userWallet.getUserPassword();
		if(!isExistingUser(phoneNum)) {
			logger.trace("User Not Found Exception Occured...");
			throw new UserNotFoundException(EWalletConstants.NON_EXISTING_USER);
		}
		UserWallet userWalletDB = ewalletUserDao.getById(phoneNum);
		if(null != userName && !userName.isEmpty()) {
			logger.trace("updated user name");
			userWalletDB.setUserName(userName);
		}
		if(null != userPassword && !userPassword.isEmpty() 
				&& userPassword.length() >= EWalletConstants.PASSWORD_MIN_LEN && userPassword.length() < EWalletConstants.PASSWORD_MAX_LEN) {
			logger.trace("updated password");
			userWalletDB.setUserPassword(userPassword);
		}
		ewalletUserDao.save(userWalletDB);
		logger.trace("updateUser() method execution completion....");
		return "Updated Successfully";
	}

	/**
	 *This method will add money to user's wallet
	 */
	@Override
	public String addMoney(UserWallet userWallet) {
		logger.trace("Entered addMoney() method");
		String phoneNum = userWallet.getPhoneNum();
		double amountToAdd = userWallet.getAmountToAdd();
		if(!isExistingUser(phoneNum)) {
			logger.trace("User Not Found Exception Occured...");
			throw new UserNotFoundException(EWalletConstants.NON_EXISTING_USER);
		}
		UserWallet userWalletDB = ewalletUserDao.getById(phoneNum);
		double currentBal = userWalletDB.getWalletBalance();
		currentBal += amountToAdd;
		userWalletDB.setWalletBalance(currentBal);
		ewalletUserDao.save(userWalletDB);
		addMoneyTransaction(userWallet);
		logger.trace("addMoney() method execution completion....");
		return amountToAdd+" added successfully. Your current wallet balance is: "+currentBal;
	}

	/**
	 *This method will transfer user's wallet money
	 */
	@Override
	public String transferMoney(UserWallet userWallet) {
		logger.trace("Entered transferMoney() method");
		String fromUserPhoneNum = userWallet.getPhoneNum();
		String toUserPhoneNum = userWallet.getToTransferWalletId();
		double amountToTransfer = userWallet.getAmountToTransfer();
		if (fromUserPhoneNum.equals(toUserPhoneNum)) {
			logger.trace("User added money in own account");
			return "You can't transfer money to yourself";
		}
		if (!isExistingUser(toUserPhoneNum)) {
			logger.trace("User Not Found Exception Occured...");
			throw new UserNotFoundException("You can't transfer money to non-registered user on EWallet");
		}
		UserWallet fromUser = ewalletUserDao.getById(fromUserPhoneNum);
		UserWallet toUser = ewalletUserDao.getById(toUserPhoneNum);
		double fromUserCurBal = fromUser.getWalletBalance();
		double toUserCurBal = toUser.getWalletBalance();
		if (amountToTransfer > fromUserCurBal) {
			return "Insufficient Balance To Transfer";
		}
		toUserCurBal += amountToTransfer;
		fromUserCurBal -= amountToTransfer;
		fromUser.setWalletBalance(fromUserCurBal);
		toUser.setWalletBalance(toUserCurBal);
		ewalletUserDao.save(fromUser);
		ewalletUserDao.save(toUser);
		transferMoneyTransaction(userWallet);
		logger.trace("transferMoney() method execution completion....");
		return amountToTransfer + " transferred successfully to " + toUserPhoneNum + " .Your current balance is "
				+ fromUserCurBal;
	}
	
	/**
	 *This method will create UserWalletTransaction Object
	 */
	private UserWalletTransaction getUserWalletTransactionObj() {
		logger.trace("Entered getUserWalletTransactionObj() method");
		ApplicationContext factory = new AnnotationConfigApplicationContext(UserWalletTransactionConfig.class);
		logger.trace("getUserWalletTransactionObj() method execution completion....");
		return factory.getBean(UserWalletTransaction.class);		
	}
	
	/**
	 *This method will save add money transaction
	 */
	private void addMoneyTransaction(UserWallet userWallet) {
		logger.trace("Entered addMoneyTransaction() method");
		String phoneNum = userWallet.getPhoneNum();
		double amountToAdd = userWallet.getAmountToAdd();
		UserWalletTransaction userWalletTransaction = getUserWalletTransactionObj();
		userWalletTransaction.setAmount(amountToAdd);
		userWalletTransaction.setFromUser(phoneNum);
		userWalletTransaction.setTransactionType(EWalletConstants.TRANSACTION_TYPE_ADD_MONEY);
		userWalletTransaction.setTransactionDate(new Date());
		ewalletTranscationDao.save(userWalletTransaction);
		logger.trace("addMoneyTransaction() method execution completion....");
	}
	
	/**
	 *This method will save transfer money transaction
	 */
	private void transferMoneyTransaction(UserWallet userWallet) {
		logger.trace("Entered transferMoneyTransaction() method");
		String fromUserPhoneNum = userWallet.getPhoneNum();
		String toUserPhoneNum = userWallet.getToTransferWalletId();
		double amountToTransfer = userWallet.getAmountToTransfer();
		UserWalletTransaction userWalletTransaction = getUserWalletTransactionObj();
		userWalletTransaction.setAmount(amountToTransfer);
		userWalletTransaction.setFromUser(fromUserPhoneNum);
		userWalletTransaction.setToUser(toUserPhoneNum);
		userWalletTransaction.setTransactionType(EWalletConstants.TRANSACTION_TYPE_TRANSFER_MONEY);
		userWalletTransaction.setTransactionDate(new Date());
		ewalletTranscationDao.save(userWalletTransaction);
		logger.trace("transferMoneyTransaction() method execution completion....");
	}

	/**
	 *This method will get user's all transactions
	 */
	@Override
	public List<UserWalletTransaction> getUserTransactions(String phoneNum) {
		logger.trace("Entered getUserTransactions() method.....");
		List<UserWalletTransaction> list = ewalletTranscationDao.getUserTransactions(phoneNum);
		logger.trace("getUserTransactions() method execution completion....");
		return list;
	}
}
