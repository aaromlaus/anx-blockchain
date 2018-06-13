package com.anx.blockchain.entity;

import java.math.BigDecimal;

import com.google.gson.annotations.SerializedName;

public class Transaction {

	@SerializedName("txid")
	private String txnId;
	
	@SerializedName("time")
	private String time;
	
	@SerializedName("address")
	private String address;
	
	@SerializedName("type")
	private String type;
	
	@SerializedName("amount")
	private BigDecimal amount;
	
	@SerializedName("fee")
	private BigDecimal fee;

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
}
