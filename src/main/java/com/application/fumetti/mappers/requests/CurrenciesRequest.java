package com.application.fumetti.mappers.requests;

import com.application.fumetti.mappers.results.CurrencyResult;

import java.math.BigDecimal;

public class CurrenciesRequest extends CurrencyResult {

    public CurrenciesRequest(String name, String symbol, BigDecimal valueLire, BigDecimal valueEuro) {
        super(null, name, symbol, valueLire, valueEuro);
    }
}
