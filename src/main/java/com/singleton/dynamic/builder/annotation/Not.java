package com.singleton.dynamic.builder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Annotation that indicates that the parameter is intended to never be specified as {@code null}.
 * </p>
 * <p>
 * Copyright &copy 2016 Cerner Corporation
 * </p>
 *
 * @author Dustin Singleton
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Not
{
    Validator[] value();
}
