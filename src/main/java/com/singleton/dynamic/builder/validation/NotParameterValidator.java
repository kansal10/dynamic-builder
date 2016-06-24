package com.singleton.dynamic.builder.validation;

import java.lang.reflect.Method;

import com.singleton.dynamic.builder.validation.exception.MismatchedValidatorTypeException;

/**
 * <p>
 * Enumeration of supported validation types that can be performed in a "not" situation, such as not
 * {@code null} or not empty.
 * </p>
 *
 * @author Dustin Singleton
 */
public enum NotParameterValidator
{
    /**
     * Validator for ensuring that the specified arguments are not {@code null}.
     */
    NULL
    {
        @Override
        public void validate(Object objectToValidate, Method method)
        {
            if (objectToValidate == null)
            {
                throw new IllegalArgumentException(
                        method.getName() + " was provided null, but non null values are required");
            }
        }
    },
    /**
     * Validator for ensuring that the specified arguments are not empty. Supported data types are:
     * <ul>
     * <li>{@link String}</li>
     * </ul>
     */
    EMPTY
    {
        @Override
        public void validate(Object objectToValidate, Method method)
        {
            if (String.class.isAssignableFrom(objectToValidate.getClass()))
            {
                if (((String) objectToValidate).isEmpty())
                {
                    throw new IllegalArgumentException(method.getName()
                            + " was provided empty, but non empty values are required");
                }
            }
        }
    };

    /**
     * Validates the specified {@code objectToValidate}. In situations where the validation does not
     * pass, an {@link IllegalArgumentException} should be thrown. All implementations should make
     * no assumptions about the implementation. If the provided {@code objectToValidate} does not
     * match the supported class types, then an {@link MismatchedValidatorTypeException} should be
     * thrown.
     * 
     * @param objectToValidate
     *            The object to perform the validation on.
     * @param method
     *            The method that the validation was performed on.
     */
    public abstract void validate(Object objectToValidate, Method method);
}
