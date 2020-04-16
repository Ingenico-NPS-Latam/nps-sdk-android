package com.github.ingeniconpslatam.nps;

import android.content.Context;
import android.os.StrictMode;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;



@RunWith(MockitoJUnitRunner.class)
public class NpsTest {

    @Mock
    static Context mMockContext;

    private static String psp_MerchantId = "psp_test";
    private static String secretCode = "IeShlZMDk8mp8VA6vy41mLnVggnj1yqHcJyNqIYaRINZnXdiTfhF0Ule9WNAUCR6";
    private static String psp_ClientSession;
    private static String psp_CustomerId;
    private static boolean isFirstRun = true;


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

        assertNotSame("", product);
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
        assertNotSame("", psp_ClientSession);
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

                assertEquals("2", paymentMethodToken.getResponseCod());
                assertEquals("psp_test".trim(), paymentMethodToken.getMerchantId().trim());
                assertFalse("".equals(paymentMethodToken.getId()));
                assertNotNull(paymentMethodToken.getId());
                assertFalse("".equals(paymentMethodToken.getProduct()));
                assertNotNull(paymentMethodToken.getProduct());

                assertNotNull(paymentMethodToken.getCardOutputDetails());

                assertEquals("1912", paymentMethodToken.getCardOutputDetails().getExpirationDate());
                assertEquals("2019", paymentMethodToken.getCardOutputDetails().getExpirationYear());
                assertEquals("12", paymentMethodToken.getCardOutputDetails().getExpirationMonth());
                assertEquals("John Smith", paymentMethodToken.getCardOutputDetails().getHolderName());
                assertEquals("450799", paymentMethodToken.getCardOutputDetails().getIin());
                assertEquals("0010", paymentMethodToken.getCardOutputDetails().getLast4());
                assertEquals("17", paymentMethodToken.getCardOutputDetails().getNumberLength());
                assertEquals("450799*******0010", paymentMethodToken.getCardOutputDetails().getMaskedNumber());
                assertEquals("*************0010", paymentMethodToken.getCardOutputDetails().getMaskedNumberAlternative());

                assertNotNull(paymentMethodToken.getPerson());
                assertEquals("John", paymentMethodToken.getPerson().getFirstName());
                assertEquals("Smith", paymentMethodToken.getPerson().getLastName());
                assertEquals("1987-01-01", paymentMethodToken.getPerson().getDateOfBirth());
                assertEquals("M", paymentMethodToken.getPerson().getGender());
                assertEquals("ARG", paymentMethodToken.getPerson().getNationality());
                assertEquals("200", paymentMethodToken.getPerson().getIdType());
                assertEquals("32123123", paymentMethodToken.getPerson().getIdNumber());
                assertEquals("4123-1234", paymentMethodToken.getPerson().getPhoneNumber1());
                assertEquals("4123-5678", paymentMethodToken.getPerson().getPhoneNumber2());

                assertNotNull(paymentMethodToken.getAddress());

                assertEquals("adding additional info", paymentMethodToken.getAddress().getAdditionalInfo());
                assertEquals("Capital Federal", paymentMethodToken.getAddress().getCity());
                assertEquals("Capital Federal", paymentMethodToken.getAddress().getStateProvince());
                assertEquals("ARG", paymentMethodToken.getAddress().getCountry());
                assertEquals("1414", paymentMethodToken.getAddress().getZipCode());
                assertEquals("Calle Falsa", paymentMethodToken.getAddress().getStreet());
                assertEquals("123", paymentMethodToken.getAddress().getHouseNumber());

                assertEquals("0", paymentMethodToken.getAlreadyUsed());

                assertFalse("".equals(paymentMethodToken.getCreatedAt()));
                assertNotNull(paymentMethodToken.getCreatedAt());

