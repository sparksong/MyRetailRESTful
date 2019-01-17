package productapi.data;

//Class used to hold Price information retrieved from call to database.
public class PriceData {

    private String value;
    private String currencyCode;
    
    public PriceData(String value, String currencyCode) {
        this.value = value;
        this.currencyCode = currencyCode;
    }

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

}