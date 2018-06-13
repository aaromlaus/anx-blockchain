package com.anx.blockchain.checker.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert.Unit;

import com.anx.blockchain.checker.IEthereumChecker;
import com.anx.blockchain.entity.Transaction;
import com.anx.blockchain.util.UnitConverter;

public class EthereumChecker implements IEthereumChecker {
	
	public void checkTransaction(Transaction transaction) throws IOException {
		Web3j web3j = Web3j.build(new HttpService("https://ropsten.infura.io/bIiZ9ZlgIRraYClar19x"));
//		System.out.println("Connected to Ethereum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());

		String txnId = transaction.getTxnId();
		EthTransaction tran = web3j
				.ethGetTransactionByHash(txnId).send();
		if(tran.getError() == null) {
			// Get Transaction Receipt
			EthGetTransactionReceipt tr = web3j
					.ethGetTransactionReceipt(txnId).send();
			
			// Get the Block
			EthBlock eb = web3j.ethGetBlockByNumber(
					DefaultBlockParameter.valueOf(tr.getTransactionReceipt().get().getBlockNumber()), true).send();
			LocalDateTime timestamp = Instant.ofEpochSecond(eb.getResult().getTimestamp().longValueExact())
					.atZone(ZoneId.of("UTC")).toLocalDateTime();
			System.out.println("VERIFY TIME STAMP FROM UX : " + timestamp);

			String address = tran.getResult().getFrom();
			BigDecimal amount = UnitConverter.fromWei(new BigDecimal(tran.getResult().getValue()), Unit.ETHER);

			BigDecimal gasPrice = UnitConverter.fromWei(new BigDecimal(tran.getTransaction().get().getGasPrice()), Unit.ETHER);
			BigInteger gasUsed = tr.getResult().getGasUsed();

			System.out.println(gasPrice.toPlainString() + " x " + gasUsed);
			BigDecimal gasUsedByTxn = gasPrice.multiply(new BigDecimal(gasUsed));

			System.out.println("TXN ID : " + txnId);
			System.out.println("TXN ID : " + transaction.getTxnId());
			
			System.out.println("TIME : " + eb.getBlock().getTimestamp());
			System.out.println("TIME : " + transaction.getTime());
			
			System.out.println("ADDRESS : " + address);
			System.out.println("ADDRESS : " + transaction.getAddress());
			
			System.out.println("AMOUNT : " + amount);
			System.out.println("AMOUNT : " + transaction.getAmount());
			
			System.out.println("FEE : " + gasUsedByTxn);
			System.out.println("FEE : " + transaction.getFee());
			
			System.out.println("TXN ID Matched? : " + txnId.equals(transaction.getTxnId()));
			System.out.println("TIME Matched? : " + eb.getBlock().getTimestamp().toString().equals(transaction.getTime()));
			System.out.println("ADDRESS Matched? : " + address.equals(transaction.getAddress()));
			System.out.println("AMOUNT Matched? : " + (amount.compareTo(transaction.getAmount()) == 0));
			System.out.println("FEE Matched? : " + (gasUsedByTxn.compareTo(transaction.getFee()) == 0));
		}
		
	}
}
