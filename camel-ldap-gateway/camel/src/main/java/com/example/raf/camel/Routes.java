package com.example.raf.camel;

import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ClaimCheckOperation;

import com.example.raf.camel.config.Endpoint;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class Routes extends RouteBuilder {

    @Inject
    Endpoint endpoint;

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
                .recipientList(simple("http://" + endpoint.host() + ":" + endpoint.port()
                        + ((endpoint.path().startsWith("/")) ? endpoint.path() : "/" + endpoint.path())
                        + "?bridgeEndpoint=true"))
                .log("redirected");

    }
}
