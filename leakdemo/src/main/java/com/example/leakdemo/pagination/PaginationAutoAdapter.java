package com.example.leakdemo.pagination;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.leakdemo.R;
import com.example.leakdemo.rxbus.RxBus;

import java.util.ArrayList;
import java.util.List;

/**
 * __   __    _
 * \ \ / /_ _| |_   _ _ __
 * \ V / _` | | | | | '_ \
 * | | (_| | | |_| | | | |
 * |_|\__,_|_|\__,_|_| |_|
 * Created by Hades on 2017/8/1.
 */

class PaginationAutoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int ITEM_LOG = 1;
    private final RxBus rxBus;
    private final List<String> items=new ArrayList<>();

    public PaginationAutoAdapter(RxBus rxBus) {
        this.rxBus=rxBus;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ItemLogViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        (((ItemLogViewHolder) holder)).bindContent(items.get(position));

        boolean lastPositionReached=position==items.size()-1;
        if (lastPositionReached) {
            rxBus.send(new PageEvent());
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_LOG;
    }

    void addItems(List<String > items){
        this.items.addAll(items);
    }

    private static class ItemLogViewHolder extends RecyclerView.ViewHolder{

        public ItemLogViewHolder(View itemView) {
            super(itemView);
        }

        static ItemLogViewHolder create(ViewGroup parent){
            return  new ItemLogViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log,parent,false));
        }

        void bindContent(String content){
            ((TextView)itemView).setText(content);
        }
    }

    static class PageEvent{}
}
