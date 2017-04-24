package ar.com.nps.android;

import java.util.ArrayList;

public class PaymentMethodToken {
	public String id;
	public Boolean used;
	public String object;
	public ArrayList<InstallmentOption> installmentOptionList;
	
	public String getId() {
		return id;
	}
	public PaymentMethodToken setId(String id) {
		this.id = id;
		return this;
	}
	public Boolean getUsed() {
		return used;
	}
	public PaymentMethodToken setUsed(boolean b) {
		this.used = b;
		return this;
	}
	public String getObject() {
		return object;
	}
	public PaymentMethodToken setObject(String object) {
		this.object = object;
		return this;
	}
	public ArrayList<InstallmentOption> getInstallmentOptions() {
		return installmentOptionList;
	}
	public void addInstallmentOptions(InstallmentOption installmentOption) {
		this.installmentOptionList.add(installmentOption);
	}
	public PaymentMethodToken setInstallmentOptions(ArrayList<InstallmentOption> installmentOptions) {
		this.installmentOptionList = installmentOptions;
		return this;
	}
}
