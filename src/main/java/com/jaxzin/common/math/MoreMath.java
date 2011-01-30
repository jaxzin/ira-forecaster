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
package com.jaxzin.common.math;

/**
 * Date: Feb 12, 2006
 * Time: 9:35:07 PM
 *
 * @author <a href="mailto:brian@jaxzin.com">Brian R. Jackson</a>
 */
public final class MoreMath {

    private MoreMath() {}

    public static double cdfinv(double p) {
        return MoreMath.nor(p);
    }

     /**
      *  Inverse of standart normal CDF. i.e. returns value x, such that
      *  int(f,-inf..x)=p <a href="http://lib.stat.cmu.edu/apstat/241">PPND16,
      *  ALGORITHM AS241 APPL. STATIST. (1988) VOL. 37, NO. 3, 477-484.</a>
      *
      *@param  p  double - probability
      *@return    double - value of x
      */
     private static double nor(double p) {
         //redu with apstat 241
         //return VisualNumerics.math.Statistics.inverseNormalCdf(p);
         if (Double.isNaN(p)) {
             return 0;
         }
         double q = p - 0.5;
         double r;
         double ret_val;
         if (Math.abs(q) <= 0.425) {
             r = 0.180625 - q * q;
             return q
                      * (((((((r * 2509.0809287301226727 + 33430.575583588128105) * r
                      + 67265.770927008700853)
                      * r
                      + 45921.953931549871457)
                      * r
                      + 13731.693765509461125)
                      * r
                      + 1971.5909503065514427)
                      * r
                      + 133.14166789178437745)
                      * r
                      + 3.387132872796366608)
                      / (((((((r * 5226.495278852854561 + 28729.085735721942674) * r
                      + 39307.89580009271061)
                      * r
                      + 21213.794301586595867)
                      * r
                      + 5394.1960214247511077)
                      * r
                      + 687.1870074920579083)
                      * r
                      + 42.313330701600911252)
                      * r
                      + 1);
         }
         r = Math.min(1 - p, p);
         if (r <= 0) {
             return p > 0 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
         }
         r = Math.sqrt(-Math.log(r));
         if (r <= 5) {
             r += -1.6;
             return (q < 0 ? -1 : 1)
                      * (((((((r * 7.7454501427834140764e-4 + .0227238449892691845833) * r
                      + .24178072517745061177)
                      * r
                      + 1.27045825245236838258)
                      * r
                      + 3.64784832476320460504)
                      * r
                      + 5.7694972214606914055)
                      * r
                      + 4.6303378461565452959)
                      * r
                      + 1.42343711074968357734)
                      / (((((((r * 1.05075007164441684324e-9 + 5.475938084995344946e-4) * r
                      + .0151986665636164571966)
                      * r
                      + .14810397642748007459)
                      * r
                      + .68976733498510000455)
                      * r
                      + 1.6763848301838038494)
                      * r
                      + 2.05319162663775882187)
                      * r
                      + 1.);
         }
         r += -5.;
         return (q < 0 ? -1 : 1)
                  * (((((((r * 2.01033439929228813265e-7 + 2.71155556874348757815e-5) * r
                  + .0012426609473880784386)
                  * r
                  + .026532189526576123093)
                  * r
                  + .29656057182850489123)
                  * r
                  + 1.7848265399172913358)
                  * r
                  + 5.4637849111641143699)
                  * r
                  + 6.6579046435011037772)
                  / (((((((r * 2.04426310338993978564e-15 + 1.4215117583164458887e-7) * r
                  + 1.8463183175100546818e-5)
                  * r
                  + 7.868691311456132591e-4)
                  * r
                  + .0148753612908506148525)
                  * r
                  + .13692988092273580531)
                  * r
                  + .59983220655588793769)
                  * r
                  + 1.);
     }


    /**
    *
    *This method calculates the normal cdf inverse function.
    *<p>
    *Let PHI(x) be the normal cdf.  Suppose that Q calculates
    *1.0 - PHI(x), and that QINV calculates QINV(p) for p in (0.0,.5].
    *Then for p .le. .5, x = PHIINV(p) = -QINV(p).
    *For p .gt. .5, x = PHIINV(p) = QINV(1.0 - p).
    *The formula for approximating QINV is taken from Abramowitz and Stegun,
    *Handbook of Mathematical Functions, Dover, 9th printing,
    *formula 26.2.3, page 933.  The error in x is claimed to
    *be less than 4.5e-4 in absolute value.
    *
    *@param   p    p must lie between 0 and 1.  xnormi returns
    *              the normal cdf inverse evaluated at p.
    *
    *@author Steve Verrill
    *@version .5 --- June 7, 1996
    *
    */
       private static double xnormi(double p) {

          double arg,t,t2,t3,xnum,xden,qinvp,x,pc;

          final double c[] = {2.515517,
          .802853,
          .010328};

          final double d[] = {1.432788,
          .189269,
          .001308};

          if (p <= .5) {

             arg = -2.0*Math.log(p);
             t = Math.sqrt(arg);
             t2 = t*t;
             t3 = t2*t;

             xnum = c[0] + c[1]*t + c[2]*t2;
             xden = 1.0 + d[0]*t + d[1]*t2 + d[2]*t3;
             qinvp = t - xnum/xden;
             x = -qinvp;

             return x;

          }

          else {

             pc = 1.0 - p;
             arg = -2.0*Math.log(pc);
             t = Math.sqrt(arg);
             t2 = t*t;
             t3 = t2*t;

             xnum = c[0] + c[1]*t + c[2]*t2;
             xden = 1.0 + d[0]*t + d[1]*t2 + d[2]*t3;
             x = t - xnum/xden;

             return x;

          }

       }
}
