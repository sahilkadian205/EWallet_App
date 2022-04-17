package com.assessment.ewallet.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.assessment.ewallet.entities.UserWalletTransaction;

public interface EWalletTranscationDao extends JpaRepository<UserWalletTransaction, Long>{
	
	@Query("select uwt from user_wallet_transaction uwt where uwt.fromUser = ?1 order by uwt.transactionDate desc")
	public List<UserWalletTransaction> getUserTransactions(String userId);

}
