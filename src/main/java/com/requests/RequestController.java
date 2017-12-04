package com.requests;

import com.models.Request;
import com.models.Response;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {

    @RequestMapping(value="/ebank", method = RequestMethod.POST)
    public Response RequestDispatcher(@RequestBody Request request){
        if(request.getMethod() == null){
            return new Response(404, new Object[]{"Invalid Request"});
        }
        return new Response(1, new Object []{"fuck " + request.getId()});
    }
}
