package com.app.pagination;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.core.widget.NestedScrollView.OnScrollChangeListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class MainActivity extends AppCompatActivity {
    NestedScrollView objNested;
    RecyclerView objRecyclerView;
    ProgressBar objProgressBar;
    ArrayList<MainData> dataArrayList = new ArrayList<>();
    MainAdapter adapter;
    int page = 1;
    int limit = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        objNested = findViewById(R.id.scrollViewId);
        objRecyclerView = findViewById(R.id.recyclerViewId);
        objProgressBar = findViewById(R.id.progressBarId);
        adapter = new MainAdapter(MainActivity.this, dataArrayList);
        objRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        objRecyclerView.setAdapter(adapter);
        getData(page, limit);
        objNested.setOnScrollChangeListener(new OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    page++;
                    objProgressBar.setVisibility(View.VISIBLE);
                    getData(page, limit);
                }
            }
        });


    }


    private void getData(int page, int limit) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://picsum.photos/")
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        MainInterface mainInterface = retrofit.create(MainInterface.class);
        Call<String> call = mainInterface.STRING_CALL(page, limit);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful() && response.body() != null) {

                    objProgressBar.setVisibility(View.GONE);
                    try {
                        JSONArray JsonArray = new JSONArray(response.body());


                        ParseResult(JsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void ParseResult(JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.length(); i++) {

            try {
                JSONObject obj = jsonArray.getJSONObject(i);
                MainData data = new MainData();
                data.setImage(obj.getString("download_url"));
                data.setName(obj.getString("author"));
                dataArrayList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter = new MainAdapter(MainActivity.this, dataArrayList);
            objRecyclerView.setAdapter(adapter);
        }
    }
}