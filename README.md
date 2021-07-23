# Spring boot dockerised app






### This app demonstrates how we can dockerise a spring boot app and pass array type env variables

### Spring app config

```
frontend.baseUris[0]=unknown
frontend.baseUris[1]=unknown
```

### Property bean config

```
 @ConfigurationProperties( prefix = "frontend")
public class HelloWorldPropertyConfiguration {
    private List<String>  baseUris;

    public List<String> getBaseUris() {
        return baseUris;
    }

    public void setBaseUris(List<String> baseUris) {
        this.baseUris = baseUris;
    }
} 
```

### Property config injection

```
@EnableConfigurationProperties(HelloWorldPropertyConfiguration.class )
....

 @Bean
    public WebMvcConfigurer corsConfigurer(HelloWorldPropertyConfiguration frontEndPropConfig) {
        String[] baseUris = new String[ frontEndPropConfig.getBaseUris().size() ];
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                .allowedOrigins( frontEndPropConfig.getBaseUris().toArray( baseUris ) );
            }
        };
    } 
```

### Docker build  
Docker file exposes two env variables
```
ENV FRONTEND_BASEURIS_0=willbeoverritten
ENV FRONTEND_BASEURIS_1=willbeoverritten
 
```

`docker build -t helloworld-sb-image -f DockerFile .`

### Docker run
`docker run --name helloworld-sb-container --rm -p 2200:2200 -e FRONTEND_BASEURIS_0=http://localhost:8080 -e FRONTEND_BASEURIS_1=http://localhost:4200 helloworld-sb-image`
