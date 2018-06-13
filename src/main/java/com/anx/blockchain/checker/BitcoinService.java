package com.anx.blockchain.checker;

import org.mvp.blockchain.platform.model.BitcoinTransaction;

public interface BitcoinService {

	public BitcoinTransaction getTransactionDetails(String txId);

}
