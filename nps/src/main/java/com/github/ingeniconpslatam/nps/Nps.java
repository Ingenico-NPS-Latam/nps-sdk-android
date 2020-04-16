package com.github.ingeniconpslatam.nps;


import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import com.iovation.mobile.android.FraudForceManager;
import com.iovation.mobile.android.FraudForceConfiguration;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;


public class Nps {
	static public String PSP_VERSION = "2.2";
	static public String SANDBOX = "https://sandbox.nps.com.ar/ws.php?wsdl";
	static public String PRODUCTION = "https://services2.nps.com.ar/ws.php?wsdl";
	static public String STAGING = "https://implementacion.nps.com.ar/ws.php?wsdl";
	static public String DEV = "https://dev.nps.com.ar/ws.php?wsdl";

	private String clientSession = "";
	private String merchantId = "";
	private String amount = "";
	private String currency = "";
	private String country = "";
	private String environment = "";
	private FraudForceManager x;
	private FraudForceConfiguration ffConf;

	public Nps(String environment, String clientSession, String merchantId) {
		this.setEnvironment(environment);
		this.setClientSession(clientSession);
		this.setMerchantId(merchantId);
		if (android.os.Build.VERSION.SDK_INT > 9)
		{
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
	}
        
        

	static public String getDeviceFingerprint(Context context) {
		FraudForceConfiguration configuration = new FraudForceConfiguration.Builder()
//				.subscriberKey([YOUR-SUBSCRIBER-KEY-HERE])
//    			.enableNetworkCalls(true) // Defaults to false if left out of configuration
				.build();
		FraudForceManager fraudForceManager = FraudForceManager.getInstance();
		fraudForceManager.initialize(configuration, context);
		return FraudForceManager.getInstance().getBlackbox(context);
	}


    public String getNamespace() {
        return this.getEnvironment();
    }

	static public String getPosDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = sdf.format(new Date());
		return date;
	}

	public String getClientSession() {
		return clientSession;
	}

	public void setClientSession(String clientSession) {
		this.clientSession = clientSession;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}
        
