package entities;

public class Category {
	private int code;
	private String name;
	private String description;
	private Category subcategory;
	
	
	
	public Category(){}
	
	public Category(Category category){
		super();
		this.code = category.code;
		this.name = category.name;
		this.description = category.description;
		this.subcategory = category.subcategory;
	}
	
	public Category(String name, String description, Category subcategory) {
		super();
		this.name = name;
		this.description = description;
		this.subcategory = subcategory;
	}
	
	public Category(int code, String name, String description, Category subcategory) {
		super();
		this.code = code;
		this.name = name;
		this.description = description;
		this.subcategory = subcategory;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Category getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(Category subcategory) {
		this.subcategory = subcategory;
	}

	
	@Override
	public String toString() {
		return "Category [code=" + code + ", name=" + name + ", description=" + description + ", subcategory=" + subcategory
				+ "]";
	}
	
	
	
}
