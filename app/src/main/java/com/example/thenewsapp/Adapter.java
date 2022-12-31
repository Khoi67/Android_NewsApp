package com.example.thenewsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ItemViewHolder> {

    private Context mContext;
    private List<Item> itemList;
    public Adapter(Context mContext, List<Item> itemList) {
        this.mContext = mContext;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
         Item item = itemList.get(position);
         if (item == null){
             return;
         }
         Glide.with(mContext).load(Uri.parse(item.getImgUri())).into(holder.imgItemNews);
         holder.tvItemTitle.setText(item.getTieuDe());
         holder.tvItemContent.setText(item.getContent());
         holder.itemLayout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 detailNews(item);
             }
         });
         holder.ivFavor.setOnClickListener(new View.OnClickListener() {
             boolean isClicked = false;
             @Override
             public void onClick(View v) {
                 if (!isClicked) {
                     holder.ivFavor.setImageResource(R.drawable.ic_baseline_favorite_24);
                     isClicked = true;
                 } else {
                     holder.ivFavor.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                     isClicked = false;
                 }
             }
         });

         holder.ivShare.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String title = item.getTieuDe();
                 String content = item.getContent();
                 Intent send = new Intent(Intent.ACTION_SEND);
                 send.setType("text/plain");
                 String sendContent = title + "\n" + content;
                 send.putExtra(Intent.EXTRA_TEXT, sendContent);
                 mContext.startActivity(Intent.createChooser(send, "Send via"));
             }
         });
    }
    private void detailNews(Item item){
        Intent intent = new Intent(mContext, NewsActivity.class);
        intent.putExtra("object-item", (Serializable) item);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return itemList.size();

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        ImageView imgItemNews;
        TextView tvItemTitle, tvItemContent;
        ImageView ivFavor, ivShare;
        ConstraintLayout itemLayout;


        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItemNews = itemView.findViewById(R.id.iv_item);
            tvItemTitle = itemView.findViewById(R.id.tv_item_title);
            tvItemContent = itemView.findViewById(R.id.tv_item_content);
            ivFavor = itemView.findViewById(R.id.iv_favor);
            ivShare = itemView.findViewById(R.id.iv_share);
            itemLayout = itemView.findViewById(R.id.item_layout);
        }
    }
}