	public String getEnvironment() {
		return environment;
	}        
	
	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}	

	public Card getIINDetails(String number) {
		SoapObject Requerimiento = new SoapObject(this.getNamespace(), "GetIINDetails");
		Requerimiento.addProperty("psp_Version",PSP_VERSION);
		Requerimiento.addProperty("psp_MerchantId",this.getMerchantId());
		Requerimiento.addProperty("psp_IIN",number);
		Requerimiento.addProperty("psp_PosDateTime", Nps.getPosDateTime());
		Requerimiento.addProperty("psp_ClientSession",this.getClientSession());


		SoapObject request = new SoapObject(this.getNamespace(), "GetIINDetails");
		request.addProperty("Requerimiento",Requerimiento);

		SoapObject response = send(this.getEnvironment(), "GetIINDetails", request);
		String psp_ResponseCod = response.getPropertyAsString("psp_ResponseCod");

		Card card = new Card();
		if(Integer.parseInt(psp_ResponseCod) == 2) {
			card.setProduct(response.getPropertyAsString("psp_Product"));
		}
		return card;
	}
	
	public String getProduct(String number) {
		SoapObject Requerimiento = new SoapObject(this.getNamespace(), "GetIINDetails");
		Requerimiento.addProperty("psp_Version",PSP_VERSION);
		Requerimiento.addProperty("psp_MerchantId",this.getMerchantId());
		Requerimiento.addProperty("psp_IIN",number);
		Requerimiento.addProperty("psp_PosDateTime", Nps.getPosDateTime());
		Requerimiento.addProperty("psp_ClientSession",this.getClientSession());
		
		
        SoapObject request = new SoapObject(this.getNamespace(), "GetIINDetails");
      	request.addProperty("Requerimiento",Requerimiento);		
      	
      	SoapObject response = send(this.getEnvironment(), "GetIINDetails", request);
        String psp_ResponseCod = response.getPropertyAsString("psp_ResponseCod");

        String psp_Product = "";
        if(Integer.parseInt(psp_ResponseCod) == 2) {
			psp_Product = response.getPropertyAsString("psp_Product");
		}
      	return psp_Product;
	}
	
	public ArrayList<InstallmentOption> getInstallmentsOptions(String psp_PaymentMethodToken, String psp_Product, String psp_NumPayments) {
		SoapObject Requerimiento = new SoapObject(this.getNamespace(), "GetInstallmentsOptions");
		Requerimiento.addProperty("psp_Version",PSP_VERSION);
		Requerimiento.addProperty("psp_MerchantId",this.getMerchantId());
		Requerimiento.addProperty("psp_Amount",this.getAmount());
		Requerimiento.addProperty("psp_Product",psp_Product);
		Requerimiento.addProperty("psp_Currency",this.getCurrency());
		Requerimiento.addProperty("psp_Country",this.getCountry());
		Requerimiento.addProperty("psp_PaymentMethodToken",psp_PaymentMethodToken);
		Requerimiento.addProperty("psp_ClientSession",this.getClientSession());
		Requerimiento.addProperty("psp_PosDateTime", Nps.getPosDateTime());
		Requerimiento.addProperty("psp_NumPayments",psp_NumPayments);
		
        SoapObject request = new SoapObject(this.getNamespace(), "GetInstallmentsOptions");
      	request.addProperty("Requerimiento",Requerimiento);		
      	
      	SoapObject response = send(this.getEnvironment(), "GetInstallmentsOptions", request);
      	String psp_ResponseCod = response.getPropertyAsString("psp_ResponseCod");
      	
      	if(Integer.parseInt(psp_ResponseCod) == 2) {
      		Log.d("Nps","Installments Options request: OK");
      		
      		Vector<SoapObject> psp_InstallmentsOptions = (Vector<SoapObject>) response.getProperty("psp_InstallmentsOptions");
      		
      		ArrayList<InstallmentOption> installmentOptionList = new ArrayList<InstallmentOption>();
      		for (SoapObject soapObject : psp_InstallmentsOptions) {
      			InstallmentOption installmentOption = new InstallmentOption();
      			installmentOption.setNumPayments(soapObject.getPropertyAsString("NumPayments"));
      			installmentOption.setInstallmentAmount(soapObject.getPropertyAsString("InstallmentAmount"));
      			installmentOptionList.add(installmentOption);      			      			
      		}
      		return installmentOptionList;
      	}else {
            Log.d("Nps","Unable to retrieve installments [" + response.getPropertyAsString("psp_ResponseMsg") + "]");
        }

      	return new ArrayList<InstallmentOption>();
	}

	public void recachePaymentMethodToken(PaymentMethod paymentMethod, Billing billing, ResponseHandler responseHandler) {
		SoapObject Requerimiento = new SoapObject(this.getNamespace(), "RecachePaymentMethodToken");
		Requerimiento.addProperty("psp_Version", PSP_VERSION);
		Requerimiento.addProperty("psp_MerchantId", this.getMerchantId());
		Requerimiento.addProperty("psp_PaymentMethodId", paymentMethod.getId());
		Requerimiento.addProperty("psp_CardSecurityCode", paymentMethod.getCardSecurityCode());
		Requerimiento.addProperty("psp_ClientSession", this.getClientSession());

        if(billing != null && billing.getPerson().getFirstName().length() > 0) {
            Requerimiento.addProperty("psp_Person", billing.getPerson());
        }

        if(billing != null && (billing.getAddress().getStreet().length() > 0
                && billing.getAddress().getHouseNumber().length() > 0
                && billing.getAddress().getCity().length() > 0
                && billing.getAddress().getCountry().length() > 0)
                ) {
            Requerimiento.addProperty("psp_Address", billing.getAddress());
        }

		SoapObject request = new SoapObject(this.getNamespace(), "RecachePaymentMethodToken");
		request.addProperty("Requerimiento", Requerimiento);

		SoapObject response = send(this.getEnvironment(), "RecachePaymentMethodToken", request);
		String psp_ResponseCod = response.getPropertyAsString("psp_ResponseCod");

		if(Integer.parseInt(psp_ResponseCod) == 2) {
			String psp_PaymentMethodToken = response.getPropertyAsString("psp_PaymentMethodToken");
            paymentMethod.setProduct(response.getPropertyAsString("psp_Product"));
			PaymentMethodToken paymentMethodToken = new PaymentMethodToken();
			paymentMethodToken.hydrate(response);


			if(this.getCountry() == "CHL") {
				ArrayList<InstallmentOption> installmentOptionsList = this.getInstallmentsOptions(psp_PaymentMethodToken, paymentMethod.getProduct(), paymentMethod.getCardNumPayments());
				paymentMethodToken.setInstallmentOptions(installmentOptionsList);
			}

			responseHandler.onSuccess(paymentMethodToken);
		}else {
			responseHandler.onError(new Exception(response.getPropertyAsString("psp_ResponseCod") + " " + response.getPropertyAsString("psp_ResponseMsg")));
		}
	}

	
	public void createPaymentMethodToken(Card card, Billing billing, ResponseHandler responseHandler) {
        Log.d("nps","entre a createPaymentMethodToken");
		SoapObject Requerimiento = new SoapObject(this.getNamespace(), "CreatePaymentMethodToken");
		Requerimiento.addProperty("psp_Version",PSP_VERSION);
		Requerimiento.addProperty("psp_MerchantId",this.getMerchantId());
		Requerimiento.addProperty("psp_CardInputDetails",card);
		Requerimiento.addProperty("psp_ClientSession",this.getClientSession());

        if(billing != null && billing.getPerson().getFirstName().length() > 0) {
            Requerimiento.addProperty("psp_Person", billing.getPerson());
        }

        if(billing != null && (billing.getAddress().getStreet().length() > 0
                && billing.getAddress().getHouseNumber().length() > 0
                && billing.getAddress().getCity().length() > 0
                && billing.getAddress().getCountry().length() > 0)
            ) {
            Requerimiento.addProperty("psp_Address", billing.getAddress());
        }


        SoapObject request = new SoapObject(this.getNamespace(), "CreatePaymentMethodToken");
      	request.addProperty("Requerimiento",Requerimiento);		
      	
      	SoapObject response = send(this.getEnvironment(), "CreatePaymentMethodToken", request);
      	String psp_ResponseCod = response.getPropertyAsString("psp_ResponseCod");
      	if(Integer.parseInt(psp_ResponseCod) == 2) {
      		String psp_PaymentMethodToken = response.getPropertyAsString("psp_PaymentMethodToken");
            card.setProduct(response.getPropertyAsString("psp_Product"));
      		PaymentMethodToken paymentMethodToken = new PaymentMethodToken();
      		paymentMethodToken.hydrate(response);

      		if(this.getCountry() == "CHL") {
	      		ArrayList<InstallmentOption> installmentOptionsList = this.getInstallmentsOptions(psp_PaymentMethodToken, card.getProduct(), card.getNumPayments());
	      		paymentMethodToken.setInstallmentOptions(installmentOptionsList);
      		}

      		responseHandler.onSuccess(paymentMethodToken);
      	}else {
      		responseHandler.onError(new Exception(response.getPropertyAsString("psp_ResponseCod") + " " + response.getPropertyAsString("psp_ResponseMsg")));
      	}
      	
      	
	}

	public void retrievePaymentMethodToken(String paymentMethodToken, ResponseHandler responseHandler) {
		Log.d("nps","entre a createPaymentMethodToken");
		SoapObject Requerimiento = new SoapObject(this.getNamespace(), "CreatePaymentMethodToken");
		Requerimiento.addProperty("psp_Version",PSP_VERSION);
		Requerimiento.addProperty("psp_MerchantId",this.getMerchantId());
		Requerimiento.addProperty("psp_PaymentMethodToken",paymentMethodToken);
		Requerimiento.addProperty("psp_ClientSession",this.getClientSession());

		SoapObject request = new SoapObject(this.getNamespace(), "RetrievePaymentMethodToken");
		request.addProperty("Requerimiento",Requerimiento);

		SoapObject response = send(this.getEnvironment(), "RetrievePaymentMethodToken", request);
		String psp_ResponseCod = response.getPropertyAsString("psp_ResponseCod");
		if(Integer.parseInt(psp_ResponseCod) == 2) {
			String psp_PaymentMethodToken = response.getPropertyAsString("psp_PaymentMethodToken");

			PaymentMethodToken pmt = new PaymentMethodToken();
//  			  .setId(psp_PaymentMethodToken)
//  			  .setUsed(false)
//  			  .setObject("paymentMethodToken");

			pmt.hydrate(response);

			responseHandler.onSuccess(pmt);
		}else {
			responseHandler.onError(new Exception(response.getPropertyAsString("psp_ResponseCod") + " " + response.getPropertyAsString("psp_ResponseMsg")));
		}


	}
	
	static public SoapObject send(String environment, String method, SoapObject request) {

	    SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
	    envelope.dotNet = false;
	    envelope.implicitTypes = false;
	    envelope.setAddAdornments(false);
	    envelope.setOutputSoapObject(request);

	    FakeX509TrustManager.allowAllSSL();

        Log.d("nps.send",environment);

	    HttpTransportSE ht = new HttpTransportSE(environment,30000);
	    ht.debug = true;
	    ht.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	      	
	    try {
	    	Log.d("Nps","Starting request");

//            Log.d("Nps Ws Request",request.toString());

	        ht.call(environment + "/" + method, envelope);

	        Log.d("Nps","Call Finished");


	        if (envelope.bodyIn instanceof SoapObject) { // SoapObject =
                Log.d("Nps Ws Response",envelope.getResponse().toString());
				SoapObject response = (SoapObject) envelope.getResponse();
				return response;
		    }
	        
        } catch (SocketTimeoutException t) {
        	Log.d("Nps",t.getMessage());
            t.printStackTrace();
        } catch (IOException i) {
        	Log.d("Nps",i.getMessage());
            i.printStackTrace();
        } catch (Exception q) {
        	Log.d("Nps",q.getMessage());
            q.printStackTrace();
        }            	    
	    		
	    return new SoapObject();
	}



    public static boolean isValidLuhn(String number) {
        Integer sum = 0;
        char[] _ref = number.toCharArray();
        ArrayList<String> _refReversed = new ArrayList<String>();
        for (int i = _ref.length - 1; i >= 0; i--) {
            _refReversed.add((String.valueOf(_ref[i])));
        }

        int n; int _i; int _len;
        for (n = _i = 0, _len = _refReversed.size(); _i < _len; n = ++_i) {
            Integer digit = Integer.parseInt(_refReversed.get(n));
            digit = digit + digit;
            if ((n % 2) != 0) {
                digit = digit * 2;
                if (digit < 10) {
                    sum += digit;
                } else {
                    sum += digit - 9;
                }
            } else {
                sum += digit;
            }
        }
        return (sum % 10) == 0;
    }

    public static boolean isValidLength(String number) {
        String[] numberSplited = number.split("");
        return numberSplited.length > 9 && numberSplited.length < 24;
    }

    public boolean validateCardNumber(String number) {
        number = number.replaceAll("/[ -]/g", "");
        boolean luhn_valid = false;
        boolean length_valid = false;
        boolean iin_valid = false;

        luhn_valid = Nps.isValidLuhn(number);
        length_valid = Nps.isValidLength(number);
        if(luhn_valid && length_valid) {
            String psp_Product = this.getProduct(number);
            iin_valid = psp_Product != null && psp_Product != "";
            iin_valid = true;
        }
        return luhn_valid && length_valid && iin_valid;
    }

    public static boolean validateCardHolderName(String name) {
        return name.length() >= 2 && name.length() <= 26;
    }

    public static boolean validateCardSecurityCode(String cvc) {
        return (Integer.parseInt(cvc) >= 0 && Integer.parseInt(cvc) < 10000) || (cvc.matches("/^[\\d]{3,4}$/") != true);
    }

    public static boolean validateCardExpDate(String date) {
        String year = date.substring(0,date.length()-2);
        String month = date.substring(year.length());
        return date.length() <= 6 && Nps.validateCardExpMonthYear(month, year);
    }

    public static boolean validateCardExpMonthYear(String month, String year) {
        Integer monthParsed = Nps.parseMonth(month);
        Integer yearParsed = Nps.parseYear(year);
        if ((monthParsed > 0 && monthParsed < 13) && (yearParsed > 2013 && yearParsed < 2035)) {
            Date today = new Date();
            Date expDate = new Date();
            expDate.setYear(yearParsed);
            expDate.setMonth(monthParsed);
            return today.compareTo(expDate)<0;
        } else {
            return false;
        }
    }

    public static int parseMonth(String month){
        return Integer.parseInt(month);
    }

    public static int parseYear(String year) {
        if (Integer.parseInt(year) < 100) {
            year = "20" + year;
        }
        if (year.matches("/^([\\d]{2,2}|20[\\d]{2,2})$/")) {
            if (year.matches("/^([\\d]{2,2})$/")) {
                year = "20" + year;
            }
            return Integer.parseInt(year);
        } else {
            return Integer.parseInt(year);
        }
    }




	static public class ResponseHandler {

		public void onSuccess(PaymentMethodToken paymentMethodToken) {
			// TODO Auto-generated method stub
		}

		public void onError(Exception error) {
			// TODO Auto-generated method stub
		}
		
	}	




	
}
