package com.anx.blockchain.checker;

import com.anx.blockchain.entity.BitcoinTransaction;

public interface BitcoinService {

	public BitcoinTransaction getTransactionDetails(String txId);

}
