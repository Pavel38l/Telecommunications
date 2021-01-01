package ru.vsu.telecom.factory;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Configuration {
    String[] packagePaths() default {"ru.vsu.telecom"};
}

