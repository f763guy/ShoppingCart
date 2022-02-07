package com.common;


import java.lang.annotation.*;

@Target(value= ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenForm {

    boolean create() default false;
    boolean remove() default false;
}
