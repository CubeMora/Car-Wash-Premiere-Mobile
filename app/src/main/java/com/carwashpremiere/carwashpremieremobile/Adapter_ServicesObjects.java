package com.carwashpremiere.carwashpremieremobile;

import android.content.Context;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_ServicesObjects extends RecyclerView.Adapter<Adapter_ServicesObjects.ServicesObjectsViewHolder> {

    private Context mContext;
    private List<Model_ServicesObjects> mServicesObjectsList;

    public Adapter_ServicesObjects(Context mContext, List<Model_ServicesObjects> mServicesObjectsList) {
        this.mContext = mContext;
        this.mServicesObjectsList = mServicesObjectsList;
    }

    @NonNull
    @Override
    public ServicesObjectsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_servicesobjects, parent, false);
        return new ServicesObjectsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesObjectsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txt_name.setText(mServicesObjectsList.get(position).getTitle());
        holder.txt_description.setText(mServicesObjectsList.get(position).getDescription());
        Picasso.get().load(mServicesObjectsList.get(position).getImgUrl()).into(holder.img_service);
        holder.itemView.setTag(mServicesObjectsList.get(position).getId());

        holder.btn_serviceObject.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int objectId = mServicesObjectsList.get(position).getId();

                Toast.makeText(mContext, "Object ID: " + objectId, Toast.LENGTH_SHORT).show();
                String activityId = mServicesObjectsList.get(position).getActivity();
                Intent intent = null;

                Class<?> activityClass = new Function_ActivityMap().getActivityClass(activityId);

                intent = new Intent(mContext, activityClass);

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mServicesObjectsList.size();
    }

    public static class ServicesObjectsViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name, txt_description;
        ImageView img_service;
        Button btn_serviceObject;

        public ServicesObjectsViewHolder(View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_ServicesObjectsTitle);
            txt_description = itemView.findViewById(R.id.txt_ServicesObjectsDescription);
            img_service = itemView.findViewById(R.id.img_ServicesObjects);
            btn_serviceObject = itemView.findViewById(R.id.btn_ServicesObjects);
        }
    }
}

