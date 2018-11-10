package com.gladclef.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import com.gladclef.math.BigExponent.NormalizedBigExponent;

public class BigExponentTest
{
	@Test
	public void toDouble_staticMethod_SimpleTest()
	{
		assertDoubleEquality(1.0000000000000002, BigExponent.toDouble(true, 0, 1));
	}
	
	@Test
	public void getMantissaTest()
	{
		Assert.assertEquals(0, BigExponent.getMantissa(0));
		Assert.assertEquals(0, BigExponent.getMantissa(2.0));
		Assert.assertEquals((1l << 51), BigExponent.getMantissa(Double.NaN));
		Assert.assertEquals(0, BigExponent.getMantissa(Double.POSITIVE_INFINITY));
		Assert.assertEquals(0, BigExponent.getMantissa(Double.NEGATIVE_INFINITY));
		Assert.assertEquals(1, BigExponent.getMantissa(1.0000000000000002));
		Assert.assertEquals(2, BigExponent.getMantissa(1.0000000000000004));
	}
	
	@Test
	public void toDoubleTest()
	{
		for (PairContainer pair : createTestingPairs(100))
		{
			assertDoubleEquality((double) pair.firstDouble(), pair.firstBigExponent().toDouble());
			assertDoubleEquality((double) pair.secondDouble(), pair.secondBigExponent().toDouble());
		}
	}
	
	@Test
	public void toDoubleTest_specialDoubleRepresentations()
	{
		assertDoubleEquality(0.0, new BigExponent(0.0).toDouble());
		
		assertDoubleEquality(Double.POSITIVE_INFINITY,
				new BigExponent(Double.POSITIVE_INFINITY).toDouble());
		assertDoubleEquality(Double.NEGATIVE_INFINITY,
				new BigExponent(Double.NEGATIVE_INFINITY).toDouble());
		assertDoubleEquality(Double.NaN, new BigExponent(Double.NaN).toDouble());
		
		Double maybeInfinite = new BigExponent(true, 0x7ff, 0).toDouble();
		Assert.assertFalse("positive infinity accidentally encountered",
				maybeInfinite.isInfinite());
		maybeInfinite = new BigExponent(false, 0x7ff, 0).toDouble();
		Assert.assertFalse("negative infinity accidentally encountered",
				maybeInfinite.isInfinite());
	}
	
	@Test
	public void simpleDecimalAdditionTest()
	{
		for (PairContainer pair : createTestingPairs(100))
		{
			Double result = pair.firstDouble() + pair.secondDouble();
			if (result.equals(Double.NEGATIVE_INFINITY) || result.equals(Double.NEGATIVE_INFINITY))
			{
				// invalid test
				continue;
			}
			BigExponent expResult = new BigExponent(result);
			String errorMsg = String.format("Adding %d to %d", pair.firstDouble(),
					pair.secondDouble());
			Assert.assertEquals(errorMsg, expResult,
					pair.firstBigExponent().add(pair.secondBigExponent()));
		}
	}
	
	public void largeExponentDecimalAdditionTest()
	{
		for (PairContainer pair : createTestingPairs(100))
		{
			Double result = pair.firstDouble() + pair.secondDouble();
			if (result.equals(Double.NEGATIVE_INFINITY) || result.equals(Double.NEGATIVE_INFINITY))
			{
				// invalid test
				continue;
			}
			for (int i = 0; i < 2; i++)
			{
				long base = Long.MAX_VALUE;
				if (i == 1)
				{
					base *= -1;
				}
				BigExponent expResult = new BigExponent(result >= 0,
						Math.getExponent(result) + base, BigExponent.getMantissa(result));
				BigExponent firstBigExponent = pair.firstBigExponent();
				firstBigExponent = new BigExponent(firstBigExponent.isPositive(),
						firstBigExponent.getExponent() + base, firstBigExponent.getMantissa());
				BigExponent secondBigExponent = pair.secondBigExponent();
				secondBigExponent = new BigExponent(secondBigExponent.isPositive(),
						secondBigExponent.getExponent() + base, secondBigExponent.getMantissa());
				String errorMsg = String.format("Adding %d to %d", pair.firstDouble(),
						pair.secondDouble());
				Assert.assertEquals(errorMsg, expResult, firstBigExponent.add(secondBigExponent));
			}
		}
	}
	
	@Test
	public void simpleDecimalSubtractionTest()
	{
		for (PairContainer pair : createTestingPairs(100))
		{
			Double result = pair.secondDouble() - pair.firstDouble();
			if (result.equals(Double.NEGATIVE_INFINITY) || result.equals(Double.NEGATIVE_INFINITY))
			{
				// invalid test
				continue;
			}
			BigExponent expResult = new BigExponent(result);
			String errorMsg = String.format("Subtracting %d from %d", pair.secondDouble(),
					pair.firstDouble());
			Assert.assertEquals(errorMsg, expResult,
					pair.firstBigExponent().subtract(pair.secondBigExponent()));
		}
	}
	
