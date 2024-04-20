package com.example.raf.camel;

import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ClaimCheckOperation;

public class Routes extends RouteBuilder {

    @Override
    public void configure() throws RuntimeCamelException {

        from("platform-http:/calc")
                .claimCheck(ClaimCheckOperation.Push)
                // .transform().constant("(objectClass=*)")
                .transform().simple("(cn=${header.username})")
                .to("ldap:ldapserver?base=dc=example,dc=com&returnedAttributes=uid")
                .choice()
                .when(simple("${body.isEmpty()}"))
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(401))
                .setBody(constant("401 Authorization Required"))
                .otherwise()
                .claimCheck(ClaimCheckOperation.Pop)
                .log(body().toString())
                .to("direct:A");

        from("direct:A")
                .removeHeader(Exchange.HTTP_PATH)
                .recipientList(simple("http://localhost:8082/calculator-ws/CalculatorService?bridgeEndpoint=true"))
                .log("redirected");

    }
}
