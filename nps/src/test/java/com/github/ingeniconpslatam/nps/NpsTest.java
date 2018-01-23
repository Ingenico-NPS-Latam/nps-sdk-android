package com.github.ingeniconpslatam.nps;

import android.content.Context;
import android.os.StrictMode;

import com.github.ingeniconpslatam.nps.DemoHelpers;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class NpsTest {

    @Mock
    static Context mMockContext;

    static String psp_MerchantId = "psp_test";
    static String secretCode = "IeShlZMDk8mp8VA6vy41mLnVggnj1yqHcJyNqIYaRINZnXdiTfhF0Ule9WNAUCR6";
    static String psp_ClientSession;
    static String psp_CustomerId;
    static int testsExecutedSoFar = 0;
    static boolean isFirstRun = true;


    protected void init() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }



        psp_ClientSession = DemoHelpers.CreateClientSession(Nps.SANDBOX, psp_MerchantId, secretCode);
        psp_CustomerId = DemoHelpers.CreateCustomer(Nps.SANDBOX, psp_MerchantId, secretCode);
    }

    @Before
    public void setup() {
        //when(mMockContext.getString(R.string.soap_url))
        //        .thenReturn("https://dev.nps.com.ar/ws.php");

        if(isFirstRun){
            init();
            isFirstRun = false;
        }
    }


    @Test
    public void getProduct() throws Exception {
      Nps nps = new Nps(Nps.SANDBOX, psp_ClientSession, psp_MerchantId);

      String product = nps.getProduct("4507990000000010");

      assertTrue(product != "");
    }

    @Test
    public void getInstallmentsOptions() throws Exception {

    }

    @Test
    public void recachePaymentMethodToken() throws Exception {
      Nps nps = new Nps(Nps.SANDBOX, psp_ClientSession, psp_MerchantId);

      Billing billing = new Billing();

      billing.getPerson()
                .setFirstName("John")
                .setLastName("Smith")
                .setDateOfBirth("1987-01-01")
                .setGender("M")
                .setNationality("ARG")
                .setIdType(Billing.IDTYPE_AR_DNI)
                .setIdNumber("32123123")
                .setPhoneNumber1("4123-1234")
                .setPhoneNumber2("4123-5678")
        ;

      billing.getAddress()
                .setAdditionalInfo("adding additional info")
                .setCity("Capital Federal")
                .setStateProvince("Capital Federal")
                .setCountry("ARG")
                .setZipCode("1414")
                .setStreet("Calle Falsa")
                .setHouseNumber("123")
        ;

      Nps.ResponseHandler responseHandler = new Nps.ResponseHandler() {
            @Override
            public void onSuccess(PaymentMethodToken paymentMethodToken) {
                String demoResponse = "Exito! token=" + paymentMethodToken.getId();

                // if (paymentMethodToken.getInstallmentOptions().size() >= 1) {
                        /* InstallmentOption installmentOption = (InstallmentOption) paymentMethodToken.getInstallmentOptions().get(0);
                        demoResponse = demoResponse + "make " + installmentOption.getNumPayments() + " monthly payments of " + installmentOption.getInstallmentAmount(); */
                // }

                assertTrue(paymentMethodToken.getId() != null);
            }

            @Override
            public void onError(Exception e) {
                fail("Unexpected error: " + e.getMessage());
            }
        };

        ;

      PaymentMethod paymentMethod = DemoHelpers.CreatePaymentMethod(Nps.SANDBOX, psp_MerchantId, secretCode);
      paymentMethod.setCardSecurityCode("123");
      paymentMethod.setCardNumPayments("1");

      nps.recachePaymentMethodToken(paymentMethod, billing, responseHandler);
    }

    @Test
    public void createClientSession() throws Exception {
        psp_ClientSession = DemoHelpers.CreateClientSession(Nps.SANDBOX, psp_MerchantId, secretCode);
        assertTrue(psp_ClientSession != "");        
    }
    
    @Test
    public void createPaymentMethodToken() throws Exception {
        Nps nps = new Nps(Nps.SANDBOX, psp_ClientSession, psp_MerchantId);
        /* nps.setAmount("100000");
        nps.setCountry("CHL");
        nps.setCurrency("152"); */

        Card card = new Card()
                .setHolderName("John Smith")
                .setNumber("45079900000000010")
                .setExpirationDate("1912")
                .setSecurityCode("123")
                .setNumPayments("1");

        Billing billing = new Billing();

        billing.getPerson()
                 .setFirstName("John")
                 .setLastName("Smith")
                 .setDateOfBirth("1987-01-01")
                 .setGender("M")
                 .setNationality("ARG")
                 .setIdType(Billing.IDTYPE_AR_DNI)
                 .setIdNumber("32123123")
                 .setPhoneNumber1("4123-1234")
                 .setPhoneNumber2("4123-5678")
               ;

        billing.getAddress()
                  .setAdditionalInfo("adding additional info")
                  .setCity("Capital Federal")
                  .setStateProvince("Capital Federal")
                  .setCountry("ARG")
                  .setZipCode("1414")
                  .setStreet("Calle Falsa")
                  .setHouseNumber("123")
               ;

        Nps.ResponseHandler responseHandler = new Nps.ResponseHandler() {
            @Override
            public void onSuccess(PaymentMethodToken paymentMethodToken) {
                String demoResponse = "Exito! token=" + paymentMethodToken.getId();

                // if (paymentMethodToken.getInstallmentOptions().size() >= 1) {
                        /* InstallmentOption installmentOption = (InstallmentOption) paymentMethodToken.getInstallmentOptions().get(0);
                        demoResponse = demoResponse + "make " + installmentOption.getNumPayments() + " monthly payments of " + installmentOption.getInstallmentAmount(); */
                // }

                assertTrue(paymentMethodToken.getId() != null);
            }

            @Override
            public void onError(Exception e) {
                fail("Unexpected error: " + e.getMessage());
            }
        };

        nps.createPaymentMethodToken(card, billing, responseHandler);

    }
    /*
    @Test
    public void getDeviceFingerprint() throws Exception {
        assertTrue(Nps.getDeviceFingerprint(mMockContext) != null);
    }*/

    @Test
    public void getIINDetails() throws Exception {
        Nps nps = new Nps(Nps.SANDBOX, psp_ClientSession, psp_MerchantId);
        Card card = nps.getIINDetails("450799");
        assertTrue(card.getProduct() != null);
    }

}