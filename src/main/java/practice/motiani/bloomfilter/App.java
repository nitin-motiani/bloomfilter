package practice.motiani.bloomfilter;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	BloomFilter bloomFilter = new BloomFilter(100000, 4);
    	bloomFilter.insert("Hello World");
    	bloomFilter.insert("Yo matey");
    	bloomFilter.insert("lel lel jewellery");
    	
    	BloomFilter bloomFilter2 = new BloomFilter(100000, 4);
    	bloomFilter2.insert("Hello Motiani");
    	bloomFilter2.insert("Yo matey");
    	bloomFilter2.insert("Lel lel jewellery");
    	
    	BloomFilter unionFilter = bloomFilter.union(bloomFilter2);
    	BloomFilter intersectFilter = bloomFilter.intersection(bloomFilter2);
    	
    	if(intersectFilter.query("Lel lel jewellery"))
    		System.out.println("wow");
    	else
    		System.out.println("makes sense");
    	
    	if(!unionFilter.query("lel lel jewellery"))
    		System.out.println("something is seriously fucked up");
    	else
    		System.out.println("This is k");
    	
    	if(unionFilter.query("lel"))
    		System.out.println("huh");
    	else
    		System.out.println("Seems to work");
    	
    	if(intersectFilter.query("Yo matey"))
    		System.out.println("k");
    	else
    		System.out.println("Such fucking false negative");
    	
    }
}
