package com.example.thenewsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.thenewsapp.objects.Account;
import com.example.thenewsapp.objects.Database;
import com.example.thenewsapp.objects.enums.References;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Adapter extends RecyclerView.Adapter<Adapter.ItemViewHolder> implements Filterable {

    private Context mContext;
    private List<Item> itemList;
    private List<Item> originalList;
    Database database = new Database();
    DatabaseReference accountRef = database.getRef(References.ACCOUNT);
    public Adapter(Context mContext, List<Item> itemList) {
        this.mContext = mContext;
        this.itemList = itemList;
        this.originalList = itemList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Account account = SharedPrefs.getInstance().get(LocalKey.LAST_SIGNED_IN_USER, Account.class);
        List<String> favorPaper = account.getFavoritePaper();
         Item item = itemList.get(position);
         if (item == null){
             return;
         }
         Glide.with(mContext).load(Uri.parse(item.getImgUri())).into(holder.imgItemNews);
         holder.tvItemTitle.setText(item.getTieuDe());
         holder.tvItemContent.setText(item.getContent());
         holder.tvCategory.setText(item.getCategory().getValue());
         holder.itemLayout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 detailNews(item);
             }
         });
         if (favorPaper.contains(item.getId())) {
             holder.ivFavor.setImageResource(R.drawable.ic_baseline_favorite_24);
         } else holder.ivFavor.setImageResource(R.drawable.ic_baseline_favorite_border_24);

         holder.ivFavor.setOnClickListener(new View.OnClickListener() {
             boolean isClicked = favorPaper.contains(item.getId());
             @Override
             public void onClick(View v) {
                 if (!isClicked) {
                     holder.ivFavor.setImageResource(R.drawable.ic_baseline_favorite_24);
                     if(!favorPaper.contains(item.getId())) favorPaper.add(item.getId());
                     isClicked = true;
                 } else {
                     holder.ivFavor.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                     if(favorPaper.contains(item.getId())) favorPaper.remove(item.getId());
                     isClicked = false;
                 }
                 accountRef.child(account.getId()).child("favoritePaper").setValue(favorPaper);
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

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String keyword = constraint.toString();
                if(keyword.isEmpty()) {
                    itemList = originalList;
                } else {
                    keyword = keyword.toLowerCase().trim();
                    List<Item> tmplist = new ArrayList<>();
                    for (Item item : originalList) {
                        if (item.getTieuDe().trim().toLowerCase(Locale.ROOT).contains(keyword)
                                ||item.getContent().trim().toLowerCase(Locale.ROOT).contains(keyword)) {
                            tmplist.add(item);
                        }
                    }

                    itemList = tmplist;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = itemList;
                notifyDataSetChanged();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                itemList = (List<Item>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        ImageView imgItemNews;
        TextView tvItemTitle, tvItemContent, tvCategory;
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
            tvCategory = itemView.findViewById(R.id.tv_category);
        }
    }
}
