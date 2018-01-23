package com.github.ingeniconpslatam.nps;

public class InstallmentOption {
	public String numPayments;
	public String installmentAmount;

	public String getNumPayments() {
		return numPayments;
	}
	public InstallmentOption setNumPayments(String numPayments) {
		this.numPayments = numPayments;
		return this;
	}
	
	public String getInstallmentAmount() {
		return installmentAmount;
	}
	public InstallmentOption setInstallmentAmount(String installmentAmount) {
		this.installmentAmount = installmentAmount;
		return this;
	}		
	
}
