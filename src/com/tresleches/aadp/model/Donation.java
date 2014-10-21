/**
 * 
 */
package com.tresleches.aadp.model;

import java.util.Date;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * A model class for Donations
 * @author Parag Sagar
 *
 */
@ParseClassName("Donation")
public class Donation extends ParseObject {
	
	//Enumeration for Column definition 
	public enum Col{
		name, email, amount,createdAt
	}
	
	public Double getAmount() {
		return getDouble(Col.name.toString());
	}
	public void setAmount(Double amount) {
		put(Col.amount.toString(),amount);
	}
	public Date getDate() {
		return getDate(Col.createdAt.toString());
	}

	public String getName() {
		return getString(Col.name.toString());
	}
	public void setName(String name) {
		put(Col.name.toString(),name);
	}
	public String getEmail() {
		return getString(Col.email.toString());
	}
	public void setEmail(String email) {
		put(Col.email.toString(),email);
	}

}
