package com.kickstart.controller;

import com.strategicgains.restexpress.Request;
import com.strategicgains.restexpress.Response;

/**
 * Created by Pavithra on 17/7/14.
 */

public class ConversionController {

    public String convertInchToFeet(Request request,Response response) {

        int inch= Integer.parseInt(request.getUrlDecodedHeader("value"));

        double feet = 0;
        feet =(double) inch/12;

        return inch +"inch = " + feet+"feet!" ;
    }

    public String convertFeetToInch(Request request,Response response) {
        int inch=0;
        int feet =Integer.parseInt(request.getUrlDecodedHeader("value"));
        inch = 12*feet;

        return  feet+"feet = "+inch +"inches !";
    }


}
