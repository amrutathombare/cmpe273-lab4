package edu.sjsu.cmpe.cache.client;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.concurrent.Future;

/**
 * Distributed cache service
 * 
 */
 
public class DistributedCacheService {
    private final String cacheServerUrl;
    private CRDTClient callback;
    public DistributedCacheService(String serverUrl) {
        this.cacheServerUrl = serverUrl;
    }
    public DistributedCacheService(String serverUrl, CRDTClient callbk) {
        this.cacheServerUrl = serverUrl;
        this.callback = callbk;
    }

    public String get(long key) {
        Future<HttpResponse<JsonNode>> future = Unirest.get(this.cacheServerUrl + "/cache/{key}")
                .header("accept", "application/json")
                .routeParam("key", Long.toString(key))
                .asJsonAsync(new Callback<JsonNode>() {
                    public void failed(UnirestException e) {
                        callback.getFailed(e);
                    }
                    public void completed(HttpResponse<JsonNode> response) {
                        callback.getCompleted(response, cacheServerUrl);
                    }
                    public void cancelled() {
                        System.out.println("The request has been cancelled");
                    }

                });

        return null;
    }

    /**
     * @see edu.sjsu.cmpe.cache.client.DistributedCacheService#put(long,
     *      java.lang.String)
     */
        public void put(long key, String value) {
        Future<HttpResponse<JsonNode>> future = Unirest.put(this.cacheServerUrl + "/cache/{key}/{value}")
                .header("accept", "application/json")
                .routeParam("key", Long.toString(key))
                .routeParam("value", value)
                .asJsonAsync(new Callback<JsonNode>() {

                    public void failed(UnirestException e) {
                        callback.putFailed(e);
                    }

                    public void completed(HttpResponse<JsonNode> response) {
                        callback.putCompleted(response, cacheServerUrl);
                    }

                    public void cancelled() {
                        System.out.println("The request has been cancelled");
                    }

                });
    }


    public void delete(long key) {
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest
                    .delete(this.cacheServerUrl + "/cache/{key}")
                    .header("accept", "application/json")
                    .routeParam("key", Long.toString(key))
                    .asJson();
        } catch (UnirestException e) {
            System.err.println(e);
        }

        System.out.println("response is " + response);

        if (response == null || response.getCode() != 204) {
            System.out.println("Failed to delete from the cache.");
        } else {
            System.out.println("Deleted " + key + " from " + this.cacheServerUrl);
        }

    }
}
