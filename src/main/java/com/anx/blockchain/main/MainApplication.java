package com.anx.blockchain.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.anx.blockchain.checker.BitcoinService;
import com.anx.blockchain.checker.EthereumService;
import com.anx.blockchain.checker.impl.BitcoinServiceImpl;
import com.anx.blockchain.checker.impl.EthereumServiceImpl;
import com.anx.blockchain.entity.BitcoinTransaction;
import com.anx.blockchain.entity.Transaction;
import com.anx.blockchain.entity.TxInput;
import com.anx.blockchain.entity.TxOutput;
import com.anx.blockchain.util.AnxUtil;
import com.anx.blockchain.util.BitcoinMath;
import com.anx.blockchain.util.CsvToJson;
import com.anx.blockchain.util.PropertyUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class MainApplication {
	
	
	public static void main(String[] args) {
		List<Transaction> transactions = ImportCsvFile();
		for(Transaction tran : transactions) {
			checkTransaction(tran);
		}
		runBtcChecker();
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
						tran.setAmount(col[4]);
					}
					if(AnxUtil.isNotNullNorEmpty(col[5])) {
						tran.setFee(col[5]);
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
	
	
	public static void runBtcChecker() {

		File input = new File(PropertyUtil.getProps("excel.file.path"));
		BitcoinService btcService = new BitcoinServiceImpl();
		Gson gson = new Gson();
		List<Map<?, ?>> data;
		try {
			data = CsvToJson.readObjectsFromCsv(input);
			JsonParser jsonParser = new JsonParser();
			JsonArray arrayFromString = jsonParser.parse(gson.toJson(data)).getAsJsonArray();
			List<Transaction> transactionList = new ArrayList<Transaction>();
			
			for(JsonElement json : arrayFromString) {
				transactionList.add(gson.fromJson(json, Transaction.class));
			}
			int index = 1;
			for(Transaction t : transactionList) {
				//check where
				BitcoinTransaction btcTx = btcService.getTransactionDetails(t.getTxnId());

				System.out.println(t);
				System.out.println(btcTx);

				boolean hasMatch = false;
				if(btcTx != null) {
					if(t.getType().equalsIgnoreCase("Send")) {//Out
						for(TxOutput o : btcTx.getOutputs()) {
							
							if(o.getToAddress().equals(t.getAddress())){
								if(BitcoinMath.satoshiToBtc(Long.valueOf(o.getValue())).compareTo(t.getAmount()) == 0 &&
										BitcoinMath.satoshiToBtc(Long.valueOf(btcTx.getTransactionFee())).compareTo(t.getFee().abs()) == 0  && 
										btcTx.getCreateDateTime() == Long.valueOf(t.getTime())){
										System.out.println("line "+index++  +" valid");
								}else {
									System.out.println("line "+index++ +" invalid");
								}
								hasMatch = true;
							}
						}
						
						if(!hasMatch) {
							System.out.println("incorrect to address in line "+index++ );
						}
					}else if(t.getType().equalsIgnoreCase("Receive")) {//In
						for(TxInput i : btcTx.getInputs()) {
							
							if(i.getToAddress().equals(t.getAddress())){
								if(BitcoinMath.satoshiToBtc(Long.valueOf(i.getValue())).compareTo(t.getAmount()) == 0 && 
										btcTx.getCreateDateTime() == Long.valueOf(t.getTime())){
										System.out.println("line "+index++  +" valid");
								}else {
									System.out.println("line "+index++ +" invalid");
								}
								hasMatch = true;
							}
						}
						
						if(!hasMatch) {
							System.out.println("incorrect to address in line "+index++ );
						}
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
