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
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.intellij.psi.PsiType;
import com.intellij.ui.JBColor;

import org.jetbrains.annotations.NotNull;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.text.Format;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;

/**
 * {@link ParameterRule} which can be used for {@code int} values.
 */
public class IntegerParameterRule extends NumericParameterRule
{
    /** Required {@link Format} for an integer in a text field. */
    private static final Format INTEGER_FORMAT = NumberFormat.getIntegerInstance();

    /** Text field holding the current constraint value. */
    private final JFormattedTextField value = new JFormattedTextField(INTEGER_FORMAT);

    /**
     * Create a new {@link IntegerParameterRule}.
     *
     * @param name Name of the parameter for which this rule applies. Cannot be {@code null} and must have a length of
     *             at least 1.
     *
     * @throws IllegalArgumentException if {@code name}'s length is &lt; 1.
     * @throws NullPointerException if {@code name} is {@code null}.
     */
    public IntegerParameterRule(@NotNull String name)
    {
        super(PsiType.INT, name);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.gridwidth = 2;
        constraints.gridx = 4;
        constraints.gridy = 0;
        constraints.weightx = 0.5;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        value.setMinimumSize(new Dimension(100, (int) value.getPreferredSize().getHeight()));
        this.uiComponent.add(value, constraints);

        // TODO: This class should probably not be using the comboBox directly, but how to do this generically?
        numericConstraint.setSelectedItem(NumericConstraint.ANY);
        value.setEnabled(false);
        value.setEditable(false);
        value.setBackground(JBColor.LIGHT_GRAY);

        numericConstraint.addActionListener(e ->
        {
            if (getConstraint().equals(NumericConstraint.ANY))
            {
                value.setEnabled(false);
                value.setEditable(false);
                value.setBackground(JBColor.LIGHT_GRAY);
            }
            else
            {
                value.setEnabled(true);
                value.setEditable(true);
                value.setBackground(JBColor.background());
            }
        });
    }

    @Override
    public boolean isValid()
    {
        return super.isValid() &&
            (getConstraint().equals(NumericConstraint.ANY) || checkedGetValue() != null);
    }

    @NotNull
    @Override
    public ImmutableList<ParameterInitializer> getValidInitializers()
    {
        Preconditions.checkArgument(isValid(), "Must be valid when getting initializers!");

        ImmutableList.Builder<ParameterInitializer> initializers = ImmutableList.builder();
        Integer value = checkedGetValue();
        // TODO: Convert numbers to words!
        switch (getConstraint())
        {
            case ANY:
            {
                initializers.add(
                    new ParameterInitializer(
                        "valid" + getCapitalizedName(),
                        "0"
                    )
                );
                break;
            }
            case LESS_EQUAL:
            {
                initializers.add(
                    new ParameterInitializer(
                        getName() + "LessThan" + String.valueOf(value),
                        String.valueOf(value - 1)
                    )
                );
                initializers.add(
                    new ParameterInitializer(
                        getName() + "EqualTo" + String.valueOf(value),
                        String.valueOf(value)
                    )
                );
                break;
            }
            case LESS:
            {
                initializers.add(
                    new ParameterInitializer(
                        getName() + "LessThan" + String.valueOf(value),
                        String.valueOf(value - 1)
                    )
                );
                break;
            }
            case EQUAL:
            {
                initializers.add(
                    new ParameterInitializer(
                        getName() + "EqualTo" + String.valueOf(value),
                        String.valueOf(value)
                    )
                );
                break;
            }
            case GREATER:
            {
                initializers.add(
                    new ParameterInitializer(
                        getName() + "GreaterThan" + String.valueOf(value),
                        String.valueOf(value + 1)
                    )
                );
                break;
            }
            case GREATER_EQUAL:
            {
                initializers.add(
                    new ParameterInitializer(
                        getName() + "GreaterThan" + String.valueOf(value),
                        String.valueOf(value + 1)
                    )
                );
                initializers.add(
                    new ParameterInitializer(
                        getName() + "EqualTo" + String.valueOf(value),
                        String.valueOf(value)
                    )
                );
                break;
            }
            default:
            {
                throw new IllegalStateException("Unknown constraint!: " + getConstraint());
            }
        }

        return initializers.build();
    }

    @NotNull
    @Override
    public ImmutableMap<ParameterInitializer, Class<? extends Exception>> getInvalidInitializers()
    {
        Preconditions.checkArgument(isValid(), "Must be valid when getting initializers!");

        ImmutableMap.Builder<ParameterInitializer, Class<? extends Exception>> initializers = ImmutableMap.builder();
        Integer value = checkedGetValue();
        // TODO: Convert numbers to words!
        switch (getConstraint())
        {
            case ANY:
            {
                // No invalid initializers for any
                break;
            }
            case LESS_EQUAL:
            {
                initializers.put(
                    new ParameterInitializer(
                        getName() + "GreaterThan" + String.valueOf(value),
                        String.valueOf(value + 1)
                    ),
                    IllegalArgumentException.class
                );
                break;
            }
            case LESS:
            {
                initializers.put(
                    new ParameterInitializer(
                        getName() + "GreaterThan" + String.valueOf(value),
                        String.valueOf(value + 1)
                    ),
                    IllegalArgumentException.class
                );
                initializers.put(
                    new ParameterInitializer(
                        getName() + "EqualTo" + String.valueOf(value),
                        String.valueOf(value)
                    ),
                    IllegalArgumentException.class
                );
                break;
            }
            case EQUAL:
            {
                initializers.put(
                    new ParameterInitializer(
                        getName() + "LessThan" + String.valueOf(value),
                        String.valueOf(value - 1)
                    ),
                    IllegalArgumentException.class
                );
                initializers.put(
                    new ParameterInitializer(
                        getName() + "GreaterThan" + String.valueOf(value),
                        String.valueOf(value + 1)
                    ),
                    IllegalArgumentException.class
                );
                break;
            }
            case GREATER:
            {
                initializers.put(
                    new ParameterInitializer(
                        getName() + "LessThan" + String.valueOf(value),
                        String.valueOf(value - 1)
                    ),
                    IllegalArgumentException.class
                );
                initializers.put(
                    new ParameterInitializer(
                        getName() + "EqualTo" + String.valueOf(value),
                        String.valueOf(value)
                    ),
                    IllegalArgumentException.class
                );
                break;
            }
            case GREATER_EQUAL:
            {
                initializers.put(
                    new ParameterInitializer(
                        getName() + "LessThan" + String.valueOf(value),
                        String.valueOf(value - 1)
                    ),
                    IllegalArgumentException.class
                );
                break;
            }
            default:
            {
                throw new IllegalStateException("Unknown constraint!: " + getConstraint());
            }
        }

        return initializers.build();
    }

    /**
     * Get the current user input constraint value.
     *
     * @return The current user input constraint value, or {@code null} if there is no input, or it is invalid.
     */
    private Integer checkedGetValue()
    {
        Integer result;
        try
        {
            result = Integer.parseInt(value.getText());
        }
        catch (NumberFormatException nfe)
        {
            result = null;
        }
        return result;
    }
}
