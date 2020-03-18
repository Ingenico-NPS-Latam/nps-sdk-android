package com.github.ingeniconpslatam.nps.structs;

import com.github.ingeniconpslatam.nps.Billing;
import com.github.ingeniconpslatam.nps.Hydratable;

import org.ksoap2.serialization.SoapObject;

public class ShippingDetails implements Hydratable {

    public String trackingNumber;
    public String method;
    public String carrier;
    public String deliveryDate;
    public String freightAmount;
    public String giftMessage;
    public String giftWrapping;
    public Billing.Person primaryRecipient;
    public Billing.Person secondaryRecipient;
    public Billing.Address address;

    public Billing.Person getPrimaryRecipient() {
        return primaryRecipient;
    }

    public void setPrimaryRecipient(Billing.Person primaryRecipient) {
        this.primaryRecipient = primaryRecipient;
    }

    public Billing.Person getSecondaryRecipient() {
        return secondaryRecipient;
    }

    public void setSecondaryRecipient(Billing.Person secondaryRecipient) {
        this.secondaryRecipient = secondaryRecipient;
    }


    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getFreightAmount() {
        return freightAmount;
    }

    public void setFreightAmount(String freightAmount) {
        this.freightAmount = freightAmount;
    }

    public String getGiftMessage() {
        return giftMessage;
    }

    public void setGiftMessage(String giftMessage) {
        this.giftMessage = giftMessage;
    }

    public String getGiftWrapping() {
        return giftWrapping;
    }

    public void setGiftWrapping(String giftWrapping) {
        this.giftWrapping = giftWrapping;
    }

    public Billing.Address getAddress() {
        return address;
    }

    public void setAddress(Billing.Address address) {
        this.address = address;
    }


    @Override
    public void hydrate(SoapObject data) {
        this.setTrackingNumber(data.getPropertySafelyAsString("TrackingNumber"));
        this.setMethod(data.getPropertySafelyAsString("Method"));
        this.setCarrier(data.getPropertySafelyAsString("Carrier"));
        this.setDeliveryDate(data.getPropertySafelyAsString("DeliveryDate"));
        this.setFreightAmount(data.getPropertySafelyAsString("FreightAmount"));
        this.setGiftMessage(data.getPropertySafelyAsString("GiftMessage"));
        this.setGiftWrapping(data.getPropertySafelyAsString("GiftWrapping"));
//        this.setPrimaryRecipient(data.getPropertySafelyAsString("PrimaryRecipient"));
//        this.setSecondaryRecipient(data.getPropertySafelyAsString("SecondaryRecipient"));
//        this.setAddress(data.getPropertySafelyAsString("Address"));
    }
}
