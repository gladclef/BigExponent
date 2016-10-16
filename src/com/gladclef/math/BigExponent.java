package com.gladclef.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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
	private boolean isFinite = true;
	
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
	 */
	public BigExponent(Double value)
	{
		// sign
		isPositive = value >= 0;
		
		// exponent/NaN
		if (value.isNaN())
		{
			exponent = 0x7ff;
			isNan = true;
		}
		else if (value.isInfinite())
		{
			exponent = 0x7ff;
			isFinite = false;
			if (value.equals(Double.POSITIVE_INFINITY))
			{
				isPositive = true;
			}
			else
			{
				isPositive = false;
			}
		}
		else
		{
			exponent = Math.getExponent(value);
		}
		
		// mantissa
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
	
	public boolean isFinite()
	{
		return isFinite;
	}
	
	public boolean isInfinite()
	{
		return !isFinite;
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
		if (!isPositive)
		{
			doubleRep |= (1l << 63);
		}
		
		// exponent
		doubleRep |= ((exponent + 1023) << 52);
		
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
		// TODO Auto-generated method stub
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
		long longRep = Double.doubleToLongBits(value);
		return longRep & MAX_MANTISSA;
	}
	
	/**
	 * Attempts to normalize the exponents between the given BigExponent instances, so that they are operating within the same range.
	 * <p>
	 * If the spread of exponents is greater than 2047 (0x7ff), then the one with the smaller exponent will be marked as being out of bounds.
	 * <p>
	 * If both values are infinite, then they are both marked out of bounds.
	 * 
	 * @param first The first value to adjust.
	 * @param second The second value to adjust.
	 * @return The normalized BigExponent values, with their exponents adjusted.
	 */
	public static List<NormalizedBigExponent> normalizeExponents(BigExponent first, BigExponent second)
	{
		// check for if both values are infinite
		if (first.isInfinite() && second.isInfinite())
		{
			List<NormalizedBigExponent> retval = new ArrayList<>();
			retval.add(new NormalizedBigExponent(first, first.toDouble(), true, 0));
			retval.add(new NormalizedBigExponent(second, second.toDouble(), true, 0));
			return retval;
		}
		
		// check that the exponents are within a valid range of each other
		if (Math.abs(first.getExponent() - second.getExponent()) > (long) 0x7ff ||
				!first.isInfinite() || !second.isInfinite())
		{
			long firstAdjustment = 0;
			long secondAdjustment = 0;
			double firstVal = first.toDouble();
			double secondVal = second.toDouble();
			boolean firstOOB = true;
			boolean secondOOB = true;
			
			boolean firstIsGreater = first.getExponent() > second.getExponent();
			firstIsGreater = (second.isInfinite() && second.isPositive) ? false : true;
			if (firstIsGreater)
			{
				firstAdjustment = first.getExponent();
				firstVal = toDouble(first.isPositive(), 0, first.getMantissa());
				firstOOB = false;
			}
			else
			{
				secondAdjustment = second.getExponent();
				secondVal = toDouble(second.isPositive(), 0, second.getMantissa());
				secondOOB = false;
			}
			
			List<NormalizedBigExponent> retval = new ArrayList<>();
			retval.add(new NormalizedBigExponent(first, firstVal, firstOOB, firstAdjustment));
			retval.add(new NormalizedBigExponent(second, secondVal, secondOOB, secondAdjustment));
			return retval;
		}
		
		// adjust the exponents and return new NormalizedBigExponents
		long adjustment = first.getExponent() - second.getExponent();
		adjustment = (adjustment == 0x7ff) ? (adjustment / 2l + 1) : adjustment / 2;
		double firstVal = toDouble(first.isPositive(), first.getExponent() - adjustment, first.getMantissa());
		double secondVal = toDouble(second.isPositive(), second.getExponent() - adjustment, second.getMantissa());
		
		List<NormalizedBigExponent> retval = new ArrayList<>();
		retval.add(new NormalizedBigExponent(first, firstVal, false, adjustment));
		retval.add(new NormalizedBigExponent(second, secondVal, false, adjustment));
		return retval;
	}
	
	public static class NormalizedBigExponent
	{
		protected BigExponent source;
		protected double doubleValue;
		protected boolean outOfBounds;
		protected long exponentAdjustment;
		
		/**
		 * @param source The source this was created from.
		 * @param doubleValue The adjusted value of the source.
		 * @param outOfBounds If this exponent is less than 2047 (0x7ff) of the exponent of the BigExponent this is being compared to.
		 * @param exponentAdjustment The adjustment to the doubleValue's exponent.
		 */
		public NormalizedBigExponent(BigExponent source, double doubleValue, boolean outOfBounds, long exponentAdjustment)
		{
			this.source = source;
			this.doubleValue = doubleValue;
			this.outOfBounds = outOfBounds;
			this.exponentAdjustment = exponentAdjustment;
		}
		
		/**
		 * The source this was created from.
		 */
		public BigExponent getSource()
		{
			return source;
		}
		
		/**
		 * The adjusted value of the source.
		 */
		public double getDoubleValue()
		{
			return doubleValue;
		}

		/**
		 * If this exponent is less than 2047 (0x7ff) of the exponent of the BigExponent this is being compared to.
		 */
		public boolean isOutOfBounds()
		{
			return outOfBounds;
		}

		/**
		 * The adjustment to the doubleValue's exponent.
		 */
		public long getExponentAdjustment()
		{
			return exponentAdjustment;
		}
	}
}
