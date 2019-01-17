package productapi.data;

//Bean used to combine ProductData and PriceData
public class Product {

	private ProductData productData;
	private PriceData currentPrice;

	public Product(ProductData productData, PriceData currentPrice) {
		this.productData = productData;
		this.currentPrice = currentPrice;
	}

	public ProductData getProductData() {
		return productData;
	}

	public void setProductData(ProductData productData) {
		this.productData = productData;
	}

	public PriceData getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(PriceData currentPrice) {
		this.currentPrice = currentPrice;
	}
	
}