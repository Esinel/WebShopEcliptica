package entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class Bill {
	private ArrayList<Object> components;
	private long tax;
	private BigDecimal totalPrice;
	private Date dateTime;
	private User buyer;
	
	
	public Bill(){}
	
	public Bill(ArrayList<Object> components, long tax, BigDecimal totalPrice, Date dateTime, User buyer) {
		super();
		this.components = components;
		this.tax = tax;
		this.totalPrice = totalPrice;
		this.dateTime = dateTime;
		this.buyer = buyer;
	}


	public ArrayList<Object> getComponents() {
		return components;
	}
	public void setComponents(ArrayList<Object> components) {
		this.components = components;
	}
	public long getTax() {
		return tax;
	}
	public void setTax(long tax) {
		this.tax = tax;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public User getBuyer() {
		return buyer;
	}
	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}
	
	
	@Override
	public String toString() {
		return "Bill [components=" + components + ", tax=" + tax + ", totalPrice=" + totalPrice + ", dateTime="
				+ dateTime + ", buyer=" + buyer + "]";
	}
	
	
}
