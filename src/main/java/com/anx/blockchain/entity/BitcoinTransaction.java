package com.anx.blockchain.entity;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class BitcoinTransaction {
	@SerializedName("hash")
	private String transactionId; // hash

	@SerializedName("tx_index")
	private String transactionIndex; // tx_index

	@SerializedName("block_height")
	private String blockHeight; // block_height

	@SerializedName("double_spend")
	private boolean doubleSpend; // double_spend

	@SerializedName("size")
	private double size; // size

	@SerializedName("weight")
	private double weight; // weight

	@SerializedName("time")
	private long createDateTime; // time
	
	private long transactionFee;

	private List<TxOutput> outputs;

	private List<TxInput> inputs;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionIndex() {
		return transactionIndex;
	}

	public void setTransactionIndex(String transactionIndex) {
		this.transactionIndex = transactionIndex;
	}

	public String getBlockHeight() {
		return blockHeight;
	}

	public void setBlockHeight(String blockHeight) {
		this.blockHeight = blockHeight;
	}

	public boolean isDoubleSpend() {
		return doubleSpend;
	}

	public void setDoubleSpend(boolean doubleSpend) {
		this.doubleSpend = doubleSpend;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public long getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(long createDateTime) {
		this.createDateTime = createDateTime;
	}

	public long getTransactionFee() {
		return transactionFee;
	}

	public void setTransactionFee(long transactionFee) {
		this.transactionFee = transactionFee;
	}

	public List<TxOutput> getOutputs() {
		return outputs;
	}

	public void setOutputs(List<TxOutput> outputs) {
		this.outputs = outputs;
	}

	public List<TxInput> getInputs() {
		return inputs;
	}

	public void setInputs(List<TxInput> inputs) {
		this.inputs = inputs;
	}

	@Override
	public String toString() {
		return "BitcoinTransaction [transactionId=" + transactionId + ", transactionIndex=" + transactionIndex
				+ ", blockHeight=" + blockHeight + ", doubleSpend=" + doubleSpend + ", size=" + size + ", weight="
				+ weight + ", createDateTime=" + createDateTime + ", transactionFee=" + transactionFee + ", outputs="
				+ outputs + ", inputs=" + inputs + "]";
	}

	
}
