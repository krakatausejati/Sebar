package com.example.portalberita.category;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.portalberita.MainActivity;
import com.example.portalberita.R;
import com.example.portalberita.adapter.Adapter;
import com.example.portalberita.api.ApiInterface;
import com.example.portalberita.api.Server;
import com.example.portalberita.entity.Article;
import com.example.portalberita.entity.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Entertainment extends AppCompatActivity {

    public static final String API_KEY = "711b541fe4bf40ad8930aa38542ccda2";
    private RecyclerView news;
    private Adapter adapter;
    List<Article> list = new ArrayList<>();
    final String category = "entertainment";
    ProgressDialog loading;
    ApiInterface api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);

        news = findViewById(R.id.news);
        api = Server.getApiService();
        adapter = new Adapter(Entertainment.this, list);

        news.setHasFixedSize(true);
        news.setLayoutManager(new LinearLayoutManager(Entertainment.this));
        news.setAdapter(adapter);
        LoadJson();

        //membuat back button toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    public void LoadJson() {
        loading = new ProgressDialog(Entertainment.this);
        loading.setCancelable(false);
        loading.setMessage("Loading...");
        showDialog();
        api.getListNews("id", category, API_KEY).enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful()){
                    hideDialog();
                    list = response.body().getArticle();
                    news.setAdapter(new Adapter(Entertainment.this, list));
                    adapter.notifyDataSetChanged();
                } else {
                    hideDialog();
                    Toast.makeText(Entertainment.this, "Gagal mendapatkan data!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                hideDialog();
                Toast.makeText(Entertainment.this, "Gagal terhubung ke internet!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDialog() {
        if (!loading.isShowing())
            loading.show();
    }

    private void hideDialog() {
        if (loading.isShowing())
            loading.dismiss();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(Entertainment.this, MainActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Entertainment.this, MainActivity.class);
        startActivity(intent);
    }
}
