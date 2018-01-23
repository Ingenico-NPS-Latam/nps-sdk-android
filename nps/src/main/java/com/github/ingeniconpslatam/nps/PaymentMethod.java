package com.github.ingeniconpslatam.nps;

/**
 * Created by fernando on 3/27/17.
 */

public class PaymentMethod {
    public String id;
    public String maskedNumber;
    public String maskedNumberAlt;
    public String cardSecurityCode;
    public String cardNumPayments;
    public String product;

    public String getId() {
        return id;
    }
    public PaymentMethod setId(String id) {
        this.id = id;
        return this;
    }

    public String getMaskedNumber() {
        return maskedNumber;
    }
    public String getMaskedNumberAlt() {
        return maskedNumberAlt;
    }
    public PaymentMethod setMaskedNumber(String maskedNumber) {
        this.maskedNumber = maskedNumber;
        return this;
    }
    public PaymentMethod setMaskedNumberAlt(String maskedNumberAlt) {
        this.maskedNumberAlt = maskedNumberAlt;
        return this;
    }

    public String getCardSecurityCode() {
        return this.cardSecurityCode;
    }

    public PaymentMethod setCardSecurityCode(String cardSecurityCode) {
        this.cardSecurityCode = cardSecurityCode;
        return this;
    }


    public String getCardNumPayments() {
        return cardNumPayments;
    }

    public PaymentMethod setCardNumPayments(String cardNumPayments) {
        this.cardNumPayments = cardNumPayments;
        return this;
    }

    public String getProduct() {
        return product;
    }

    public PaymentMethod setProduct(String product) {
        this.product = product;
        return this;
    }
}