                assertFalse("".equals(paymentMethodToken.getUpdatedAt()));
                assertNotNull(paymentMethodToken.getUpdatedAt());

            }

            @Override
            public void onError(Exception e) {
                fail("Unexpected error: " + e.getMessage());
            }
        };

        nps.createPaymentMethodToken(card, billing, responseHandler);

    }


    @Test
    public void testRetrievePaymentMethodToken() throws Exception {
        final Nps nps = new Nps(Nps.SANDBOX, psp_ClientSession, psp_MerchantId);
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
                assertEquals("2", paymentMethodToken.getResponseCod());
                assertEquals("psp_test".trim(), paymentMethodToken.getMerchantId().trim());
                assertFalse("".equals(paymentMethodToken.getId()));
                assertNotNull(paymentMethodToken.getId());
                assertFalse("".equals(paymentMethodToken.getProduct()));
                assertNotNull(paymentMethodToken.getProduct());

                assertNotNull(paymentMethodToken.getCardOutputDetails());

                assertEquals("1912", paymentMethodToken.getCardOutputDetails().getExpirationDate());
                assertEquals("2019", paymentMethodToken.getCardOutputDetails().getExpirationYear());
                assertEquals("12", paymentMethodToken.getCardOutputDetails().getExpirationMonth());
                assertEquals("John Smith", paymentMethodToken.getCardOutputDetails().getHolderName());
                assertEquals("450799", paymentMethodToken.getCardOutputDetails().getIin());
                assertEquals("0010", paymentMethodToken.getCardOutputDetails().getLast4());
                assertEquals("17", paymentMethodToken.getCardOutputDetails().getNumberLength());
                assertEquals("450799*******0010", paymentMethodToken.getCardOutputDetails().getMaskedNumber());
                assertEquals("*************0010", paymentMethodToken.getCardOutputDetails().getMaskedNumberAlternative());

                assertNotNull(paymentMethodToken.getPerson());
                assertEquals("John", paymentMethodToken.getPerson().getFirstName());
                assertEquals("Smith", paymentMethodToken.getPerson().getLastName());
                assertEquals("1987-01-01", paymentMethodToken.getPerson().getDateOfBirth());
                assertEquals("M", paymentMethodToken.getPerson().getGender());
                assertEquals("ARG", paymentMethodToken.getPerson().getNationality());
                assertEquals("200", paymentMethodToken.getPerson().getIdType());
                assertEquals("32123123", paymentMethodToken.getPerson().getIdNumber());
                assertEquals("4123-1234", paymentMethodToken.getPerson().getPhoneNumber1());
                assertEquals("4123-5678", paymentMethodToken.getPerson().getPhoneNumber2());

                assertNotNull(paymentMethodToken.getAddress());

                assertEquals("adding additional info", paymentMethodToken.getAddress().getAdditionalInfo());
                assertEquals("Capital Federal", paymentMethodToken.getAddress().getCity());
                assertEquals("Capital Federal", paymentMethodToken.getAddress().getStateProvince());
                assertEquals("ARG", paymentMethodToken.getAddress().getCountry());
                assertEquals("1414", paymentMethodToken.getAddress().getZipCode());
                assertEquals("Calle Falsa", paymentMethodToken.getAddress().getStreet());
                assertEquals("123", paymentMethodToken.getAddress().getHouseNumber());

                assertEquals("0", paymentMethodToken.getAlreadyUsed());

                assertFalse("".equals(paymentMethodToken.getCreatedAt()));
                assertNotNull(paymentMethodToken.getCreatedAt());

                assertFalse("".equals(paymentMethodToken.getUpdatedAt()));
                assertNotNull(paymentMethodToken.getUpdatedAt());


                Nps.ResponseHandler createPaymentMethodTokenHandler = new Nps.ResponseHandler() {
                    public void onSuccess(PaymentMethodToken paymentMethodToken){
                        assertEquals("2", paymentMethodToken.getResponseCod());
                        assertEquals("psp_test".trim(), paymentMethodToken.getMerchantId().trim());
                        assertFalse("".equals(paymentMethodToken.getId()));
                        assertNotNull(paymentMethodToken.getId());
                        assertFalse("".equals(paymentMethodToken.getProduct()));
                        assertNotNull(paymentMethodToken.getProduct());

                        assertNotNull(paymentMethodToken.getCardOutputDetails());

                        assertEquals("1912", paymentMethodToken.getCardOutputDetails().getExpirationDate());
                        assertEquals("2019", paymentMethodToken.getCardOutputDetails().getExpirationYear());
                        assertEquals("12", paymentMethodToken.getCardOutputDetails().getExpirationMonth());
                        assertEquals("John Smith", paymentMethodToken.getCardOutputDetails().getHolderName());
                        assertEquals("450799", paymentMethodToken.getCardOutputDetails().getIin());
                        assertEquals("0010", paymentMethodToken.getCardOutputDetails().getLast4());
                        assertEquals("17", paymentMethodToken.getCardOutputDetails().getNumberLength());
                        assertEquals("450799*******0010", paymentMethodToken.getCardOutputDetails().getMaskedNumber());
                        assertEquals("*************0010", paymentMethodToken.getCardOutputDetails().getMaskedNumberAlternative());

                        assertNotNull(paymentMethodToken.getPerson());
                        assertEquals("John", paymentMethodToken.getPerson().getFirstName());
                        assertEquals("Smith", paymentMethodToken.getPerson().getLastName());
                        assertEquals("1987-01-01", paymentMethodToken.getPerson().getDateOfBirth());
                        assertEquals("M", paymentMethodToken.getPerson().getGender());
                        assertEquals("ARG", paymentMethodToken.getPerson().getNationality());
                        assertEquals("200", paymentMethodToken.getPerson().getIdType());
                        assertEquals("32123123", paymentMethodToken.getPerson().getIdNumber());
                        assertEquals("4123-1234", paymentMethodToken.getPerson().getPhoneNumber1());
                        assertEquals("4123-5678", paymentMethodToken.getPerson().getPhoneNumber2());

                        assertNotNull(paymentMethodToken.getAddress());

                        assertEquals("adding additional info", paymentMethodToken.getAddress().getAdditionalInfo());
                        assertEquals("Capital Federal", paymentMethodToken.getAddress().getCity());
                        assertEquals("Capital Federal", paymentMethodToken.getAddress().getStateProvince());
                        assertEquals("ARG", paymentMethodToken.getAddress().getCountry());
                        assertEquals("1414", paymentMethodToken.getAddress().getZipCode());
                        assertEquals("Calle Falsa", paymentMethodToken.getAddress().getStreet());
                        assertEquals("123", paymentMethodToken.getAddress().getHouseNumber());

                        assertEquals("0", paymentMethodToken.getAlreadyUsed());

                        assertFalse("".equals(paymentMethodToken.getCreatedAt()));
                        assertNotNull(paymentMethodToken.getCreatedAt());

                        assertFalse("".equals(paymentMethodToken.getUpdatedAt()));
                        assertNotNull(paymentMethodToken.getUpdatedAt());
                    }

                    @Override
                    public void onError(Exception e) {
                        fail("Unexpected error: " + e.getMessage());
                    }
                };
                nps.retrievePaymentMethodToken(paymentMethodToken.getId(), createPaymentMethodTokenHandler);

            }

            @Override
            public void onError(Exception e) {
                fail("Unexpected error: " + e.getMessage());
            }
        };
        nps.createPaymentMethodToken(card, billing, responseHandler);


    }

    @Test
    public void getDeviceFingerprint() throws Exception {
//        System.out.println(Nps.getDeviceFingerprint(mMockContext));
//        assertTrue(Nps.getDeviceFingerprint(mMockContext) != null);
    }

    @Test
    public void getIINDetails() throws Exception {
        Nps nps = new Nps(Nps.SANDBOX, psp_ClientSession, psp_MerchantId);
        Card card = nps.getIINDetails("450799");
        assertTrue(card.getProduct() != null);
    }

}