package com.example.testroom;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.testroom.room.DatabaseClient;
import com.example.testroom.room.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;


public class MainActivity extends AppCompatActivity {


    private static String FETCHURL = "https://api.androidhive.info/json/shimmer/menu.php";
    List<Repo> recipes;
    private RecyclerView recyclerview;
    private ArrayList<Repo> arrayList;
    private CustomRecyclerview adapter;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pb = findViewById(R.id.pb);
        pb.setVisibility(View.GONE);

        recyclerview = findViewById(R.id.recyclerview);
        arrayList = new ArrayList<>();
        adapter = new CustomRecyclerview(this, arrayList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.setAdapter(adapter);


        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting() && arrayList != null) {
            fetchfromServer();
        } else {


            fetchfromRoom();
        }


    }




    private void fetchfromRoom() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {


                List<Recipe> recipeList = DatabaseClient.getInstance(MainActivity.this).getAppDatabase().recipeDao().getAll();
                arrayList.clear();
                for (Recipe recipe: recipeList) {
                    Repo repo = new Repo(recipe.getId(),             recipe.getName(),
                            recipe.getDescription(),
                            recipe.getPrice(),
                            recipe.getThumbnail(),
                            recipe.getChef(),
                            recipe.getTimestamp());
                    arrayList.add(repo);
                }
                // refreshing recycler view
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
        thread.start();


    }

    private void fetchfromServer() {
        pb.setVisibility(View.VISIBLE);

        JsonArrayRequest request = new JsonArrayRequest(FETCHURL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response == null) {
                            pb.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Couldn't fetch the menu! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        recipes = new Gson().fromJson(response.toString(), new TypeToken<List<Repo>>() {
                        }.getType());

                        // adding data to cart list
                        arrayList.clear();
                        arrayList.addAll(recipes);


                        // refreshing recycler view
                        adapter.notifyDataSetChanged();

                        pb.setVisibility(View.GONE);

                        saveTask();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error in getting json
                pb.setVisibility(View.GONE);
                Log.e("TAG", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        request.setShouldCache(false);

        requestQueue.add(request);
    }


    private void saveTask() {


        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task

                for (int i = 0; i < recipes.size(); i++) {
                    Recipe recipe= new  Recipe();
                    recipe.setName(recipes.get(i).getName());
                    recipe.setDescription(recipes.get(i).getDescription());
                    recipe.setPrice(recipes.get(i).getPrice());
                    recipe.setThumbnail(recipes.get(i).getThumbnail());
                    recipe.setChef(recipes.get(i).getChef());
                    recipe.setTimestamp(recipes.get(i).getTimestamp());
                    DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().recipeDao().insert(recipe);
                }


                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }

}
