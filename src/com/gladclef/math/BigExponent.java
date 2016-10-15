package com.gladclef.math;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * A way to perform decimal math when the extra precision of a
 * {@link BigDecimal}'s mantissa value isn't necessary (aka the most important
 * part is the exponent).
 * <p>
 * The exponent is tracked in an unsigned long when possible, or in a
 * {@link BigInteger} otherwise. The mantissa and sign values are still tracked
 * in a double value, and are therefore limited to double precision.
 */
public class BigExponent implements Comparable<BigExponent>
{
	/**
	 * Empty constructor.
	 */
	public BigExponent()
	{
		// do nothing here
	}
	
	/**
	 * Create a new BigExponent instance with the given value. The exponent,
	 * sign, and mantissa values will be taken from the value.
	 * 
	 * @param value The value to create this instance with.
	 */
	public BigExponent(Double value)
	{
		// TODO
	}
	
	/**
	 * Copy constructor.
	 */
	protected BigExponent(BigExponent other)
	{
		// TODO
	}
	
	/**
	 * @param other The value to add to this instance.
	 * @return A new resulting instance.
	 */
	public BigExponent add(BigExponent other)
	{
		// TODO
		return null;
	}
	
	/**
	 * @param other The value to subtract from this instance.
	 * @return A new resulting instance.
	 */
	public BigExponent subtract(BigExponent other)
	{
		// TODO
		return null;
	}
	
	/**
	 * @param other The value to multiply this instance by.
	 * @return A new resulting instance.
	 */
	public BigExponent multiply(BigExponent other)
	{
		// TODO
		return null;
	}
	
	/**
	 * @param other The value to divide this instance by.
	 * @return A new resulting instance.
	 */
	public BigExponent divide(BigExponent other)
	{
		// TODO
		return null;
	}
	
	/**
	 * @param other The value to divide this instance by.
	 * @return A new resulting instance.
	 */
	public BigExponent pow(BigExponent other)
	{
		// TODO
		return null;
	}
	
	/**
	 * @param other The value to compare to this instance.
	 * @return The minimum value, either this instance or the other.
	 */
	public BigExponent min(BigExponent other)
	{
		// TODO
		return null;
	}

	/**
	 * @param other The value to compare to this instance.
	 * @return The maximum value, either this instance or the other.
	 */
	public BigExponent max(BigExponent other)
	{
		// TODO
		return null;
	}

	/**
	 * @return A copy of this instance, with the opposite sign value.
	 */
	public BigExponent negate()
	{
		// TODO
		return null;
	}

	@Override
	public int compareTo(BigExponent o)
	{
		// TODO
		return 0;
	}
	
	@Override
	public String toString()
	{
		// TODO
		return super.toString();
	}
}
