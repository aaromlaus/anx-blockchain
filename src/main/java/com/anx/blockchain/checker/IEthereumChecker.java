package com.anx.blockchain.checker;

import java.io.IOException;

import com.anx.blockchain.entity.Transaction;

public interface IEthereumChecker {

	public void checkTransaction(Transaction transaction) throws IOException;
}
