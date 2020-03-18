package com.github.ingeniconpslatam.nps;

import android.provider.Telephony;

import java.util.ArrayList;
import com.github.ingeniconpslatam.nps.structs.CardOutputDetails;
import com.github.ingeniconpslatam.nps.structs.WalletOutputDetails;

import org.ksoap2.serialization.SoapObject;

public class PaymentMethodToken implements Hydratable{

	private String id = "";
	private Boolean used;
	private String object;
	private String responseCod = "";
	private String responseMsg = "";
	private String responseExtended = "";
	private String merchantId = "";
	private String product = "";
	private CardOutputDetails cardOutputDetails = new CardOutputDetails();
	private WalletOutputDetails walletOutputDetails = new WalletOutputDetails();
	private Billing.Person person = new Billing().getPerson();
	private Billing.Address address = new Billing().getAddress();
	private String alreadyUsed;
	private String createdAt = "";
	private String updatedAt = "";

	public String getResponseCod() {
		return responseCod;
	}

	public void setResponseCod(String responseCod) {
		this.responseCod = responseCod;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public String getResponseExtended() {
		return responseExtended;
	}

	public void setResponseExtended(String responseExtended) {
		this.responseExtended = responseExtended;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public CardOutputDetails getCardOutputDetails() {
		return cardOutputDetails;
	}

	public void setCardOutputDetails(CardOutputDetails cardOutputDetails) {
		this.cardOutputDetails = cardOutputDetails;
	}

	public WalletOutputDetails getWalletOutputDetails() {
		return walletOutputDetails;
	}

	public void setWalletOutputDetails(WalletOutputDetails walletOutputDetails) {
		this.walletOutputDetails = walletOutputDetails;
	}

	public Billing.Person getPerson() {
		return person;
	}

	public void setPerson(Billing.Person person) {
		this.person = person;
	}

	public Billing.Address getAddress() {
		if (this.address == null){

		}
		return address;
	}

	public void setAddress(Billing.Address address) {
		this.address = address;
	}

	public String getAlreadyUsed() {
		return alreadyUsed;
	}

	public void setAlreadyUsed(String alreadyUsed) {
		this.alreadyUsed = alreadyUsed;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}


	public ArrayList<InstallmentOption> installmentOptionList;
	
	public String getId() {
		return id;
	}
	public PaymentMethodToken setId(String id) {
		this.id = id;
		return this;
	}
	public Boolean getUsed() {
		if (this.getAlreadyUsed().equals("0")){
			return false;
		} else {
			return true;
		}
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

	@Override
	public void hydrate(SoapObject response){
		this.setMerchantId(response.getPropertySafelyAsString("psp_MerchantId"));
		this.setResponseCod(response.getPropertySafelyAsString("psp_ResponseCod"));
		this.setResponseMsg(response.getPropertySafelyAsString("psp_ResponseMsg"));
		this.setResponseExtended(response.getPropertySafelyAsString("psp_ResponseExtended"));
		this.setId(response.getPropertySafelyAsString("psp_PaymentMethodToken"));
		this.setProduct(response.getPropertySafelyAsString("psp_Product"));
		if (response.hasProperty("psp_CardOutputDetails")) {
			CardOutputDetails cod = new CardOutputDetails();
			cod.hydrate((SoapObject) response.getPropertySafely("psp_CardOutputDetails"));
			this.setCardOutputDetails(cod);
		}
		if (response.hasProperty("psp_WalletOutputDetails")){
			WalletOutputDetails wod = new WalletOutputDetails();
			wod.hydrate((SoapObject) response.getPropertySafely("psp_WalletOutputDetails"));
			this.setWalletOutputDetails(wod);
		}

		if (response.hasProperty("psp_Person")) {
			Billing.Person person = new Billing().getPerson();
			person.hydrate((SoapObject) response.getProperty("psp_Person"));
			this.setPerson(person);
		}
		if (response.hasProperty("psp_Address")) {
			Billing.Address address = new Billing().getAddress();
			address.hydrate((SoapObject) response.getProperty("psp_Address"));
			this.setAddress(address);
		}

		this.setAlreadyUsed(response.getPropertyAsString("psp_AlreadyUsed"));
		this.setCreatedAt(response.getPropertyAsString("psp_CreatedAt"));
		this.setUpdatedAt(response.getPropertyAsString("psp_UpdatedAt"));
	}

}
