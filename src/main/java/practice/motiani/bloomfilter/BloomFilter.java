package practice.motiani.bloomfilter;

import java.util.BitSet;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.math.IntMath;

public class BloomFilter {
	private int bucketSize;
	private int hashCount;
	private BitSet bitSet;
	
	public BloomFilter(int bucketSize, int hashCount) {
		this.bucketSize = bucketSize;
		this.hashCount = hashCount;
		this.bitSet = new BitSet(bucketSize);
	}
	
	public BloomFilter(BloomFilter that)
	{
		this.bucketSize = that.bucketSize;
		this.hashCount = that.hashCount;
		this.bitSet = (BitSet) that.bitSet.clone();
	}
	
	private long[] murmurHash(String key)
	{
		HashFunction hashFunction0 = Hashing.murmur3_128(0);
		long hash0 = hashFunction0.hashString(key, Charsets.UTF_8).asLong();
		
		HashFunction hashFunction1 = Hashing.murmur3_128((int)hash0);
		long hash1 = hashFunction1.hashString(key, Charsets.UTF_8).asLong();
		
		long[] hashes = {hash0, hash1};
		return hashes;
		
	}
	
	private int[] bucketIndices(String key)
	{
		long[] hashes = murmurHash(key);
		long hash0 = hashes[0];
		long hash1 = hashes[1];
		
		int[] indices = new int[hashCount];
		for(int i = 1; i <= hashCount; i++)
		{
			long index = (hash0 + i * hash1);
			indices[i - 1] = IntMath.mod((int)index, bucketSize);
		}
		
		return indices;
	}
	
	public void insert(String key)
	{
		int[] bitIndices = bucketIndices(key);
		for(int bitIndex : bitIndices)
		{
			bitSet.set(bitIndex);
		}
	}
	
	public boolean query(String key)
	{
		int[] bitIndices = bucketIndices(key);
		for(int bitIndex : bitIndices)
		{
			if(!bitSet.get(bitIndex))
				return false;
		}
		
		return true;
	}
	
	public BloomFilter union(BloomFilter thatFilter) throws Exception
	{
		if(this.bucketSize != thatFilter.bucketSize || this.hashCount != thatFilter.hashCount)
			throw new Exception("Can't union the two filters.");
		
		BloomFilter unionFilter = new BloomFilter(this);
		unionFilter.bitSet.or(thatFilter.bitSet);
		
		return unionFilter;
	}
	
	public BloomFilter intersection(BloomFilter thatFilter) throws Exception
	{
		if(this.bucketSize != thatFilter.bucketSize || this.hashCount != thatFilter.hashCount)
			throw new Exception("Can't intersect the two filters.");
		
		BloomFilter intersectFilter = new BloomFilter(this);
		intersectFilter.bitSet.and(thatFilter.bitSet);
		
		return intersectFilter;
	}
	
}
