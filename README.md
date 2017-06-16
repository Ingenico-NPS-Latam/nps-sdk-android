This is a Android SDK that allows you to process payments directly from the client side and generate a Payment to Ingenico NPS servers without having any sensitive payment details passing through your servers.

##  How It Works

When a customer submits your payment form, nps-android-sdk sends customer sensitive payment details to be encrypted and stored at Ingenico NPS servers and gives you a PaymentMethodToken to complete Payment process using our API.

With this One-time-use PaymentMethodToken, you can do anything with our API that requires sensitive payment information. Through this mechanism you never handle any sensitive payment information and your PCI scope will be greatly reduced.


**Basic Flow:**

+ Securily collecting sensitive payment details with NPS.js
+ Converting those payment details to a One-time-use PaymentMethodToken
+ Submitting the PaymentMethodToken to your server.
+ Use [Sale/Authorization Only](#payments) methods with the psp_VaultReference.PaymentMethodToken to finish the payment.


**To Process payments with nps-android-sdk you should follow the following steps**

1. Customer device ask to Merchant Backend for a clientSession ID
2. Merchant Backend does CreateClientSession() on Ingenico NPS Latam
3. Ingenico NPS Latam responses the CreateClientSession() with a clientsession ID
4. The clientsession ID is sended to customer device
5. Customer device uses clientsession ID to request CreatePaymentMethodToken() on Ingenico NPS Latam sending all sensitive payment details
6. Ingenico NPS Latam responses with a Token (This token can be used just one time)
7. Customer device sends the Token to Merchant Backend
8. Merchant Backend requests any type of payment using the token received
9. Ingenico NPS Latam responses the payment made.

##  Installation
    
To get started install on your Android API following the next steps:

###  Android Studio

1. Clone this project
2. On your Android Studio go to: File -> New -> Import Module and find conektasdk folder on your file system.
3. Go to File -> Project Structure..., this will open a window, then choose on Modules section your app, click on Dependencies tab,  click on "+" button, and finally on Module dependency dialog choose nps-sdk.

Add the nps-android-sdk dependency to the build.gradle file.

```java
dependencies {
    compile project(':nps')
}
```

##  Integrate

Declare the necessary permissions for your Android Project by adding the following lines to app/src/AndroidManifest.xml, inside the <application> tags.

```java
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
```

Import all nps classes from ar.com.nps namespace

```java
import ar.com.nps.android.Card;
import ar.com.nps.android.InstallmentOption;
import ar.com.nps.android.Nps;
import ar.com.nps.android.PaymentMethod;
import ar.com.nps.android.PaymentMethodToken;
```

##  Configure

To be able to use nps-android-sdk methods you need to get a new instance of the class an pass by parameters your client session and your merchant id.
Follow the next sections to get the parameters.

```html
Nps nps = new Nps(getApplicationContext(), "__YOUR_CLIENT_SESSION__", "__YOUR_MERCHANT_ID__");
```

###  Configure Your Client Session

You should configure your client session to be able to start to use nps-android-sdk to identify your site while communicating with NPS. 
To do this, set value as parameter on the constructor of the Nps class. Remember to replace the client session call on sandbox with the production domain when you are ready to create real charges.

You can obtain your client session by calling the webservice method "CreateClientSession"
[CreateClientSession (Request)](#panel-parameters-reference)
[CreateClientSession (Response)](#panel-parameters-reference)


###  Configure Your Merchant ID

You should configure your merchant ID to be able to start to use nps-android-sdk to identify your site while communicating with NPS. 
To do this, use the merchant ID provided by NPS. Remember to replace the merchant ID on sandbox with the production merchant ID when you are ready to create real charges.



##  Tokenizing Cards

The main function provided by nps-android-sdk is card tokenization, this allows you to securely send card information to Ingenico NPS without it ever touching your servers reducing PCI obligations. 
Once the PaymentMethoToken has been generated, you can safely pass it to your servers and process the payment or save it as a re-usable PaymentMethodId.


> Create a PaymentMethodToken by passing a card object with the payment method data. Also an optional billing object

```java

Nps nps = new Nps(getApplicationContext(), "__YOUR_NPS_CLIENT_SESSION__", "__YOUR_NPS_MERCHANT_ID__");

Card card = new Card()
        .setHolderName("John Smith")
        .setNumber("4507990000000010")
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
          .setPhoneNumber2("4123-5678");

billing.getAddress()
          .setAdditionalInfo("adding additional info")
          .setCity("Capital Federal")
          .setStateProvince("Capital Federal")
          .setCountry("ARG")
          .setZipCode("1414")
          .setStreet("Calle Falsa")
          .setHouseNumber("123");

nps.createPaymentMethodToken(card, billing, 
    new Nps.ResponseHandler() {
        @Override
        public void onSuccess(PaymentMethodToken paymentMethodToken) {
            Log.d("Nps", "token success = " + paymentMethodToken.getId());
        }

        @Override
        public void onError(Exception error) {
            Log.d("Nps", "token error");
        }
    });
```

The **Nps.ResponseHandler.onSuccess** receives the PaymentMethodToken response as an object. The main attribute of this object is the paymentMethodToken.id, which you will need to process the payment through the API.

The **Nps.ResponseHandler.onError** should be used for error handling. The common errors may be caused by mistakes in development time or invalid payment details input data.



##  Card Validators

Form validation is mandatory. On form submition nps.validateCardNumber must be executed below sequence of validation :

###  card.validateCardHolderName

This validator checks if the name on card is a valid name with min-length of 2 and max-length of 26

###  card.validateCardNumber

Checks that the number of credit card is formatted correctly and pass the Luhn test.

###  card.validateCardExpDate

This validator checks if the expiration date is a valid month and not expired.

###  card.validateCardSecurityCode

This validator checks if the security code is a valid integer (size 3-4 characters).

##  Card Installments

if you require card installment in the specific case that the customer must view the installment payment amount you can follow the next tutorial:

###  Configure Amount

You should configure the FULL payment amount as cents, to be able to accept installment to calculate the installment amount. To do this, use the call setAmount.

```java
nps.setAmount('120050');
```

###  Configure Country

You should configure the country where the payment is recieved, to be able to accept installment to calculate the installment amount. To do this, use the call setCountry.

```java
NPS.setCountry('CHL');
```

###  Configure Currency

You should configure the currency wich the payment is processed in, to be able to accept installment to calculate the installment amount. To do this, use the call setCurrency.

```java
NPS.setCurrency('152');
```

##  Device Fingerprint

Through this functionality you can collect information about your end-user's devices. The client
generates a blackbox that contains all available device information. You then return the blackbox (Device Fingerprint) to NPS from
your back-end servers using the psp_CustomerAdditionalDetails.DeviceFingerPrint API field for Fraud Screening porpouses.


###  Nps.getDeviceFingerprint

This method allows you to get de Device Figerprint of the end-user's device.

```java

Nps.getDeviceFingerprint(getApplicationContext());
```