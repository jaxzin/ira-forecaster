package com.jaxzin.common.util;

/**
 * Date: Feb 13, 2006
 * Time: 9:56:03 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public interface ErrorMeasurable<T extends ErrorMeasurable> {
    boolean approxEquals(T o);
}
