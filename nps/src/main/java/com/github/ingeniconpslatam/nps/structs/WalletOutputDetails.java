package com.github.ingeniconpslatam.nps.structs;

import com.github.ingeniconpslatam.nps.Hydratable;

import org.ksoap2.serialization.SoapObject;

public class WalletOutputDetails implements Hydratable {
    public CardOutputDetails getCardOutputDetails() {
        return cardOutputDetails;
    }

    public void setCardOutputDetails(CardOutputDetails cardOutputDetails) {
        this.cardOutputDetails = cardOutputDetails;
    }

    public ShippingDetails getShippingDetails() {
        return shippingDetails;
    }

    public void setShippingDetails(ShippingDetails shippingDetails) {
        this.shippingDetails = shippingDetails;
    }

    public CardOutputDetails cardOutputDetails;
    public ShippingDetails shippingDetails;

    @Override
    public void hydrate(SoapObject data) {
        CardOutputDetails cod = new CardOutputDetails();
        cod.hydrate((SoapObject) data.getPrimitivePropertySafely("CardOutputDetails"));
        this.setCardOutputDetails(cod);
        ShippingDetails sd = new ShippingDetails();
        sd.hydrate((SoapObject) data.getPropertySafely("ShippingDetails"));
        this.setShippingDetails(sd);
    }
}
