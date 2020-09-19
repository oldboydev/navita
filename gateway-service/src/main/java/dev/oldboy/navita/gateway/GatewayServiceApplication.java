package dev.oldboy.navita.gateway;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;

import reactor.core.publisher.Mono;

@SpringBootApplication
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}
//	
//	@Bean
//	public GlobalFilter customGlobalFilter() {
//	    return ((exchange, chain) -> {
//	      System.out.println("filter trigger");
//	      return exchange.getPrincipal()
//	          .map(Principal::getName)
//	          .defaultIfEmpty("Default User")
//	          .map(userName -> {
//	            //adds header to proxied request
//	            exchange.getRequest().mutate().header("CUSTOM-REQUEST-HEADER", userName).build();
//	            return exchange;
//	          })
//	          .flatMap(chain::filter);
//	    });
//	}
}
