package edu.sjsu.cmpe.cache.client;

import com.mashape.unirest.http.Unirest;
import java.lang.*;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println(" -- Starting Cache Client --");
        CRDTClient crdtClient = new CRDTClient();

        /*First HTTP PUT call to store “a” to key 1 => server */

        boolean result = crdtClient.put(1, "a");
        System.out.println(" Result:  " + result);
        Thread.sleep(30*1000);
        System.out.println("Step 1: put(1 => a) , sleeping 30s.");


        /* Now we will update key 1 value to “b” */

        crdtClient.put(1, "b");
        Thread.sleep(30*1000);
        System.out.println("Step 2: put(1 => b), sleeping for 30s");


       /*  Finally retrieve key “1” value. */

        String value = crdtClient.get(1);
        System.out.println("Step 3: get(1) => " + value);

        System.out.println(" -- Exiting Cache Client --");
        Unirest.shutdown();

    }

}
