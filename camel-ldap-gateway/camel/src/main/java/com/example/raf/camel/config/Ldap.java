package com.example.raf.camel.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "ldap")
public interface Ldap {
    
    String host();
    int port();
    String principal();
    String base();
    String password();
}
