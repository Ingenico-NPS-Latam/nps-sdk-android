package ar.com.nps.demo;


import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import ar.com.nps.android.Card;
import ar.com.nps.android.InstallmentOption;
import ar.com.nps.android.Nps;
import ar.com.nps.android.PaymentMethod;
import ar.com.nps.android.PaymentMethodToken;


public class WithInstallmentsActivity extends Activity {
    private TextView txtResultado;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withinstallments);

        //Codigo de nuestro programa

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        }

        /**
         * Configure Your Client Session
         *
         * You should configure your client session to be able to start to use NPS.js to identify your site while communicating with NPS. 
         * To do this, use the call setClientSession. 
         * Remember to replace the client session call on sandbox with the production domain when you are ready to create real charges.
        */
        final String psp_ClientSession = "__YOUR_CLIENT_SESSION__";
        
        final String psp_MerchantId = "__YOUR_MERCHANT_ID__";
        final String secretCode = "__YOUR_SECRET_CODE__";
        final String psp_CustomerId = "__YOUR_CUSTOMER_ID__";

        final EditText inputCardNumber = (EditText)findViewById(R.id.card_number);
        final EditText inputCardHolderName = (EditText)findViewById(R.id.card_holder_name);
        final EditText inputCardExpirationDate = (EditText)findViewById(R.id.card_expiration_date);
        final EditText inputCardSecurityCode = (EditText)findViewById(R.id.card_security_code);
        Button btn = (Button)findViewById(R.id.send);
        final TextView cardInstallmentsAnswer = (TextView)findViewById(R.id.card_installments_answer);

        final Spinner inputCardInstallments = (Spinner)findViewById(R.id.card_installments);
        String[] inputCardInstallmentsItems = new String[]{"1", "2", "3", "4" ,"5" ,"6", "99"};
        ArrayAdapter<String> inputCardInstallmentsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, inputCardInstallmentsItems);
        inputCardInstallments.setAdapter(inputCardInstallmentsAdapter);


        final ArrayList<String> arrayPaymentMethods = new ArrayList<String>();
        ArrayList<PaymentMethod> paymentMethods = new ArrayList<PaymentMethod>();
        final Spinner inputPaymentMethods = (Spinner)findViewById(R.id.payment_methods);
        String[] inputPaymentMethodsItems = new String[paymentMethods.size()+1];
        inputPaymentMethodsItems[0] = "New Card";
        for (int i = 1; i < inputPaymentMethodsItems.length; i++) {
            arrayPaymentMethods.add(paymentMethods.get(i-1).getId());
            inputPaymentMethodsItems[i] = paymentMethods.get(i-1).getMaskedNumberAlt();
        }
        ArrayAdapter<String> inputPaymentMethodsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, inputPaymentMethodsItems);
        inputPaymentMethods.setAdapter(inputPaymentMethodsAdapter);




        inputPaymentMethods.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(inputPaymentMethods.getSelectedItemPosition() > 0) {
                    inputCardHolderName.setVisibility(View.GONE);
                    inputCardNumber.setVisibility(View.GONE);
                    inputCardExpirationDate.setVisibility(View.GONE);
                }else {
                    inputCardHolderName.setVisibility(View.VISIBLE);
                    inputCardNumber.setVisibility(View.VISIBLE);
                    inputCardExpirationDate.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Nps nps = new Nps(getApplicationContext(), psp_ClientSession, psp_MerchantId);
                nps.setAmount("100000");
                nps.setCountry("CHL");
                nps.setCurrency("152");


                Log.d("validateNumber",String.valueOf(nps.validateCardNumber(inputCardNumber.getText().toString())));
                Log.d("validateSecurityCode",String.valueOf(nps.validateCardSecurityCode(inputCardSecurityCode.getText().toString())));
                Log.d("validateExpDate",String.valueOf(nps.validateCardExpDate(inputCardExpirationDate.getText().toString())));
                Log.d("validateCardHolderName",String.valueOf(nps.validateCardHolderName(inputCardHolderName.getText().toString())));



                Nps.ResponseHandler responseHandler = new Nps.ResponseHandler() {
                    @Override
                    public void onSuccess(PaymentMethodToken paymentMethodToken) {
                        Log.d("Nps", "dentro del onSuccess");
                        Log.d("Nps", "tengo el token = " + paymentMethodToken.getId());

                        String demoResponse = "Exito! token=" + paymentMethodToken.getId();

                        if (paymentMethodToken.getInstallmentOptions().size() >= 1) {
                            InstallmentOption installmentOption = (InstallmentOption) paymentMethodToken.getInstallmentOptions().get(0);
                            demoResponse = demoResponse + "make " + installmentOption.getNumPayments() + " monthly payments of " + installmentOption.getInstallmentAmount();
                        }

                        cardInstallmentsAnswer.setText(demoResponse);
                    }

                    @Override
                    public void onError(Exception error) {
                        Log.d("Nps", "dentro del onError");
                        cardInstallmentsAnswer.setText(error.getMessage());
                    }
                };



                if(inputPaymentMethods.getSelectedItemPosition() > 0) {
                    PaymentMethod paymentMethod = new PaymentMethod()
                            .setCardSecurityCode(inputCardSecurityCode.getText().toString())
                            .setCardNumPayments(inputCardInstallments.getSelectedItem().toString())
                            .setId(arrayPaymentMethods.get(inputPaymentMethods.getSelectedItemPosition()-1))
                            ;
                    nps.recachePaymentMethodToken(paymentMethod, null, responseHandler);
                }else {
                    Card card = new Card()
                            .setHolderName(inputCardHolderName.getText().toString())
                            .setNumber(inputCardNumber.getText().toString())
                            .setExpirationDate(inputCardExpirationDate.getText().toString())
                            .setSecurityCode(inputCardSecurityCode.getText().toString())
                            .setNumPayments(inputCardInstallments.getSelectedItem().toString())
                            ;
                    nps.createPaymentMethodToken(card, null, responseHandler);
                }

            }
        });
    }


}
