package ar.com.nps.demo;


import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ar.com.nps.android.Card;
import ar.com.nps.android.Nps;
import ar.com.nps.android.PaymentMethodToken;


public class MainActivity extends Activity {
    private TextView txtResultado;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        final TextView cardAnswer = (TextView)findViewById(R.id.card_answer);


        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Nps nps = new Nps(Nps.SANDBOX, psp_ClientSession, psp_MerchantId);


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

                        cardAnswer.setText(demoResponse);
                    }

                    @Override
                    public void onError(Exception error) {
                        Log.d("Nps", "dentro del onError");
                        cardAnswer.setText(error.getMessage());
                    }
                };



                    Card card = new Card()
                            .setHolderName(inputCardHolderName.getText().toString())
                            .setNumber(inputCardNumber.getText().toString())
                            .setExpirationDate(inputCardExpirationDate.getText().toString())
                            .setSecurityCode(inputCardSecurityCode.getText().toString())
                            ;
                    nps.createPaymentMethodToken(card, null, responseHandler);


            }
        });
    }


}
