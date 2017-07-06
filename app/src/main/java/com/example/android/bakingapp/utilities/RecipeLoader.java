package com.example.android.bakingapp.utilities;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.example.android.bakingapp.domain.Recipe;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by mateus on 15/06/17.
 */

public class RecipeLoader {

    private Context context;
    private OnLoadListener listener;

    private static RequestQueue requestQueue;

    private static final String URL = "https://go.udacity.com/android-baking-app-json";

    public RecipeLoader(Context context, OnLoadListener listener){
        this.context = context;
        this.listener = listener;
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(context);
        }
    }

    public void load(){

        JsonArrayRequest request = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(listener != null && response != null){
                    listener.onLoaded(JsonUtils.fetchRecipeList(response));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(listener != null){
                    listener.onError(error.getMessage());
                }
            }
        });
        requestQueue.add(request);
    }

    public ArrayList<Recipe> loadSync(){
        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JsonArrayRequest request = new JsonArrayRequest(URL, future, future);
        requestQueue.add(request);
        try {
            JSONArray response = future.get();
            return JsonUtils.fetchRecipeList(response);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface OnLoadListener{
        void onLoaded(ArrayList<Recipe> data);
        void onError(String error);
    }

}
