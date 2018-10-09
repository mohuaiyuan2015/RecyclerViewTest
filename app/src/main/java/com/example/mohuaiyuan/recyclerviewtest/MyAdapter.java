package com.example.mohuaiyuan.recyclerviewtest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> mData;
    private Context mContext;

    public MyAdapter(Context context, List<String> list) {
        mContext = context;
        mData = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_not_arrival, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String bean = mData.get(position);

        holder.setName(bean);
        Glide.with(mContext)
                .load("helloworld")
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.mipmap.dl_nul_0001)
                .bitmapTransform(new CropCircleTransformation(mContext))
                .into(holder.iv_icon);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_name;
        private ImageView iv_icon;

        ViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            iv_icon = itemView.findViewById(R.id.iv_icon);
        }

        void setName(String parName) {
            this.tv_name.setText(parName);
        }


    }
}
