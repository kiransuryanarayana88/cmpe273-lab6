package edu.sjsu.cmpe.cache.client;
import com.google.common.hash.Hashing;
import java.util.*;
import java.lang.*;
import java.io.*;

/**
 * CMPE 273- Lab 3
 *          
 * @version November, 2014
 * 
 * Author: Kiran Suryanarayana
 */

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        CacheServiceInterface cache = new DistributedCacheService(
                "http://localhost:3000");
        CacheServiceInterface cache1 = new DistributedCacheService("http://localhost:3001");
        CacheServiceInterface cache2 = new DistributedCacheService("http://localhost:3002");

        List<CacheServiceInterface> server = new ArrayList(3);
        server.add(cache);
        server.add(cache1);
        server.add(cache2);
        
        String [] value = {"0","a","b", "c", "d", "e", "f", "g", "h", "i", "j"};
        
        for(int key=1; key<11; key++) 
        {
            int collection = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(key)), server.size());
            server.get(collection).put(key, value[key]);
            System.out.println(" Insert value - " + value[key] + " with KEY - " + key + " to SERVER - " + collection);
        }

        for(int key=1; key<11; key++) 
        {
            int collection = Hashing.consistentHash(Hashing.md5().hashString(Integer.toString(key)), server.size());
            System.out.println(" Retrieving value - " + server.get(collection).get(key) + " having KEY - " + key+ " from SERVER - "+ collection); 
        }
        

        System.out.println("Existing Cache Client...");
    }
}
