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
