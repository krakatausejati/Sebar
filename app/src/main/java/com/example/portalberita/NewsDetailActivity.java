package com.example.portalberita;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class NewsDetailActivity extends AppCompatActivity{

    String url,img, title, desc, publishedAt, author, source;
    ImageView tvImg;
    TextView tvUrl, tvTitle, tvDesc, tvPublishedAt, tvAuthor, tvSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        url = getIntent().getStringExtra("urlNews");
        img = getIntent().getStringExtra("imgNews");
        title = getIntent().getStringExtra("titleNews");
        desc = getIntent().getStringExtra("contentNews");
        publishedAt = getIntent().getStringExtra("dateNews");
        author = getIntent().getStringExtra("authorNews");
        source = getIntent().getStringExtra("sourceNews");

        bindView();

        Glide.with(getApplicationContext())
                .load(img).into(tvImg);
        tvUrl.setText(source);
        tvTitle.setText(title);
        tvDesc.setText(desc);
        tvPublishedAt.setText(publishedAt);
        tvSource.setText("Lihat Lebih Lengkap");
        tvAuthor.setText(author);

        if(getSupportActionBar() != null)getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void source (View view) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    public void bindView(){
        tvUrl = findViewById(R.id.publisher);
        tvImg = findViewById(R.id.img);
        tvTitle = findViewById(R.id.title);
        tvDesc = findViewById(R.id.desc);
        tvAuthor = findViewById(R.id.author);
        tvPublishedAt = findViewById(R.id.publishedAt);
        tvSource = findViewById(R.id.source);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(NewsDetailActivity.this, MainActivity.class);
        startActivity(intent);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(NewsDetailActivity.this, MainActivity.class);
        startActivity(intent);
    }
}