package com.anx.blockchain.checker.impl;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.mvp.blockchain.platform.model.BitcoinTransaction;
import org.mvp.blockchain.platform.model.TxInput;
import org.mvp.blockchain.platform.model.TxOutput;
import org.mvp.blockchain.platform.service.BitcoinService;
import org.mvp.blockchain.util.ApiUtil;
import org.mvp.blockchain.util.PropertyUtil;

import com.google.gson.Gson;

public class BitcoinServiceImpl implements BitcoinService{

	public static final String BITCOIN_TEST_HOST = PropertyUtil.getProps("bitcoin.url.test");
	public static final String BITCOIN_TX_API = "rawtx/";
	public static final String BITCOIN_TXFEE_API = "q/txfee/";
	
	public BitcoinTransaction getTransactionDetails(String txId) {
		Gson gson = new Gson();
		JSONObject json = ApiUtil.callApi(BITCOIN_TEST_HOST+BITCOIN_TX_API+txId);
		BitcoinTransaction btc = gson.fromJson(json.toString(), BitcoinTransaction.class);

		List<TxOutput> out = new ArrayList<TxOutput>();
		for(Object jArr : json.getJSONArray("out")) {
			out.add(gson.fromJson(jArr.toString(), TxOutput.class));
		}
		
		List<TxInput> in = new ArrayList<TxInput>();
		for(Object jArr : json.getJSONArray("inputs")) {
			Object prev_out = new JSONObject(jArr.toString()).get("prev_out");
			in.add(gson.fromJson(prev_out.toString(), TxInput.class));
		}
		btc.setInputs(in);
		btc.setOutputs(out);
		
		long txFee = Long.valueOf(ApiUtil.callApiStr(BITCOIN_TEST_HOST+BITCOIN_TXFEE_API+txId));
		
		btc.setTransactionFee(txFee);
		
		return btc;
	}

	
}
