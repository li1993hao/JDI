package com.tiptimes.tp.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * action注解
 * 为actionDeal提供url,或者actionListener
 * @author haoli
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Action {
	String url() ;
	String actionListener() default "";
}
