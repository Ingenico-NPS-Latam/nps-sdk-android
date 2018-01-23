package com.github.ingeniconpslatam.nps;

import org.ksoap2.serialization.SoapObject;

public class Card extends SoapObject {
	private String numPayments;
	private String product;

	public Card setHolderName(String holderName) {
		this.addProperty("HolderName",holderName);
		return this;
	}

	public Card setNumber(String number) {
		this.addProperty("Number",number);
		return this;
	}
	
	public String getNumber() {
		return this.getPropertyAsString("Number");
	}	

	public Card setExpirationDate(String expirationDate) {
		this.addProperty("ExpirationDate",expirationDate);
		return this;
	}

	public Card setSecurityCode(String securityCode) {
		this.addProperty("SecurityCode",securityCode);
		return this;
	}

	public String getSecurityCode() {
		return this.getPropertyAsString("SecurityCode");
	}

	public String getNumPayments() {
		return numPayments;
	}

	public Card setNumPayments(String numPayments) {
		this.numPayments = numPayments;
		return this;
	}

	public String getProduct() {
		return product;
	}

	public Card setProduct(String product) {
		this.product = product;
		return this;
	}



}
