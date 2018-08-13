package com.eshaghi.poc.cache;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public interface Bootstrap {

    static void main(String[] args) {
        new AnnotationConfigApplicationContext("com.eshaghi.poc.cache");
    }
}
