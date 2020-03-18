package com.github.ingeniconpslatam.nps;

import org.ksoap2.serialization.SoapObject;

public interface Hydratable {

    void hydrate(SoapObject data);
}
