package com.jaxzin.iraf.forecast;

import com.jaxzin.common.finance.forecast.Forecast;
import com.jaxzin.common.finance.forecast.ForecastDomain;
import org.jscience.economics.money.Money;

import java.util.List;

/**
 * Date: Feb 10, 2006
 * Time: 6:18:02 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public class IRAForecast implements Forecast<IRAForecast> {

    private ForecastDomain domain;

    private List<List<Money>> forecastData;

    public IRAForecast() {
    }

    public IRAForecast(ForecastDomain domain, List<List<Money>> forecastData) {
        this.domain = domain;
        this.forecastData = forecastData;
    }

    public void setDomain(IRAForecastDomain domain) {
        this.domain = domain;
    }

    public ForecastDomain getDomain() {
        return this.domain;
    }

    public List<List<Money>> getForecastData() {
        return forecastData;
    }

    public void setForecastData(List<List<Money>> forecastData) {
        this.forecastData = forecastData;
    }

    public boolean approxEquals(IRAForecast that) {
        if(this == that) return true;
        if(that == null || getClass() != that.getClass()) return false;

        if (domain != null ? !domain.approxEquals(that.domain) : that.domain != null) return false;
        if (forecastData != null ? !approxEquals(forecastData,that.forecastData) : that.forecastData != null) return false;

        return true;
    }

    private static boolean approxEquals(List<List<Money>> fd1, List<List<Money>> fd2) {
        if(fd1.size() != fd2.size()) return false;

        for(int i = 0; i < fd1.size(); i++) {
            List<Money> sub1 = fd1.get(i);
            List<Money> sub2 = fd2.get(i);
            if(sub1.size() != sub2.size()) return false;

            for(int j = 0; j < sub1.size(); j++) {
                if(!sub1.get(i).approxEquals(sub2.get(i))) return false;
            }
        }
        return true;
    }


    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("IRAForecast");
        sb.append("{domain=").append(domain);
        sb.append(", forecastData=").append(forecastData);
        sb.append('}');
        return sb.toString();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final IRAForecast that = (IRAForecast) o;

        if (domain != null ? !domain.equals(that.domain) : that.domain != null) return false;
        if (forecastData != null ? !forecastData.equals(that.forecastData) : that.forecastData != null) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (domain != null ? domain.hashCode() : 0);
        result = 29 * result + (forecastData != null ? forecastData.hashCode() : 0);
        return result;
    }
}
