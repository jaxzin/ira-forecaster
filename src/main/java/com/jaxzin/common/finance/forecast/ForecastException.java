/**
 * Copyright (C) 2006-2011 Brian R. Jackson <brian@jaxzin.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jaxzin.common.finance.forecast;

/**
 * Date: Feb 13, 2006
 * Time: 5:58:48 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public class ForecastException extends Exception {

    private Forecaster forecaster = null;

    public ForecastException() {
    }

    public ForecastException(String string) {
        super(string);
    }

    public ForecastException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public ForecastException(Throwable throwable) {
        super(throwable);
    }

    public ForecastException(Forecaster forecaster) {
        this.forecaster = forecaster;
    }

    public ForecastException(String string, Forecaster forecaster) {
        super(string);
        this.forecaster = forecaster;
    }

    public ForecastException(String string, Throwable throwable, Forecaster forecaster) {
        super(string, throwable);
        this.forecaster = forecaster;
    }

    public ForecastException(Throwable throwable, Forecaster forecaster) {
        super(throwable);
        this.forecaster = forecaster;
    }

    public Forecaster getForecaster() {
        return forecaster;
    }

    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(" :: ");
        sb.append("ForecastException");
        sb.append("{forecaster=").append(forecaster);
        sb.append('}');
        return sb.toString();
    }
}
