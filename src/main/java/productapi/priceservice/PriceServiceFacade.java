package productapi.priceservice;

import productapi.data.PriceData;

public interface PriceServiceFacade {
	public PriceData getProductPriceByID(String id);
	
	public PriceData updateProductPrice(String id, String value, String currencyCode);
	
}
