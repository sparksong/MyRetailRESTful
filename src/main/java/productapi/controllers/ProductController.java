package productapi.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import productapi.data.PriceData;
import productapi.data.Product;
import productapi.data.ProductData;
import productapi.priceservice.PriceServiceFacadeImpl;

//Controller to handle CRUD information regarding Product / Price Data
@RestController
public class ProductController {

	@Value(value = "https://redsky.target.com/v2/pdp/tcin/")
	private String startURL;
	@Value(value = "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics")
	private String endURL;

	private PriceServiceFacadeImpl priceServiceFacade = new PriceServiceFacadeImpl();

	// Public method used to call API and NoSQL DB for product and price information using product id returning combined object.
	@RequestMapping("/product/{id}")
	public Product getProductById(@PathVariable("id") String id) {

		ProductData productDataFromAPICall = new ProductData(id, getProductTitleById(id));
		PriceData priceDataFromDatabase = priceServiceFacade.getProductPriceByID(id);

		return new Product(productDataFromAPICall, priceDataFromDatabase);
	}
	
	@RequestMapping("/product/{id}/value={value}/currencyCode={currencyCode}")
	public Product updatePriceById( @PathVariable("id") String id, 
									@PathVariable("value") String value, 
									@PathVariable("currencyCode") String currencyCode) {

		ProductData productDataFromAPICall = new ProductData(id, getProductTitleById(id));
		
		PriceData updatedPriceData = priceServiceFacade.updateProductPrice(id, value, currencyCode);

		return new Product(productDataFromAPICall, updatedPriceData);
	}

	private String getProductTitleById(String id) {
		// Create URL using id
		String urlString = startURL + id + endURL;

		StringBuilder result = new StringBuilder();
		String productTitle = "";
		try {
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			
			rd.close();

			JSONObject resultObject = new JSONObject(result.toString());

			productTitle = resultObject.getJSONObject("product").getJSONObject("item")
					.getJSONObject("product_description").getString("title");

		} catch (IOException e) {
			// TODO If time add in logging with SLF4J
			// Error calling API for product information
			e.printStackTrace();
		} catch (JSONException e) {
			// Error parsing JSON
			e.printStackTrace();
		}

		return productTitle;
	}

}