package com.example.helloworldspringwebproject.controller;

import com.example.helloworldspringwebproject.domain.ViewRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.View;
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

    private static Map<String, ViewData> actionToViewMapping = new HashMap<>();

    private static class ViewData{
        private String nextView;
        private String data;

        private ViewData(String nextView, String data) {
            this.nextView = nextView;
            this.data = data;
        }

        private String getNextView() {
            return nextView;
        }

        private String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    static {
        actionToViewMapping.put("start", new ViewData("job",  null)) ;
        actionToViewMapping.put("job", new ViewData("profile",  null));
        actionToViewMapping.put("profile", new ViewData("job",  null));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ViewResponse decideNextView(@RequestBody ViewRequest viewRequest) {

        String currentView = viewRequest.getAction();
        String nextViewName = actionToViewMapping.get( currentView ).getNextView();

        log.info( "{} has this data {}", viewRequest.getAction(), viewRequest.getData() );
        String nextViewzData = " This is " +  nextViewName + "'s data";

        if ( viewRequest.getData() != null ){ //if current's view has data has changed set it
            actionToViewMapping.get( currentView ).setData( viewRequest.getData() ) ;
        }

        if ( actionToViewMapping.get( nextViewName ).getData() != null ){ // if nextView has changed data stored get that
            nextViewzData  = actionToViewMapping.get( nextViewName ).getData();
        }

        return  new ViewResponse( currentView, nextViewzData, nextViewName );
    }

    @PostMapping(value= "/goback", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ViewResponse decidePreviousView(@RequestBody ViewRequest viewRequest) {
        String lastView = viewRequest.getAction();

        log.info( "{} has this last view's data  {}", lastView, viewRequest.getData() );
        String data = actionToViewMapping.get( lastView ).getData() ; // get the last saved data for the last view
        String nextView = actionToViewMapping.get( lastView ).getNextView();

        actionToViewMapping.get( nextView ).setData( viewRequest.getData() ); // before going back capture the state of the current view

        return  new ViewResponse( lastView, data, nextView );
    }

}
