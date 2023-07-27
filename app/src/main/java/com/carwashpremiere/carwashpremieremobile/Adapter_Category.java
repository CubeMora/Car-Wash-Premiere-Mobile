package com.carwashpremiere.carwashpremieremobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class Adapter_Category extends RecyclerView.Adapter<Adapter_Category.ViewHolder> {
    private Context mContext;
    private List<Model_Category> mCategoryList;

    public Adapter_Category(Context mContext, List<Model_Category> mCategoryList) {
        this.mContext = mContext;
        this.mCategoryList = mCategoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_category, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txt_name.setText(mCategoryList.get(position).getName());
        holder.txt_description.setText(mCategoryList.get(position).getDescription());

        // Load image from URL using Picasso
        Picasso.get().load(mCategoryList.get(position).getImage()).into(holder.img_category);

        holder.itemView.setTag(mCategoryList.get(position).getId());

        holder.btn_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int categoryId = mCategoryList.get(position).getId();
                String activityId = mCategoryList.get(position).getActivity();
                Intent intent = null;

                Class<?> activityClass = new Function_ActivityMap().getActivityClass(activityId);

                intent = new Intent(mContext, activityClass);

                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name, txt_description;
        ImageView img_category;
        Button btn_category;


        public ViewHolder(View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_AdapterTitleCategory);
            txt_description = itemView.findViewById(R.id.txt_AdapterDescriptionCategory);
            img_category = itemView.findViewById(R.id.img_AdapterCategory);
            btn_category = itemView.findViewById(R.id.btn_AdapterCategory);
        }
    }
}

