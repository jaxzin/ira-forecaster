package com.jaxzin.common.finance.growthsim;

import com.jaxzin.common.util.ErrorMeasurable;
import org.jscience.physics.quantities.Dimensionless;

/**
 * Date: Feb 10, 2006
 * Time: 11:05:20 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public interface IssueStats<T extends IssueStats> extends ErrorMeasurable<T> {
    Dimensionless getMeanReturn();
    Dimensionless getStandardDeviation();
}
