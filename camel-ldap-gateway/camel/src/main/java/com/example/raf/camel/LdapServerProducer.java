package com.example.raf.camel;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.ldap.InitialLdapContext;

import com.example.raf.camel.config.Ldap;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;

@ApplicationScoped
public class LdapServerProducer {

    @Produces
    @Dependent
    @Named("ldapserver")
    public DirContext createLdapServer(Ldap ldap) throws Exception {
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://" + ldap.host() + ":" + ldap.port());
        env.put(Context.URL_PKG_PREFIXES, "com.sun.jndi.url");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, ldap.principal());
        env.put(Context.SECURITY_CREDENTIALS, ldap.password());

        return new InitialLdapContext(env, null);
    }
}
