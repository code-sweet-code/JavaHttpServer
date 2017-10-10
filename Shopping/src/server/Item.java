package server;

public class Item {
	private String name;
	private String quantity;
	private String optional;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getOptional() {
		return optional;
	}
	public void setOptional(String optional) {
		this.optional = optional;
	}
}
