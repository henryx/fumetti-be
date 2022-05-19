package com.application.fumetti.mappers.requests;

import com.application.fumetti.mappers.results.CurrencyResult;
import com.application.fumetti.mappers.results.NationResult;

public class NationsRequest extends NationResult {
    public NationsRequest(String name, String sign, CurrencyResult currency) {
        super(null, name, sign, currency);
    }
}
