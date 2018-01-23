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

import com.github.ingeniconpslatam.nps.Card;
import com.github.ingeniconpslatam.nps.Nps;
import com.github.ingeniconpslatam.nps.PaymentMethodToken;

import java.util.ArrayList;



public class DevActivity extends Activity {
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

        final String psp_MerchantId = "psp_test";
        final String secretCode = "IeShlZMDk8mp8VA6vy41mLnVggnj1yqHcJyNqIYaRINZnXdiTfhF0Ule9WNAUCR6";
        final String psp_ClientSession = DemoHelpers.CreateClientSession(Nps.SANDBOX, psp_MerchantId, secretCode);
        final String psp_CustomerId = DemoHelpers.CreateCustomer(Nps.SANDBOX, psp_MerchantId, secretCode);

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
