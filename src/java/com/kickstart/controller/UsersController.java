package com.kickstart.controller;

import com.strategicgains.restexpress.Request;
import com.strategicgains.restexpress.Response;

/**
 * Created by Pavithra on 17/7/14.
 */

public class UsersController {

    public String read(Request request, Response response)
    {
        String value = request.getRawHeader("welcome");
        response.setResponseCreated();
        response.setContentType("text/xml");

      return   "WELCOME TO Restful webservice display page!!!";
    }

    public String readAll(Request request,Response response){
        String name=System.getProperty("user.name");
        response.setResponseCreated();

        if(request.getUrlDecodedHeader("name").equals(name))
        {
            return name;
        }
      else
        {
            return "Value not Matching";
        }
    }

}
