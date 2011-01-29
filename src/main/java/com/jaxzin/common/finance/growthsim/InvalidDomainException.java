package com.jaxzin.common.finance.growthsim;

/**
 * Date: Feb 13, 2006
 * Time: 7:49:49 AM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public class InvalidDomainException extends Exception {
    public InvalidDomainException() {
    }

    public InvalidDomainException(String string) {
        super(string);
    }

    public InvalidDomainException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public InvalidDomainException(Throwable throwable) {
        super(throwable);
    }
}
