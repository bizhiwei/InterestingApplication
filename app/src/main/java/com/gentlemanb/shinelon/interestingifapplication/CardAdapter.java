package com.gentlemanb.shinelon.interestingifapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Shinelon on 2016/6/18.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyHolder> {
    private List<String> list;

    public CardAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.main_card_item, null));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.tv.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public MyHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.main_card_item_tv);
        }
    }
}
