package com.gladclef.math;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * A way to perform decimal math when the extra precision of a
 * {@link BigDecimal}'s mantissa value isn't necessary (aka the most important
 * part is the exponent).
 * <p>
 * The exponent is tracked in a signed long, and results in infinity when the
 * long runs out of precision. The mantissa and sign values are still tracked in
 * a double value, and are therefore limited to double precision.
 * <p>
 * TODO use {@link BigInteger} for the exponent when the long runs out of
 * precision.
 * <p>
 * A huge thanks to
 * {@link https://en.wikipedia.org/wiki/Double-precision_floating-point_format}.
 */
public class BigExponent implements Comparable<BigExponent>
{
	/**
	 * The maximum value that the mantissa of a double can hold (not accounting
	 * for NaN representations).
	 */
	public static final long MAX_MANTISSA;
	/**
	 * The maximum value that a BigExponent can represent with a long exponent.
	 */
	public static final BigExponent MAX_VALUE;
	/**
	 * The minimum value that a BigExponent can represent with a long exponent.
	 */
	public static final BigExponent MIN_VALUE;
	
	static
	{
		MAX_MANTISSA = 4503599627370495L; // = 000f ffff ffff ffff;
		MIN_VALUE = new BigExponent(false, Long.MIN_VALUE, MAX_MANTISSA);
		MAX_VALUE = new BigExponent(true, Long.MAX_VALUE, MAX_MANTISSA);
	}
	
	private boolean isPositive;
	private long exponent;
	private long mantissa;
	private boolean isNan = false;
	
	/**
	 * Empty constructor.
	 */
	public BigExponent()
	{
		isPositive = true;
		exponent = 0;
		mantissa = 0;
	}
	
	/**
	 * Create a new BigExponent instance with the given value. The exponent,
	 * sign, and mantissa values will be taken from the value.
	 * 
	 * @param value The value to create this instance with.
	 * @throws IllegalArgumentException If the given value is positive/negative
	 *             infinity.
	 */
	public BigExponent(Double value) throws IllegalArgumentException
	{
		isPositive = value >= 0;
		isPositive = (value != -0.0) ? isPositive : false;
		
		if (value.isInfinite() || value.isNaN())
		{
			if (value.isInfinite())
			{
				throw new IllegalArgumentException(
						String.format("Can't construct a %s from an infinite value.",
								this.getClass().getSimpleName()));
			}
			exponent = 0x7ff;
			isNan = true;
		}
		else
		{
			exponent = Math.getExponent(value);
		}
		
		mantissa = getMantissa(value);
	}
	
	/**
	 * Construct a new instance with the given sing, exponent, and mantissa
	 * values.
	 * 
	 * @param isPositive True if this instance is positive.
	 * @param exponent The exponent of this instance.
	 * @param mantissa The mantissa of this instance.
	 */
	public BigExponent(boolean isPositive, long exponent, long mantissa)
	{
		this.isPositive = isPositive;
		this.exponent = exponent;
		this.mantissa = mantissa;
	}
	
	/**
	 * Copy constructor.
	 */
	protected BigExponent(BigExponent other)
	{
		// TODO
	}
	
	public boolean isPositive()
	{
		return isPositive;
	}
	
	public long getExponent()
	{
		return exponent;
	}
	
	public long getMantissa()
	{
		return mantissa;
	}
	
	public boolean isNaN()
	{
		return isNan;
	}
	
	/**
	 * Converts the value represented by this instance to a double.<br>
	 * This can include positive/negative infinity, and NaN.
	 * <p>
	 * It could be that the returned value is different than if a double was
	 * constructed using the values of {@link #isPositive()},
	 * {@link #getExponent()}, and {@link #getMantissa()} due to the special
	 * representations in a double value.
	 * 
	 * @return The double value of this instance, or positive/negative infinity
	 *         if the value exceeds double precision.
	 */
	public double toDouble()
	{
		// special double values
		if (isNan)
		{
			return Double.NaN;
		}
		if (exponent > 0x7ff || (exponent == 0x7ff && mantissa == MAX_MANTISSA))
		{
			return Double.POSITIVE_INFINITY;
		}
		if (exponent <= -0x7ff)
		{
			return Double.NEGATIVE_INFINITY;
		}
		
		// avoid double representation of positive/negative infinity
		if (exponent == 0x7ff && mantissa == 0)
		{
			return toDouble(isPositive, 0x7fe, MAX_MANTISSA);
		}
		
		return toDouble(isPositive, exponent, mantissa);
	}
	
	public static double toDouble(boolean isPositive, long exponent, long mantissa)
	{
		long doubleRep = 0;
		
		// sign
		if (isPositive)
		{
			doubleRep |= (1l << 63);
		}
		
		// exponent
		doubleRep |= (exponent << 52);
		
		// mantissa
		doubleRep |= mantissa;
		
		return Double.longBitsToDouble(doubleRep);
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
		// check for zero
		
		// normalize exponents
		
		// divide the double representations
		
		// apply new mantissa and exponent values
		
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
	
	/**
	 * @return The mantissa value of a double, including NaN doubles.
	 */
	public static long getMantissa(double value)
	{
		long longRep = Double.doubleToRawLongBits(value);
		return longRep & MAX_MANTISSA;
	}
}
