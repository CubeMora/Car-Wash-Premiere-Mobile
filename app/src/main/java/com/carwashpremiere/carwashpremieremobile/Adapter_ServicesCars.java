package com.carwashpremiere.carwashpremieremobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

public class Adapter_ServicesCars extends RecyclerView.Adapter<Adapter_ServicesCars.ServicesCarsViewHolder>{

    private Context mContext;
    private List<Model_ServicesCars> mServicesCarsList;
    String serviceName;

    public Adapter_ServicesCars(Context mContext, List<Model_ServicesCars> mServicesCarsList) {
        this.mContext = mContext;
        this.mServicesCarsList = mServicesCarsList;
    }

    @NonNull
    @Override
    public ServicesCarsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_servicescars, parent, false);

        return new ServicesCarsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesCarsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txt_name.setText(mServicesCarsList.get(position).getName());
        holder.txt_description.setText(mServicesCarsList.get(position).getShort_descritption());
        Picasso.get().load(mServicesCarsList.get(position).getIcon()).into(holder.img_service);
        holder.itemView.setTag(mServicesCarsList.get(position).getId());

        holder.btn_serviceCar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int categoryId = mServicesCarsList.get(position).getId();
                String activityId = mServicesCarsList.get(position).getActivity();
                String serviceName = mServicesCarsList.get(position).getName();
                //Log.e("GET NAME", serviceName);

                Intent intent = new Intent(mContext, new Function_ActivityMap().getActivityClass(activityId));
                intent.putExtra("serviceTitle", serviceName);

                mContext.startActivity(intent);
            }
        });
    }


    public int getItemCount() {
        return mServicesCarsList.size();
    }

    public static class ServicesCarsViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name, txt_description;
        ImageView img_service;
        Button btn_serviceCar;


        public ServicesCarsViewHolder(View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_ServicesObjectsTitle);
            txt_description = itemView.findViewById(R.id.txt_ServicesObjectsDescription);
            img_service = itemView.findViewById(R.id.img_ServicesObjects);
            btn_serviceCar = itemView.findViewById(R.id.btn_ServicesCars);
        }
    }
}
