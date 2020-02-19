package com.ihiabe.josh.fastnews.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ihiabe.josh.fastnews.R;
import com.ihiabe.josh.fastnews.data.database.entity.Articles;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsHolder> {
    private List<Articles> mArticles;
    private Context mContext;
    private OnItemClickListener mListener;

    public NewsRecyclerAdapter(Context context) {
        mContext = context;
        mArticles = new ArrayList<>();
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext.getApplicationContext())
                .inflate(R.layout.news_item,parent,false);
        return new NewsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        holder.bind(mArticles.get(position));
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public void setData(List<Articles> newArticles){
        if (mArticles != null){
            PostDiffCallback postDiffCallback = new PostDiffCallback(mArticles,newArticles);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(postDiffCallback);

            mArticles.clear();
            mArticles.addAll(newArticles);
            diffResult.dispatchUpdatesTo(this);
        }else {
            mArticles = newArticles;
        }
    }


    class NewsHolder extends RecyclerView.ViewHolder{
        private ImageView newsImage;
        private TextView newsTitle;
        private TextView publishedDate;
        private TextView newsAuthor;

        NewsHolder(@NonNull View itemView) {
            super(itemView);

            newsImage = itemView.findViewById(R.id.news_home_image);
            newsTitle = itemView.findViewById(R.id.news_home_title);
            publishedDate = itemView.findViewById(R.id.news_home_date);
            newsAuthor = itemView.findViewById(R.id.news_author);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   int position = getAdapterPosition();
                   if (mListener != null && position != RecyclerView.NO_POSITION){
                       mListener.onItemClick(mArticles.get(position));
                   }
                }
            });
        }

        void bind(Articles articles){
            if (articles != null){
                Picasso.get().load(articles.getUrlToImage()).fit().centerCrop().into(newsImage);
                newsTitle.setText(articles.getTitle());
                publishedDate.setText(articles.getPublishedAt());
                newsAuthor.setText(articles.getAuthor());
            }
        }
    }

    class PostDiffCallback extends  DiffUtil.Callback{
        private final List<Articles> oldArticles, newArticles;

        PostDiffCallback(List<Articles> oldArticles, List<Articles> newArticles) {
            this.oldArticles = oldArticles;
            this.newArticles = newArticles;
        }

        @Override
        public int getOldListSize() {
            return oldArticles.size();
        }

        @Override
        public int getNewListSize() {
            return newArticles.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldArticles.get(oldItemPosition).getTitle().equals(newArticles.get(newItemPosition)
                    .getTitle());
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldArticles.get(oldItemPosition).equals(newArticles.get(newItemPosition));
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Articles articles);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }
}
