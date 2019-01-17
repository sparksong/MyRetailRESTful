package productapi.data;

//Bean used to hold Product information retrieved from redsky API call.
public class ProductData {

	private String id;
	private String name;

	public ProductData(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}