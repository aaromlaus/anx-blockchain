package com.anx.blockchain.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.anx.blockchain.checker.EthereumService;
import com.anx.blockchain.checker.impl.EthereumServiceImpl;
import com.anx.blockchain.entity.Transaction;
import com.anx.blockchain.util.AnxUtil;

public class MainApplication {
	
	
	public static void main(String[] args) {
		List<Transaction> transactions = ImportCsvFile();
		for(Transaction tran : transactions) {
			checkTransaction(tran);
		}

	}
	
	public static void checkTransaction(Transaction transaction) {
		EthereumService ethChecker = new EthereumServiceImpl();
		try {
			ethChecker.checkTransaction(transaction);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<Transaction> ImportCsvFile() {
		List<Transaction> transactions = new ArrayList<Transaction>();
		String csvFile = "C://temp/transactions.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {
			boolean firstRow = true;
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				if(firstRow) {
					firstRow = false;
				} else {
					String[] col = line.split(cvsSplitBy, -1);
					Transaction tran = new Transaction();
					tran.setTxnId(col[0]);
					tran.setTime(col[1]);
					tran.setAddress(col[2]);
					tran.setType(col[3]);
					if(AnxUtil.isNotNullNorEmpty(col[4])) {
						tran.setAmount(new BigDecimal(col[4]));
					}
					if(AnxUtil.isNotNullNorEmpty(col[5])) {
						tran.setFee(new BigDecimal(col[5]));
					}
					transactions.add(tran);
				}
				
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return transactions;

	}

}
