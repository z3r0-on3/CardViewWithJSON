package com.pasha.cardviewwithjson;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private List<JSONInfo> result;
    ImageLoader imageLoader;

    public DataAdapter(List<JSONInfo> result, ImageLoader imageLoader){

        this.result = result;
        this.imageLoader = imageLoader;

    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        DataAdapter.ViewHolder dataAdapter = new DataAdapter.ViewHolder(view);
        return dataAdapter;
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {

        JSONInfo info = result.get(position);
        holder.txtName.setText(info.getName());
        holder.txtUrl.setText(info.getUrl());
        String imageUrl = info.getImage();
        imageLoader.displayImage(imageUrl, holder.imageView);

    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView txtName, txtUrl;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView)itemView.findViewById(R.id.imageView);
            txtName= (TextView)itemView.findViewById(R.id.txtName);
            txtUrl = (TextView)itemView.findViewById(R.id.txtUrl);

        }
    }
}