	@Test
	public void simpleDecimalMultiplicationTest()
	{
		for (PairContainer pair : createTestingPairs(100))
		{
			Double result = pair.firstDouble() * pair.secondDouble();
			if (result.equals(Double.NEGATIVE_INFINITY) || result.equals(Double.NEGATIVE_INFINITY))
			{
				// invalid test
				continue;
			}
			BigExponent expResult = new BigExponent(result);
			String errorMsg = String.format("Multiplying %d by %d", pair.firstDouble(),
					pair.secondDouble());
			Assert.assertEquals(errorMsg, expResult,
					pair.firstBigExponent().multiply(pair.secondBigExponent()));
		}
	}
	
	@Test
	public void simpleDecimalDivisionTest()
	{
		for (PairContainer pair : createTestingPairs(100))
		{
			if (pair.secondDouble() == 0)
			{
				// invalid test
				continue;
			}
			Double result = pair.firstDouble() / pair.secondDouble();
			if (result.equals(Double.NEGATIVE_INFINITY) || result.equals(Double.NEGATIVE_INFINITY))
			{
				// invalid test
				continue;
			}
			BigExponent expResult = new BigExponent(result);
			String errorMsg = String.format("Dividing %d by %d", pair.firstDouble(),
					pair.secondDouble());
			Assert.assertEquals(errorMsg, expResult,
					pair.firstBigExponent().divide(pair.secondBigExponent()));
		}
	}
	
	@Test
	public void addBorderTest()
	{
		BigExponent first = new BigExponent(Double.MAX_VALUE);
		BigExponent plusOne = new BigExponent(true, Double.MAX_EXPONENT, 1);
		BigExponent expected = new BigExponent(true, Double.MAX_EXPONENT + 1, 1);
		BigExponent result = first.add(plusOne);
		Assert.assertEquals("addition failed when adding 2^11 to the largest double value",
				expected, result);
	}
	
	public void subtractBorderTest()
	{
		// TODO
	}
	
	public void multiplyBorderTest()
	{
		// TODO
	}
	
	public void divideBorderTest()
	{
		// TODO
	}
	
	public void powBorderTest()
	{
		// TODO
	}
	
	public void addToMaxTest()
	{
		// TODO
	}
	
	public void subtractFromMinTest()
	{
		// TODO
	}
	
	public void divideByZeroTest()
	{
		// TODO
	}
	
	public void multiplyMinTest()
	{
		// TODO
	}
	
	public void multiplyMaxTest()
	{
		// TODO
	}
	
	public void powMaxTest()
	{
		// TODO
	}
	
	public void powMinTest()
	{
		// TODO
	}
	
	public void addSpecialsTest()
	{
		// TODO math with infinity and NaN
	}
	
	public void subtractSpecialsTest()
	{
		// TODO math with infinity and NaN
	}
	
	public void multiplySpecialsTest()
	{
		// TODO math with infinity and NaN
	}
	
	public void divideSpecialsTest()
	{
		// TODO math with infinity and NaN
	}
	
	public void powSpecialsTest()
	{
		// TODO math with infinity and NaN
	}
	
	@Test
	public void initializeWithInitinityTest()
	{
		Assert.assertTrue("Should be infinite but isn't.",
				new BigExponent(Double.POSITIVE_INFINITY).isInfinite());
		Assert.assertTrue("Should be infinite but isn't.",
				new BigExponent(Double.NEGATIVE_INFINITY).isInfinite());
		Assert.assertTrue("Should be positive but isn't.",
				new BigExponent(Double.POSITIVE_INFINITY).isPositive());
		Assert.assertFalse("Should be negative but isn't.",
				new BigExponent(Double.NEGATIVE_INFINITY).isPositive());
	}
	
	@Test
	public void normalizeTest()
	{
		long spread = 0x3ff;
		long firstExp = spread + Long.MAX_VALUE / 2;
		long secondExp = firstExp - spread;
		BigExponent first = new BigExponent(true, firstExp, 0);
		BigExponent second = new BigExponent(true, secondExp, 0);
		List<NormalizedBigExponent> normalizedValues = BigExponent.normalizeExponents(first,
				second);
				
		// sanity check
		Assert.assertFalse("should have been in bounds, but was out of bounds",
				normalizedValues.get(0).isOutOfBounds());
		Assert.assertFalse("should have been in bounds, but was out of bounds",
				normalizedValues.get(1).isOutOfBounds());
				
		// ok, now check the exponent adjustment
		double firstDouble = normalizedValues.get(0).getDoubleValue();
		double secondDouble = normalizedValues.get(1).getDoubleValue();
		Assert.assertEquals("exponent normalization not applied equaly to both values", spread,
				Math.getExponent(firstDouble) - Math.getExponent(secondDouble));
		long center = Math.abs(Math.getExponent(firstDouble) - Math.getExponent(secondDouble)) / 2;
		center += Math.min(Math.getExponent(firstDouble), Math.getExponent(secondDouble));
		Assert.assertEquals("exponents not centered around zero", 0.0, center, 1.0);
	}
	
