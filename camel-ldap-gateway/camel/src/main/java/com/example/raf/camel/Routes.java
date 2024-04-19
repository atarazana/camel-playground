package com.example.raf.camel;

import javax.naming.NamingException;

import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ClaimCheckOperation;

public class Routes extends RouteBuilder {

    @Override
    public void configure() throws RuntimeCamelException, NamingException {

        from("platform-http:/calc")
                .claimCheck(ClaimCheckOperation.Push)
                .transform().constant("(objectClass=*)")
                .to("ldap:ldapserver?base=dc=example,dc=com&returnedAttributes=uid")
                .log(body().toString())
                .claimCheck(ClaimCheckOperation.Pop)
                .log(body().toString())
                .to("direct:A");

        from("direct:A")
                .removeHeader(Exchange.HTTP_PATH)
                .recipientList(simple("http://localhost:8082/calculator-ws/CalculatorService?bridgeEndpoint=true"))
                .log("redirected");

    }
}
