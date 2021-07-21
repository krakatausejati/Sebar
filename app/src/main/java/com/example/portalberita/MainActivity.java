package com.example.portalberita;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.portalberita.adapter.Adapter;
import com.example.portalberita.api.ApiInterface;
import com.example.portalberita.api.Server;
import com.example.portalberita.category.Business;
import com.example.portalberita.category.Entertainment;
import com.example.portalberita.category.Health;
import com.example.portalberita.category.Science;
import com.example.portalberita.category.Sports;
import com.example.portalberita.category.Technology;
import com.example.portalberita.entity.Article;
import com.example.portalberita.entity.News;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

    public static final String API_KEY = "711b541fe4bf40ad8930aa38542ccda2";
    private RecyclerView recyclerView;
    private List<Article> list = new ArrayList<>();
    ProgressDialog loading;
    ApiInterface api;
    private Adapter adapter;
    private static final int TIME_LIMIT = 1800;
    private static long backPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        api = Server.getApiService();
        adapter = new Adapter(MainActivity.this, list);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);
        LoadJson("");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void LoadJson(final String keyword){
        Call<News> call;

        if (keyword.length() > 0 ){
            call = api.getNewsSearch(keyword,"publishedAt", API_KEY);
        } else {
            call = api.getListAllNews("id", API_KEY);
        }
        call.enqueue(new Callback<News>(){
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful()){
                    list = response.body().getArticle();
                    recyclerView.setAdapter(new Adapter(MainActivity.this, list));
                    adapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(MainActivity.this, "Gagal mendapatkan data!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Gagal terhubung ke internet!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Mencari Berita Terbaru...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2){
                    LoadJson(query);
                }
                else {
                    Toast.makeText(MainActivity.this, "Masukan Kata Yang Ingin Dicari!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                LoadJson(newText);
                return false;
            }
        });

        searchMenuItem.getIcon().setVisible(false, false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (TIME_LIMIT + backPressed > System.currentTimeMillis()) {
                // super.onBackPressed();
                moveTaskToBack(true);
            } else {
                Toast.makeText(this, "Press back again to exit.", Toast.LENGTH_SHORT).show();
            }
            backPressed = System.currentTimeMillis();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.business) {
            Intent bus = new Intent(MainActivity.this, Business.class);
            startActivity(bus);
        } else if (id == R.id.entertainment) {
            Intent enter = new Intent(MainActivity.this, Entertainment.class);
            startActivity(enter);
        } else if (id == R.id.health) {
            Intent heal = new Intent(MainActivity.this, Health.class);
            startActivity(heal);
        } else if (id == R.id.science) {
            Intent scien = new Intent(MainActivity.this, Science.class);
            startActivity(scien);
        } else if (id == R.id.sports) {
            Intent sport = new Intent(MainActivity.this, Sports.class);
            startActivity(sport);
        } else if (id == R.id.technology) {
            Intent tech = new Intent(MainActivity.this, Technology.class);
            startActivity(tech);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}