	@Test
	public void normalize_SpreadTooWideTest()
	{
		BigExponent first = new BigExponent(true, 0x7ff + 1, 0);
		BigExponent second = new BigExponent(true, 0, 0);
		List<NormalizedBigExponent> normalizedValues = BigExponent.normalizeExponents(first,
				second);
				
		Assert.assertFalse("should have been in bounds, but was out of bounds",
				normalizedValues.get(0).isOutOfBounds());
		Assert.assertTrue("should have been out of bounds, but was in bounds",
				normalizedValues.get(1).isOutOfBounds());
	}
	
	@Test
	public void normalize_WidestSpreadTest()
	{
		long base = Long.MAX_VALUE / 2;
		BigExponent first = new BigExponent(true, 0x7ff + base, 0);
		BigExponent second = new BigExponent(true, 0 + base, 0);
		List<NormalizedBigExponent> normalizedValues = BigExponent.normalizeExponents(first,
				second);
				
		Assert.assertFalse("should have been in bounds, but was out of bounds",
				normalizedValues.get(0).isOutOfBounds());
		Assert.assertFalse("should have been in bounds, but was out of bounds",
				normalizedValues.get(1).isOutOfBounds());
		Assert.assertEquals(0x7ff, Math.getExponent(normalizedValues.get(0).getDoubleValue()));
		Assert.assertEquals(0, Math.getExponent(normalizedValues.get(1).getDoubleValue()));
	}
	
	@Test
	public void simpleDecimalPowerTest()
	{
		for (PairContainer pair : createTestingPairs(100))
		{
			Double result = Math.pow(pair.firstDouble(), pair.secondDouble());
			if (result.equals(Double.NEGATIVE_INFINITY) || result.equals(Double.NEGATIVE_INFINITY))
			{
				// invalid test
				continue;
			}
			BigExponent expResult = new BigExponent(result);
			String errorMsg = String.format("Raising %d to the %d power", pair.firstDouble(),
					pair.secondDouble());
			Assert.assertEquals(errorMsg, expResult,
					pair.firstBigExponent().pow(pair.secondBigExponent()));
		}
	}
	
	/**
	 * Verifies that the given double are exactly equal, bit for bit.
	 * <p>
	 * Includes "nice" error message if the assertion fails.
	 */
	private static void assertDoubleEquality(double expected, double actual)
	{
		long expectedLong = Double.doubleToLongBits(expected);
		long actualLong = Double.doubleToLongBits(actual);
		Assert.assertEquals("Expected " + expected + " but was " + actual, expectedLong,
				actualLong);
	}
	
	/**
	 * Creates count number of PairContainers with random values.
	 */
	public static List<PairContainer> createTestingPairs(int count)
	{
		List<PairContainer> retval = new ArrayList<>();
		
		for (int i = 0; i < count; i++)
		{
			retval.add(new PairContainer());
		}
		
		return retval;
	}
	
	/**
	 * A pair of pairs, containing two Double values and two matching
	 * BigExponent values.
	 */
	public static class PairContainer
	{
		protected List<Double> doubleVals = new ArrayList<>();
		protected List<BigExponent> bigExponentVals = new ArrayList<>();
		
		protected static Random rand = new Random();
		
		/**
		 * Populates the values of this container with random values.
		 */
		public PairContainer()
		{
			this(rand.nextDouble(), rand.nextDouble());
		}
		
		public PairContainer(Double firstD, Double secondD)
		{
			this.doubleVals.add(firstD);
			this.doubleVals.add(secondD);
			this.bigExponentVals.add(new BigExponent(firstD));
			this.bigExponentVals.add(new BigExponent(secondD));
		}
		
		public Double firstDouble()
		{
			return doubleVals.get(0);
		}
		
		public Double secondDouble()
		{
			return doubleVals.get(1);
		}
		
		public BigExponent firstBigExponent()
		{
			return bigExponentVals.get(0);
		}
		
		public BigExponent secondBigExponent()
		{
			return bigExponentVals.get(1);
		}
		
		/**
		 * @return All Double values of this instance, in order.
		 */
		public List<Double> getDoubleValues()
		{
			return new ArrayList<>(doubleVals);
		}
		
		/**
		 * @return All BigExponent values of this instance, in order.
		 */
		public List<BigExponent> getBigExponentValues()
		{
			return new ArrayList<>(bigExponentVals);
		}
		
		/**
		 * Get the Double value and BigExponent value for the given index.
		 * 
		 * @param index The index to get the value for. Should be either 0 or 1.
		 * @return a list with one Double and one BigExponent value
		 */
		public List<Object> getPair(int index)
		{
			if (index < 0 || index >= doubleVals.size())
			{
				throw new IllegalStateException(String.format(
						"Index value must be between 0 and %d, inclusive.", doubleVals.size() - 1));
			}
			List<Object> retval = new ArrayList<>();
			retval.add(doubleVals.get(index));
			retval.add(bigExponentVals.get(index));
			return retval;
		}
	}
}
