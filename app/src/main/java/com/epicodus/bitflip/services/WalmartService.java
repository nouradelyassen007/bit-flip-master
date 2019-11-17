package com.epicodus.bitflip.services;

import android.util.Log;

import com.epicodus.bitflip.Constants;
import com.epicodus.bitflip.model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by DroAlvarez on 12/1/16.
 */

public class WalmartService {

    public static void findItems(String search, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.API_BASE_URL + Constants.SEARCH_URL_ADDON).newBuilder();
        urlBuilder.addQueryParameter(Constants.SEARCH_QUERY_PARAMETER, search);
        urlBuilder.addQueryParameter(Constants.FORMAT_QUERY_PARAMETER, Constants.FORMAT_QUERY_ANSWER);
        urlBuilder.addQueryParameter(Constants.API_KEY_QUERY_PARAMETER, Constants.API_KEY);
        String url = urlBuilder.build().toString();

        Request request= new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Item> processSearchResults(Response response) {
        ArrayList<Item> items = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            if(response.isSuccessful()) {
                JSONObject walmartJSON = new JSONObject(jsonData);
                String category = walmartJSON.getString("query");
                JSONArray itemsJSON = walmartJSON.getJSONArray("items");
                for(int i = 0; i < itemsJSON.length(); i++) {
                    JSONObject itemJSON = itemsJSON.getJSONObject(i);
                    String name = itemJSON.getString("name");
                    String price = itemJSON.getString("salePrice");
                    String imageUrl = itemJSON.getString("thumbnailImage");
                    String description = "";
                    Item item = new Item(category, name, description, price, imageUrl, "", "");
                    items.add(item);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return items;
    }
}
