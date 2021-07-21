package com.example.portalberita.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.portalberita.NewsDetailActivity;
import com.example.portalberita.R;
import com.example.portalberita.Utils;
import com.example.portalberita.entity.Article;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{

    private List<Article> articles;
    private Context context;

    public Adapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, null, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, int position) {
        final MyViewHolder holder = holders;
        Article model = articles.get(position);

        Glide.with(context).load(model.getUrlToImage()).into(holder.imageView);
        holder.title.setText(model.getTitle());
        holder.desc.setText(model.getDescription());
        holder.source.setText(model.getSource().getName());
        holder.time.setText(" \u2022 " + Utils.DateToTimeFormat(model.getPublishedAt()));
        holder.published_at.setText(Utils.DateFormat(model.getPublishedAt()));
        holder.author.setText(model.getAuthor());

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, NewsDetailActivity.class);
                i.putExtra("urlNews", model.getUrl());
                i.putExtra("imgNews", model.getUrlToImage());
                i.putExtra("titleNews", model.getTitle());
                if (model.getDescription() == null) {
                    i.putExtra("contentNews", "No Description");
                } else {
                    i.putExtra("contentNews", model.getDescription());
                }
                i.putExtra("dateNews", model.getPublishedAt().substring(0, 10) + " " + model.getPublishedAt().substring(11, 16));
                if (model.getAuthor() == null) {
                    i.putExtra("authorNews", "Unknown Author");
                } else {
                    i.putExtra("authorNews", model.getAuthor());
                }
                i.putExtra("sourceNews", model.getSource().getName());
                context.startActivity(i);
                ((Activity) context).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        CardView cv;
        TextView title, desc, author, published_at, source, time;
        ImageView imageView;
        ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);

            cv = itemView.findViewById(R.id.cardview);
            title = itemView.findViewById(R.id.title);
            desc = itemView.findViewById(R.id.desc);
            author = itemView.findViewById(R.id.author);
            published_at = itemView.findViewById(R.id.publishedAt);
            source = itemView.findViewById(R.id.source);
            time = itemView.findViewById(R.id.time);
            imageView = itemView.findViewById(R.id.img);
            progressBar = itemView.findViewById(R.id.progress_load_photo);
        }
    }
}
