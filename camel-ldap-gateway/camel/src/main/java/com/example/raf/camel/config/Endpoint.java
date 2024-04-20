package com.example.raf.camel.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "endpoint")
public interface Endpoint {

    String host();
    int port();
    String path();
}
