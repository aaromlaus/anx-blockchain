package com.anx.blockchain.util;

import java.math.BigDecimal;

import org.web3j.utils.Convert.Unit;

public class UnitConverter {

	public static BigDecimal fromWei(String number, Unit unit) {
        return fromWei(new BigDecimal(number), unit);
    }

    public static BigDecimal fromWei(BigDecimal number, Unit unit) {
        return number.divide(unit.getWeiFactor());
    }
}
