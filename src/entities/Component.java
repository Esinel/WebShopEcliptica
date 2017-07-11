package entities;

import java.math.BigDecimal;

public class Component {
	private int code;
	private String name;
	private BigDecimal price; //has a lot of rounding modes
	private int availableQuantity;
	private String description;
	private Category category;
	private String linkManifacturer;
	private String image; //filesystem link
	
	
	public Component(){}
	
	public Component(Component component){
		super();
		this.code = component.code;
		this.name = component.name;
		this.price = component.price;
		this.availableQuantity = component.availableQuantity;
		this.description = component.description;
		this.category = component.category;
		this.linkManifacturer = component.linkManifacturer;
		this.image = component.image;
	}
	
	public Component(String name, BigDecimal price, int availableQuantity, String description,
			Category category, String linkManifacturer, String image) {
		super();
		this.name = name;
		this.price = price;
		this.availableQuantity = availableQuantity;
		this.description = description;
		this.category = category;
		this.linkManifacturer = linkManifacturer;
		this.image = image;
	}
	
	
	//create with given ID
	public Component(int code, String name, BigDecimal price, int availableQuantity, String description,
			Category category, String linkManifacturer, String image) {
		super();
		this.code = code;
		this.name = name;
		this.price = price;
		this.availableQuantity = availableQuantity;
		this.description = description;
		this.category = category;
		this.linkManifacturer = linkManifacturer;
		this.image = image;
	}
	
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public int getAvailableQuantity() {
		return availableQuantity;
	}
	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getLinkManifacturer() {
		return linkManifacturer;
	}
	public void setLinkManifacturer(String linkManifacturer) {
		this.linkManifacturer = linkManifacturer;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	
	@Override
	public String toString() {
		return "Component [code=" + code + ", name=" + name + ", price=" + price + ", availableQuanity="
				+ availableQuantity + ", description=" + description + ", category=" + category + ", linkManifacturer="
				+ linkManifacturer + ", image=" + image + "]";
	}
	
	
}
