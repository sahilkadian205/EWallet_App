package com.assessment.ewallet.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
public class UserWallet {
	
	@Id
	private String phoneNum;
	
	private String userName;
	
	private String userPassword;
	
	private double walletBalance;
	
	@JsonInclude()
	@Transient
	private double amountToAdd;
	
	@JsonInclude()
	@Transient
	private String toTransferWalletId;
	
	@JsonInclude()
	@Transient
	private double amountToTransfer;
	
	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public double getWalletBalance() {
		return walletBalance;
	}
	
	public void setWalletBalance(double walletBalance) {
		this.walletBalance = walletBalance;
	}	
	
	public String getToTransferWalletId() {
		return toTransferWalletId;
	}

	public void setToTransferWalletId(String toTransferWalletId) {
		this.toTransferWalletId = toTransferWalletId;
	}
	
	public double getAmountToAdd() {
		return amountToAdd;
	}

	public void setAmountToAdd(double amountToAdd) {
		this.amountToAdd = amountToAdd;
	}

	public double getAmountToTransfer() {
		return amountToTransfer;
	}

	public void setAmountToTransfer(double amountToTransfer) {
		this.amountToTransfer = amountToTransfer;
	}
	
	@Override
	public String toString() {
		return "User Profile -\n"
			   +"Phone Num - "+phoneNum+"\n"
			   +"Username - "+userName+"\n"
			   +"Wallet Balance - "+walletBalance;
	}
	
}
