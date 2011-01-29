package com.jaxzin.common.finance.growthsim;

/**
 * Date: Feb 13, 2006
 * Time: 7:52:26 AM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public class SimulationException extends Exception {
    public SimulationException() {
    }

    public SimulationException(String string) {
        super(string);
    }

    public SimulationException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public SimulationException(Throwable throwable) {
        super(throwable);
    }
}
