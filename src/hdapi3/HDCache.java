package hdapi3;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class HDCache {
	private static final int MAX_ENTRIES = 50000;
	private static final HDCache INSTANCE = new HDCache();
	private static Cache<String, Object> HDCACHED_OBJECTS;

	static {
		INSTANCE.initialize();
	}
	private void initialize() {
		// Any one off inits go here.
		HDCACHED_OBJECTS = CacheBuilder.newBuilder().maximumSize(MAX_ENTRIES).build();
	}
	
	public static HDCache getInstance() {
		return INSTANCE;
	}

	public HDCache() {
	}
	
	public void put(String key, Object value) {
		HDCACHED_OBJECTS.put(key, value);
	}
	
	public Object get(String key) {
		Object reply;
		reply = HDCACHED_OBJECTS.getIfPresent(key);
		return reply;
	}
	
	public static void main(String[] args) throws Exception{
		final HDCache myCache = new HDCache();
		Thread t1 = new Thread() {
			@Override
			public void run() {
				myCache.put("k1", "v1");
				myCache.put("k2", "v2");
				myCache.put("k3", "v3");
			}
		};
		Thread t2 = new Thread() {
			@Override
			public void run() {
				myCache.put("k4", "v4");
				myCache.put("k5", "v5");
				myCache.put("k6", "v6");
			}
		};
		Thread t3 = new Thread() {
			@Override
			public void run() {
				myCache.put("k7", "v7");
				myCache.put("k8", "v8");
				myCache.put("k9", "v9");
			}
		};
		t3.start();
		t2.start();
		t1.start();
		while (t1.isAlive() || t2.isAlive() || t3.isAlive()) {
			Thread.sleep(3000);
		}
		System.out.println(myCache.get("k1"));
		System.out.println(myCache.get("k2"));
		System.out.println(myCache.get("k3"));
		System.out.println(myCache.get("k4"));
		System.out.println(myCache.get("k5"));
		System.out.println(myCache.get("k6"));
		System.out.println(myCache.get("k7"));
		System.out.println(myCache.get("k8"));
		System.out.println(myCache.get("k9"));
		
	}
}
