/*
 * LensKit, an open source recommender systems toolkit.
 * Copyright 2010-2014 LensKit Contributors.  See CONTRIBUTORS.md.
 * Work on LensKit has been funded by the National Science Foundation under
 * grants IIS 05-34939, 08-08692, 08-12148, and 10-17697.
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package org.lenskit.specs.data;

import org.lenskit.specs.AbstractSpec;

import javax.annotation.Nonnull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Specification for a preference domain.
 */
public class PrefDomainSpec extends AbstractSpec {
    private double minimum;
    private double maximum;
    private double precision;

    public double getMinimum() {
        return minimum;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    public double getMaximum() {
        return maximum;
    }

    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }

    public double getPrecision() {
        return precision;
    }

    public void setPrecision(double precision) {
        this.precision = precision;
    }

    private static Pattern specRE =
            Pattern.compile("\\s*\\[\\s*((?:\\d*\\.)?\\d+)\\s*,\\s*((?:\\d*\\.)?\\d+)\\s*\\]\\s*(?:/\\s*((?:\\d*\\.)?\\d+))?\\s*");

    /**
     * Parse a preference domain from a string specification.
     * <p>
     * Continuous preference domains are specified as {@code [min, max]}; discrete domains
     * as {@code min:max[/prec/}.  For example, a 0.5-5.0 half-star rating scale is represented
     * as {@code [0.5, 5.0]/0.5}.
     *
     * @param spec The string specifying the preference domain.
     * @return The preference domain represented by {@code spec}.
     * @throws IllegalArgumentException if {@code spec} is not a valid domain specification.
     */
    @Nonnull
    public static PrefDomainSpec fromString(@Nonnull String spec) {
        Matcher m = specRE.matcher(spec);
        if (!m.matches()) {
            throw new IllegalArgumentException("invalid domain specification");
        }
        double min = Double.parseDouble(m.group(1));
        double max = Double.parseDouble(m.group(2));
        String precs = m.group(3);
        PrefDomainSpec pds = new PrefDomainSpec();
        pds.setMinimum(min);
        pds.setMaximum(max);
        if (precs != null) {
            double prec = Double.parseDouble(precs);
            pds.setPrecision(prec);
        }
        return pds;
    }
}
