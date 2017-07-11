package entities;

import java.util.ArrayList;

public class CustomMachine {
	private int code;
	private String name;
	private String description;
	private ArrayList<Component> components;
	
	
	public CustomMachine(){};
	
	public CustomMachine(CustomMachine machine){
		super();
		this.code = machine.code;
		this.name = machine.name;
		this.description = machine.description;
		this.components = machine.components;
	}
	
	
	
	public CustomMachine(String name, String description, ArrayList<Component> components) {
		super();
		this.name = name;
		this.description = description;
		this.components = components;
	}
	
	// machine without components
	public CustomMachine(int code, String name, String description) {
		super();
		this.code = code;
		this.name = name;
		this.description = description;
	}
	
	public CustomMachine(int code, String name, String description, ArrayList<Component> components) {
		super();
		this.code = code;
		this.name = name;
		this.description = description;
		this.components = components;
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
	public ArrayList<Component> getComponents() {
		return components;
	}
	public void setComponents(ArrayList<Component> components) {
		this.components = components;
	}

	
	@Override
	public String toString() {
		return "CustomBuiltMachine [code=" + code + ", name=" + name + ", description=" + description + ", components="
				+ components + "]";
	}
	
	
	
}
