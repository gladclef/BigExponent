package com.gladcelf.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import com.gladclef.math.BigExponent;

public class BigExponentTest
{
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
			String errorMsg = String.format("Adding %d to %d", pair.firstDouble(), pair.secondDouble());
			Assert.assertEquals(errorMsg, expResult, pair.firstBigExponent().add(pair.secondBigExponent()));
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
			String errorMsg = String.format("Subtracting %d from %d", pair.secondDouble(), pair.firstDouble());
			Assert.assertEquals(errorMsg, expResult, pair.firstBigExponent().subtract(pair.secondBigExponent()));
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
			String errorMsg = String.format("Multiplying %d by %d", pair.firstDouble(), pair.secondDouble());
			Assert.assertEquals(errorMsg, expResult, pair.firstBigExponent().multiply(pair.secondBigExponent()));
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
			String errorMsg = String.format("Dividing %d by %d", pair.firstDouble(), pair.secondDouble());
			Assert.assertEquals(errorMsg, expResult, pair.firstBigExponent().divide(pair.secondBigExponent()));
		}
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
			String errorMsg = String.format("Raising %d to the %d power", pair.firstDouble(), pair.secondDouble());
			Assert.assertEquals(errorMsg, expResult, pair.firstBigExponent().pow(pair.secondBigExponent()));
		}
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
	 * A pair of pairs, containing two Double values and two matching BigExponent values.
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
