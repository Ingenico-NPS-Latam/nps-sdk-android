package com.github.ingeniconpslatam.nps.structs;


import com.github.ingeniconpslatam.nps.Hydratable;

import org.ksoap2.serialization.SoapObject;

public class CardOutputDetails implements Hydratable {

    public String number;
    public String expirationDate;
    public String expirationYear;
    public String expirationMonth;
    public String holderName;
    public String iin;
    public String last4;
    public String numberLength;
    public String maskedNumber;
    public String maskedNumberAlternative;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }

    public String getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public String getIin() {
        return iin;
    }

    public void setIin(String iin) {
        this.iin = iin;
    }

    public String getLast4() {
        return last4;
    }

    public void setLast4(String last4) {
        this.last4 = last4;
    }

    public String getNumberLength() {
        return numberLength;
    }

    public void setNumberLength(String numberLength) {
        this.numberLength = numberLength;
    }

    public String getMaskedNumber() {
        return maskedNumber;
    }

    public void setMaskedNumber(String maskedNumber) {
        this.maskedNumber = maskedNumber;
    }

    public String getMaskedNumberAlternative() {
        return maskedNumberAlternative;
    }

    public void setMaskedNumberAlternative(String maskedNumberAlternative) {
        this.maskedNumberAlternative = maskedNumberAlternative;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    @Override
    public void hydrate(SoapObject data) {
        this.setNumber(data.getPrimitivePropertySafelyAsString("Number"));
        this.setExpirationDate(data.getPrimitivePropertySafelyAsString("ExpirationDate"));
        this.setExpirationYear(data.getPrimitivePropertySafelyAsString("ExpirationYear"));
        this.setExpirationMonth(data.getPrimitivePropertySafelyAsString("ExpirationMonth"));
        this.setHolderName(data.getPrimitivePropertySafelyAsString("HolderName"));
        this.setIin(data.getPrimitivePropertySafelyAsString("IIN"));
        this.setLast4(data.getPrimitivePropertySafelyAsString("Last4"));
        this.setNumberLength(data.getPrimitivePropertySafelyAsString("NumberLength"));
        this.setMaskedNumber(data.getPrimitivePropertySafelyAsString("MaskedNumber"));
        this.setMaskedNumberAlternative(data.getPrimitivePropertySafelyAsString("MaskedNumberAlternative"));
    }
}
