package com.example.helloworldspringwebproject.controller;

import com.example.helloworldspringwebproject.domain.ViewRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController()
@RequestMapping("/helloWorld")
public class HelloWorldController {
    private final static Logger log = LoggerFactory.getLogger( HelloWorldController.class) ;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public HelloResponse getARandomOtherNameGiven (@RequestParam (name="inputName") String inputName){
        String aRandomString = UUID.randomUUID().toString();
        StringBuilder sb = new StringBuilder();
        log.info( " '{}' this is the input name ", inputName);
        sb.append( inputName ).append(" had been jibberished with ").append( aRandomString);
        log.info( "And '{}' is the jibberish output", sb );
        return new HelloResponse( sb.toString() );
    }

    private class HelloResponse {
        private String jibberishedName;

        public HelloResponse(String jibberishedName) {
            this.jibberishedName = jibberishedName;
        }

        public String getJibberishedName(){ return this.jibberishedName; }
    }

    private class ViewResponse {
        private String viewName;
        private String data;
        private String nextViewName;


        public ViewResponse(String viewName, String data, String nextViewName) {
            this.viewName = viewName;
            this.data = data;
            this.nextViewName = nextViewName;
        }

        public String getViewName() {
            return viewName;
        }

        public String getData() {
            return data;
        }

        public String getNextViewName() {
            return nextViewName;
        }

    }

    private static Map<String, String> actionToViewMapping = new HashMap<>();

    static {
        actionToViewMapping.put("start", "job");
        actionToViewMapping.put("job", "profile");
        actionToViewMapping.put("profile", "job");
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ViewResponse decideNextView(@RequestBody ViewRequest viewRequest) {
        String nextViewName = actionToViewMapping.get( viewRequest.getAction() );

        log.info( "{} has this data {}", viewRequest.getAction(), viewRequest.getData() );
        String data = " This is " +  nextViewName + "'s data";

        return  new ViewResponse(viewRequest.getAction(), data, nextViewName );
    }
}
