package com.kickstart.controller;

import com.strategicgains.restexpress.Request;
import com.strategicgains.restexpress.Response;

/**
 * Created by Pavithra on 17/7/14.
 */

public class ConversionController {

    public String convertInchToFeet(Request request,Response response) {

        int inch= Integer.parseInt(request.getRawHeader("value"));
        double feet = 0;
        feet =(double) inch/12;

        return "<InchToFeetService>"
                + "<Inch>" + inch + "</Inch>"
                + "<Feet>" + feet + "</Feet>"
                + "</InchToFeetService>";
    }

    public String convertFeetToInch(Request request,Response response) {
        int inch=0;
        int feet =Integer.parseInt(request.getRawHeader("value"));
        inch = 12*feet;

        return "<FeetToInchService>"
                + "<Feet>" + feet + "</Feet>"
                + "<Inch>" + inch + "</Inch>"
                + "</FeetToInchService>";
    }


}
