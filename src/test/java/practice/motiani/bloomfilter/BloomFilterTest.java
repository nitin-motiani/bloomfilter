package practice.motiani.bloomfilter;

import static org.junit.Assert.*;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

public class BloomFilterTest {
	
	private static final int bucketSize = 100000;
	private static final int hashCount = 7;
	private static final int numEntries = 10000;
	private static final int stringSize = 20;
	
	
	private BloomFilter bloomFilter1;
	private BloomFilter bloomFilter2;
	String[] randomStrings;
	boolean[] presentInFirstFilter;
	boolean[] presentInSecondFilter;
	
	@Before
	public void setup()
	{
		System.out.println("Setting up");
		bloomFilter1 = new BloomFilter(bucketSize, hashCount);
		bloomFilter2 = new BloomFilter(bucketSize, hashCount);
		
		randomStrings = new String[numEntries];
		presentInFirstFilter = new boolean[numEntries];
		presentInSecondFilter = new boolean[numEntries];
		
    	for(int i = 0; i < numEntries; i++)
    	{
    		randomStrings[i] = RandomStringUtils.randomAlphanumeric(stringSize);
    	}
    	
    	Random randNum = new Random();
    	for(int i = 0; i < numEntries; i++)
    	{
    		int result1, result2;
    		result1 = randNum.nextInt(2);
    		result2 = randNum.nextInt(2);
    		
    		presentInFirstFilter[i] = (result1 == 0);
    		presentInSecondFilter[i] = (result2 == 0);
    		
    		if(presentInFirstFilter[i])
    			bloomFilter1.insert(randomStrings[i]);
    		
    		if(presentInSecondFilter[i])
    			bloomFilter2.insert(randomStrings[i]);
    	}
	}
	
	private int testFilter(final BloomFilter bloomFilter, final boolean[] presentInFilter)
	{
		int falsePositives = 0;
		for(int i = 0; i < numEntries; i++)
		{
			if(presentInFilter[i])
				assertTrue(bloomFilter.query(randomStrings[i]));
			else
			{
				if(bloomFilter.query(randomStrings[i]))
					falsePositives++;
			}
		}
		
		return falsePositives;
	}

	@Test
	public void testFilters() 
	{
		int falsePositives = testFilter(bloomFilter1, presentInFirstFilter);
		System.out.println("False positives in first filter : " + falsePositives);
		
		falsePositives = testFilter(bloomFilter2, presentInSecondFilter);
		System.out.println("False positives in second filter : " + falsePositives);
	}

	/*@Test
	public void testFilter2()
	{
		
	}*/
}
