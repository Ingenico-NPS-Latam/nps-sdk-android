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
5. Client Device shows credit card input data form
6. Customer completes the form with credit card data
7. Customer device uses clientsession ID to request CreatePaymentMethodToken() on Ingenico NPS Latam sending all sensitive payment details. This token is a one time used token.
8. Ingenico NPS Latam responses with a PaymentMethodToken (This token can be used just one time)
9. Customer device sends PaymentMethodToken to Merchant Backend
10. Merchant Backend requests any type of payment using the token received 
11. Ingenico NPS Latam responses the payment made.
12. Merchant Backend sends payment response to customer device.
13. And finally customer device shows payment response to the customer 

## Installation
    
To get started install on your Android API the NPS android sdk. You can choose to clone or download from github or use compile:

### Compile

just add the nps-android-sdk dependency to your build.gradle file.

```bash
compile 'com.github.ingenico-nps-latam:nps-sdk-android:1.0.46'
```

### Github

1. Clone or download the project from [github](https://github.com/Ingenico-NPS-Latam/nps-sdk-android)
2. On your Android Studio go to: File -> New -> Import Module and find nps-sdk-android folder on your file system.
3. Go to File -> Project Structure..., this will open a window, then choose on Modules section your app, click on Dependencies tab,  click on "+" button, and finally on Module dependency dialog choose nps-sdk.

```bash
compile 'com.github.ingenico-nps-latam:nps-sdk-android:1.0.46'
```

### Github Demo

Ingenico Nps Latam has a demo project where the developers can read the code of a working app integrated with our platform

1. Clone or download the project from [github](https://github.com/Ingenico-NPS-Latam/nps-sdk-android)
2. Open the project with Android Studio.
3. Add to the Demo build.gradle file the nps-sdk-android dependency and sync the project

```bash
compile 'com.github.ingenico-nps-latam:nps-sdk-android:1.0.46'
```



##  Integrate

Declare the necessary permissions for your Android Project by adding the following lines to app/src/AndroidManifest.xml, inside the <application> tags.

```java
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
```

Add to yours APK build.gradle this repository

```java
maven { url "https://oss.sonatype.org/content/repositories/ksoap2-android-releases/" }
```

### Github Demo

Ingenico Nps Latam has a demo project where the developers can read the code of a working app integrated with our platform

1. Clone or download the project from [github](https://github.com/Ingenico-NPS-Latam/nps-sdk-android)
2. Open the project with Android Studio.
3. Add to the Demo build.gradle file the nps-sdk-android dependency and sync the project

```bash
compile 'com.github.ingenico-nps-latam:nps-sdk-android:1.0.46'
```


Import all nps classes from com.github.ingeniconpslatam.nps namespace

```java
import com.github.ingeniconpslatam.nps.Card;
import com.github.ingeniconpslatam.nps.InstallmentOption;
import com.github.ingeniconpslatam.nps.Nps;
import com.github.ingeniconpslatam.nps.PaymentMethod;
import com.github.ingeniconpslatam.nps.PaymentMethodToken;
```

##  Configure

To be able to use nps-android-sdk methods you need to get a new instance of the class an pass by parameters your client session and your merchant id.
Follow the next sections to get the parameters.

```html
Nps nps = new Nps(Nps.SANDBOX, "__YOUR_CLIENT_SESSION__", "__YOUR_MERCHANT_ID__");
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

Nps nps = new Nps(Nps.SANDBOX, "__YOUR_NPS_CLIENT_SESSION__", "__YOUR_NPS_MERCHANT_ID__");

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



###  Tokenizing Cards From PaymentMethodId

You are able to create a PaymentMethodToken with only your stored PaymentMethodId and the card security code; Card security code is only required in some countries.
PaymentMethodId is a persistant PaymentMethodToken wich is created by calling the server-side method [CreatePaymentMethod (Request)](#panel-parameters-reference) and [CreateCustomer (Request)](#panel-parameters-reference), 
Behaviour and capabilities of recache has been cloned from original createPaymentMethodToken method.

> Recache a PaymentMethodToken by passing a PaymentMethod Object with the PaymentMethod data

```html
<script>
Nps nps = new Nps(Nps.SANDBOX, "__YOUR_NPS_CLIENT_SESSION__", "__YOUR_NPS_MERCHANT_ID__");

PaymentMethod paymentMethod = new PaymentMethod();
paymentMethod.setId('51e0kuKSwkG3GlaGq2fQaNdBsfOY0EHY');
paymentMethod.setCardSecurityCode("123");

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

nps.recachePaymentMethodToken(paymentMethod, billing, new Nps.ResponseHandler() {
        @Override
        public void onSuccess(PaymentMethodToken paymentMethodToken) {
            Log.d("Nps", "token success = " + paymentMethodToken.getId());
        }

        @Override
        public void onError(Exception error) {
            Log.d("Nps", "token error");
        }
    });
</script>
```



##  Card Validators

Form validation is mandatory. On form submition nps.validateCardNumber must be executed below sequence of validation :

###  card.validateCardHolderName

This validator checks if the name on card is a valid name with min-length of 2 and max-length of 26

###  card.validateCardNumber

Checks that the number of credit card is formatted correctly and pass the Luhn test.

### # IIN Lookup

By retrieving the product ID with the IIN you are able to validate the card number.
This method will also call the webservice method "GetIINDetails":
[GetIINDetails (Request)](#panel-parameters-reference)
[GetIINDetails (Response)](#panel-parameters-reference)

```java
Nps nps = new Nps(Nps.SANDBOX, "__YOUR_NPS_CLIENT_SESSION__", "__YOUR_NPS_MERCHANT_ID__");

nps.getIINDetails('450799');
```

###  card.validateCardExpDate

This validator checks if the expiration date is a valid month and not expired.

###  card.validateCardSecurityCode

This validator checks if the security code is a valid integer (size 3-4 characters).

##  Card Installments

if you require card installment in the specific case that the customer must view the installment payment amount you can follow the next example:

```java
Nps nps = new Nps(Nps.SANDBOX, "__YOUR_NPS_CLIENT_SESSION__", "__YOUR_NPS_MERCHANT_ID__");
nps.setAmount('120050');
nps.setCountry('CHL');
nps.setCurrency('152');

nps.getInstallmentsOptions('8T3BOsXaLMxVsvtHiSuWKL1DEOUUDq3N', '14', '3');
```

###  Configure Amount

You should configure the FULL payment amount as cents, to be able to accept installment to calculate the installment amount. To do this, use the call setAmount.

```java
Nps nps = new Nps(Nps.SANDBOX, "__YOUR_NPS_CLIENT_SESSION__", "__YOUR_NPS_MERCHANT_ID__");
nps.setAmount('120050');
```

###  Configure Country

You should configure the country where the payment is recieved, to be able to accept installment to calculate the installment amount. To do this, use the call setCountry.

```java
Nps nps = new Nps(Nps.SANDBOX, "__YOUR_NPS_CLIENT_SESSION__", "__YOUR_NPS_MERCHANT_ID__");
nps.setCountry('CHL');
```

###  Configure Currency

You should configure the currency wich the payment is processed in, to be able to accept installment to calculate the installment amount. To do this, use the call setCurrency.

```java
Nps nps = new Nps(Nps.SANDBOX, "__YOUR_NPS_CLIENT_SESSION__", "__YOUR_NPS_MERCHANT_ID__");
nps.setCurrency('152');
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