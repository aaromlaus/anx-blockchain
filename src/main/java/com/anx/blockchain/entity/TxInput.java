package com.anx.blockchain.entity;

import com.google.gson.annotations.SerializedName;

public class TxInput {
	@SerializedName("addr")
	private String toAddress; // addr

	@SerializedName("value")
	private String value; // value
	
	@SerializedName("script")
	private String script; // script
	
	@SerializedName("spent")
	private boolean spent;// spent
	
	@SerializedName("type")
	private String type; // type
	
	@SerializedName("n")
	private String n; // n

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public boolean isSpent() {
		return spent;
	}

	public void setSpent(boolean spent) {
		this.spent = spent;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	@Override
	public String toString() {
		return "TxInput [toAddress=" + toAddress + ", value=" + value + ", script=" + script + ", spent=" + spent
				+ ", type=" + type + ", n=" + n + "]";
	}
}
