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
package com.rtoth.boilerplate;

import com.google.common.base.Preconditions;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an error during test generation.
 */
class TestGenerationException extends Exception
{
    /**
     * Create a new {@link TestGenerationException} using the provided message.
     *
     * @param message Message describing the error. Cannot be {@code null}.
     *
     * @throws NullPointerException if {@code message} is {@code null}.
     */
    TestGenerationException(@NotNull String message)
    {
        super(Preconditions.checkNotNull(message, "message cannot be null."));
    }

    /**
     * Create a new {@link TestGenerationException} using the provided message and cause.
     *
     * @param message Message describing the error. Cannot be {@code null}.
     * @param cause Original cause of the error. Cannot be {@code null}.
     *
     * @throws NullPointerException if any parameter is {@code null}.
     */
    public TestGenerationException(@NotNull String message, @NotNull Throwable cause)
    {
        super(
            Preconditions.checkNotNull(message, "message cannot be null."),
            Preconditions.checkNotNull(cause, "cause cannot be null.")
        );
    }
}
