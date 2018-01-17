package ar.com.nps.android;

import android.content.Context;
import android.util.Log;

import org.ksoap2.serialization.SoapObject;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Vector;


/**
 * Created by fbonifacio on 3/16/17.
 */

public class DemoHelpers {

    public static String calculateMD5(String originalString) throws Exception {
        String md5Hash = null;

        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            //Para hashear se debe convertir el String a bytes, pero si recibe acentos, cualquier codificación no devuelve lo mismo
            //Cuando se convierte el string a un array de bytes, el tamaño del array coincide con la longitud de la cadena, siempre y cuando no haya
            //caracteres raros, como acentos.
            m.update(originalString.getBytes("UTF-8"), 0, originalString.getBytes("UTF-8").length);
            md5Hash = new BigInteger(1, m.digest()).toString(16);
            //En algunos casos el string resultante tiene una longitud menor a 32 dígitos, habría que completarlo.
            if (md5Hash.length() < 32) {
                //FIXME: Buscar una manera elegante de obtener el char de 0.
                char c = "0".charAt(0);
                return leftCompleteString(md5Hash, 32, c);
                //El string resultante no debería ser mayor a 32 dígitos.
            } else if (md5Hash.length() > 32) {
                throw new Exception("The calculated md5 has more than 32 chars.");
            }
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("The class MessageDigest didn't recognize the MD5 algorithm?", e.getCause() );
        }
        //Si el string resultante tiene una longitud de 32 dígitos exactos, lo devuelvo sin modificar.
        return md5Hash;
    }

    /**
     * This method completes a String (str) with a particular character (cchar)
     * by the left to get to a particular string length (size).
     *
     * @param str A string that must be completed
     * @param size The desired amount of chars that the returned string must
     * have
     * @param cchar The char that will be used to complete the original string
     * @return If the original string has the same length (or it's bigger) than
     * the expected size, it will be returned without any modifications.
     * If not, will be completed at the left with the character passed as an arg.
     */
    public static String leftCompleteString(String str, int size, char cchar) {
        if (str.length() < size) {
            StringBuilder sb = new StringBuilder();
            for (int toAdd = size - str.length(); toAdd > 0; toAdd--) {
                sb.append(cchar);
            }
            sb.append(str);
            String result = sb.toString();
            return result;
        } else {
            return str;
        }
    }

    public static PaymentMethod CreatePaymentMethod(String environment, String psp_MerchantId, String secretCode) {
        try {
            SoapObject CardInputDetails = new SoapObject();
            CardInputDetails.addProperty("Number","4507990000000010");
            CardInputDetails.addProperty("ExpirationDate","1912");
            CardInputDetails.addProperty("SecurityCode","123");
            CardInputDetails.addProperty("HolderName","John Smith");

            SoapObject psp_PaymentMethod = new SoapObject();
            psp_PaymentMethod.addProperty("Product","14");
            psp_PaymentMethod.addProperty("CardInputDetails",CardInputDetails);


            SoapObject Requerimiento = new SoapObject(environment, "CreatePaymentMethod");
            Requerimiento.addProperty("psp_Version",Nps.PSP_VERSION);
            Requerimiento.addProperty("psp_MerchantId",psp_MerchantId);
            Requerimiento.addProperty("psp_PaymentMethod",psp_PaymentMethod);
            Requerimiento.addProperty("psp_PosDateTime",Nps.getPosDateTime());

            String psp_SecureHash = DemoHelpers.calculateMD5(Requerimiento.getProperty("psp_MerchantId") + "" + Requerimiento.getProperty("psp_PosDateTime") + "" + Requerimiento.getProperty("psp_Version") + "" + secretCode);
            Requerimiento.addProperty("psp_SecureHash",psp_SecureHash);

            SoapObject request = new SoapObject(environment, "CreatePaymentMethod");
            request.addProperty("Requerimiento",Requerimiento);

            SoapObject response = Nps.send(environment, "CreatePaymentMethod",request);
            String psp_ResponseCod = response.getPropertyAsString("psp_ResponseCod");
            SoapObject responsePspPaymentMethod = (SoapObject) response.getProperty("psp_PaymentMethod");
            if(Integer.parseInt(psp_ResponseCod) == 2) {
                PaymentMethod paymentMethod = new PaymentMethod();
                paymentMethod.setId(responsePspPaymentMethod.getPropertyAsString("PaymentMethodId"));
                paymentMethod.setProduct(responsePspPaymentMethod.getPropertyAsString("Product"));
                return paymentMethod;
            }

        } catch (Exception q) {

        }

        return new PaymentMethod();
    }

    public static String CreateCustomer(String environment, String psp_MerchantId, String secretCode) {
        try {
            SoapObject CardInputDetails = new SoapObject();
            CardInputDetails.addProperty("Number","4507990000000010");
            CardInputDetails.addProperty("ExpirationDate","1912");
            CardInputDetails.addProperty("SecurityCode","123");
            CardInputDetails.addProperty("HolderName","John Smith");

            SoapObject psp_PaymentMethod = new SoapObject();
            psp_PaymentMethod.addProperty("Product","14");
            psp_PaymentMethod.addProperty("CardInputDetails",CardInputDetails);


            SoapObject Requerimiento = new SoapObject(environment, "CreateCustomer");
            Requerimiento.addProperty("psp_Version",Nps.PSP_VERSION);
            Requerimiento.addProperty("psp_MerchantId",psp_MerchantId);
            Requerimiento.addProperty("psp_EmailAddress","mi@email.com");
            Requerimiento.addProperty("psp_PaymentMethod",psp_PaymentMethod);
            Requerimiento.addProperty("psp_PosDateTime",Nps.getPosDateTime());

            String psp_SecureHash = DemoHelpers.calculateMD5(Requerimiento.getProperty("psp_EmailAddress") + "" + Requerimiento.getProperty("psp_MerchantId") + "" + Requerimiento.getProperty("psp_PosDateTime") + "" + Requerimiento.getProperty("psp_Version") + "" + secretCode);
            Requerimiento.addProperty("psp_SecureHash",psp_SecureHash);

            SoapObject request = new SoapObject(environment, "CreateCustomer");
            request.addProperty("Requerimiento",Requerimiento);

            SoapObject response = Nps.send(environment, "CreateCustomer",request);
            String psp_ResponseCod = response.getPropertyAsString("psp_ResponseCod");
            if(Integer.parseInt(psp_ResponseCod) == 2) {
                return response.getPropertyAsString("psp_CustomerId");
            }
            return "";
        } catch (Exception q) {
            return "";
        }
    }

    public static String CreateClientSession(String environment, String psp_MerchantId, String secretCode) {
        try {
            SoapObject Requerimiento = new SoapObject(environment, "CreateClientSession");
            Requerimiento.addProperty("psp_Version",Nps.PSP_VERSION);
            Requerimiento.addProperty("psp_MerchantId",psp_MerchantId);
            Requerimiento.addProperty("psp_PosDateTime",Nps.getPosDateTime());

            String psp_SecureHash = DemoHelpers.calculateMD5(Requerimiento.getProperty("psp_MerchantId") + "" + Requerimiento.getProperty("psp_PosDateTime") + "" + Requerimiento.getProperty("psp_Version") + "" + secretCode);
            Requerimiento.addProperty("psp_SecureHash",psp_SecureHash);

            SoapObject request = new SoapObject(environment, "CreateClientSession");
            request.addProperty("Requerimiento",Requerimiento);

            SoapObject response = Nps.send(environment, "CreateClientSession",request);
            String psp_ResponseCod = response.getPropertyAsString("psp_ResponseCod");
            if(Integer.parseInt(psp_ResponseCod) == 2) {
                return response.getPropertyAsString("psp_ClientSession");
            }
            return "";
        } catch (Exception q) {
            return "";
        }
    }

     public static ArrayList<PaymentMethod> RetrievePaymentMethods(String environment, String psp_MerchantId, String psp_CustomerId, String secretCode) {
        try {
            SoapObject Requerimiento = new SoapObject(environment, "RetrieveCustomer");
            Requerimiento.addProperty("psp_Version",Nps.PSP_VERSION);
            Requerimiento.addProperty("psp_MerchantId",psp_MerchantId);
            Requerimiento.addProperty("psp_CustomerId",psp_CustomerId);
            Requerimiento.addProperty("psp_PosDateTime",Nps.getPosDateTime());

            String psp_SecureHash = DemoHelpers.calculateMD5(Requerimiento.getProperty("psp_Version") + "" + Requerimiento.getProperty("psp_MerchantId") + "" + Requerimiento.getProperty("psp_CustomerId") + "" + Requerimiento.getProperty("psp_PosDateTime") + "" + secretCode);
            Requerimiento.addProperty("psp_SecureHash",psp_SecureHash);

            SoapObject request = new SoapObject(environment, "RetrieveCustomer");
            request.addProperty("Requerimiento",Requerimiento);

            SoapObject response = Nps.send(environment, "RetrieveCustomer",request);
            String psp_ResponseCod = response.getPropertyAsString("psp_ResponseCod");
            if(Integer.parseInt(psp_ResponseCod) == 2) {

                Vector<SoapObject> psp_PaymentMethods = (Vector<SoapObject>) response.getProperty("psp_PaymentMethods");

                Log.d("RetrieveCards","tengo el soapobject");

                ArrayList<PaymentMethod> PaymentMethodList = new ArrayList<PaymentMethod>();
                for (SoapObject soapObject : psp_PaymentMethods) {
                    SoapObject CardOutputDetails = (SoapObject) soapObject.getProperty("CardOutputDetails");

                    PaymentMethod paymentMethod = new PaymentMethod();
                    paymentMethod.setId(soapObject.getPropertyAsString("PaymentMethodId"));
                    paymentMethod.setMaskedNumber(CardOutputDetails.getPropertyAsString("MaskedNumber"));
                    paymentMethod.setMaskedNumberAlt(CardOutputDetails.getPropertyAsString("MaskedNumberAlternative"));
                    PaymentMethodList.add(paymentMethod);
                }
                return PaymentMethodList;

            }
        } catch (Exception q) {

        }

        return new ArrayList<PaymentMethod>();
    }


}

