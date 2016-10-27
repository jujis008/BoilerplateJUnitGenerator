/*
 * Copyright (c) 2016 Robert Toth
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.rtoth.boilerplate.parameters;

import com.google.common.base.Preconditions;
import com.intellij.psi.PsiType;

import org.jetbrains.annotations.NotNull;

/**
 * Contains functionality common to all {@link ParameterRule}s.
 */
abstract class AbstractParameterRule implements ParameterRule
{
    /** {@link PsiType} of the parameter for which this rule applies. */
    private final PsiType type;

    /** Name of the parameter for which this rule applies. */
    private final String name;

    /**
     * Create a new {@link AbstractParameterRule}.
     *
     * @param type {@link PsiType} of the parameter for which this rule applies. Cannot be {@code null}.
     * @param name Name of the parameter for which this rule applies. Cannot be {@code null} and must have a length of
     *             at least 1.
     *
     * @throws IllegalArgumentException if {@code name}'s length is &lt; 1.
     * @throws NullPointerException if any parameter is {@code null}.
     */
    AbstractParameterRule(@NotNull PsiType type, @NotNull String name)
    {
        this.type = Preconditions.checkNotNull(type, "type cannot be null.");
        this.name = Preconditions.checkNotNull(name, "name cannot be null.");
        Preconditions.checkArgument(name.length() >= 1, "name must be of length >= 1");
    }

    @NotNull
    @Override
    public PsiType getType()
    {
        return type;
    }

    @NotNull
    @Override
    public String getName()
    {
        return name;
    }

    /**
     * Get a version of {@link #getName()} with the first letter capitalized.
     *
     * @return {@link #getName()} with the first letter capitalized. Never {@code null}.
     */
    @NotNull
    String getCapitalizedName()
    {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
