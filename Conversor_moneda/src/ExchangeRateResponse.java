import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class ExchangeRateResponse {
    @SerializedName("result")
    private String result;
    @SerializedName("base_code")
    private String base_code;
    @SerializedName("conversion_rates")
    private Map<String, Double> conversion_rates;

    public ExchangeRateResponse() {
    }

    public String getResult() {
        return result;
    }

    public String getBase_code() {
        return base_code;
    }

    public Map<String, Double> getConversion_rates() {
        return conversion_rates;
    }
}
