package com.quar.taskd2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.quar.taskd2.R;
import com.quar.taskd2.models.NewsModel;
import com.quar.taskd2.room.TopNewsTable;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AdapterTopNews extends PagedListAdapter<NewsModel, AdapterTopNews.viewHolder> {

    private final Context context;
    private ItemClickListener mClickListener;


    public AdapterTopNews(Context context) {
        super(NewsModel.DIFF_CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_top_news, parent, false);
        return new viewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
        final NewsModel newsModel = getItem(position);

        if (newsModel != null) {
            holder.bindTo(newsModel);
        } else {
            holder.clear();
        }

    }

    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        NewsModel newsModel;

        ImageView top_news_img;

        TextView top_news_title, top_news_description, top_news_date;


        viewHolder(@NonNull View itemView) {
            super(itemView);

            top_news_img = itemView.findViewById(R.id.top_news_img);

            top_news_title = itemView.findViewById(R.id.top_news_title);
            top_news_description = itemView.findViewById(R.id.top_news_description);
            top_news_date = itemView.findViewById(R.id.top_news_date);
        }

        void bindTo(NewsModel newsModel) {
            this.newsModel = newsModel;

            this.top_news_title.setText(newsModel.getSource());
            this.top_news_description.setText(newsModel.getTitle());

            try {
                Date date1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(newsModel.getPublishedAt());
                top_news_date.setText(new SimpleDateFormat("MMMM dd, yyyy").format(date1));

            } catch (ParseException e) {
                e.printStackTrace();
            }

            Picasso.get().load(newsModel.getUrlToImage()).fit().centerCrop()
                    .placeholder(R.drawable.ic_download)
                    .error(R.drawable.ic_broken_image)
                    .into(this.top_news_img);
            this.itemView.setOnClickListener(this);
        }

        void clear() {
            itemView.invalidate();
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) mClickListener.onItemClick(v, newsModel);
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, NewsModel newsModel);
    }


}