package com.singleton.dynamic.builder.validation.exception;

/**
 * <p>
 * Exception class for situations where a validator has been asked to validate an unsupported data
 * type.
 * </p>
 *
 * @author Dustin Singleton
 */
public class MismatchedValidatorTypeException extends RuntimeException
{
    private static final long serialVersionUID = 1046985172758875252L;

    /**
     * Constructs a {@link MismatchedValidatorTypeException} with the specified {@code message}.
     * 
     * @param message
     *            The detail message for the exception.
     */
    public MismatchedValidatorTypeException(String message)
    {
        super(message);
    }
}
