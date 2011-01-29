package com.jaxzin.common.finance.growthsim;

import org.jfree.data.time.RegularTimePeriod;
import org.jscience.physics.quantities.Dimensionless;

/**
 * Date: Feb 10, 2006
 * Time: 11:37:28 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public class SimpleIssueStats implements IssueStats<SimpleIssueStats> {

    Dimensionless meanReturn;
    Dimensionless standardDeviation;

    public SimpleIssueStats(
            final Dimensionless meanReturn,
            final Dimensionless standardDeviation)
    {
        this.meanReturn = meanReturn;
        this.standardDeviation = standardDeviation;
    }

    public Dimensionless getMeanReturn() {
        return meanReturn;
    }

    public Dimensionless getStandardDeviation() {
        return standardDeviation;
    }

    public boolean approxEquals(SimpleIssueStats that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;

        if (!meanReturn.approxEquals(that.meanReturn)) return false;
        if (!standardDeviation.approxEquals(that.standardDeviation)) return false;

        return true;
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SimpleIssueStats");
        sb.append("{meanReturn=").append(meanReturn);
        sb.append(", standardDeviation=").append(standardDeviation);
        sb.append('}');
        return sb.toString();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final SimpleIssueStats that = (SimpleIssueStats) o;

        if (!meanReturn.equals(that.meanReturn)) return false;
        if (!standardDeviation.equals(that.standardDeviation)) return false;

        return true;
    }

    public int hashCode() {
        int result = 17;
        result = 29 * result + meanReturn.hashCode();
        result = 29 * result + standardDeviation.hashCode();
        return result;
    }
}
