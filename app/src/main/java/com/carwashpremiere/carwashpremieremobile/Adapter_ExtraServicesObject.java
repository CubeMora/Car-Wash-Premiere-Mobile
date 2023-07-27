package com.carwashpremiere.carwashpremieremobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter_ExtraServicesObject extends RecyclerView.Adapter<Adapter_ExtraServicesObject.ExtraServicesObjectViewHolder> {
    private double totalPrice;
    private Context mContext;
    private List<Model_ExtraServicesObjects> mExtraServicesObjectList;
    private List<String> selectedServices = new ArrayList<>();

    private OnTotalPriceChangedListener listener;

    public Adapter_ExtraServicesObject(Context mContext, List<Model_ExtraServicesObjects> mExtraServicesObjectList) {
        this.mContext = mContext;
        this.mExtraServicesObjectList = mExtraServicesObjectList;
    }

    @NonNull
    @Override
    public ExtraServicesObjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_extraservicesobject, parent, false);
        return new ExtraServicesObjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExtraServicesObjectViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txt_title.setText(mExtraServicesObjectList.get(position).getName());
       // holder.txt_price.setText("$ " + mExtraServicesObjectList.get(position).getPrice());
        holder.itemView.setTag(mExtraServicesObjectList.get(position).getId());

        holder.chBox_ExtraServicesObject.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Update the total price based on whether the checkbox is checked or unchecked
                if (isChecked) {
                    totalPrice += Float.parseFloat(mExtraServicesObjectList.get(position).getPrice());
                    selectedServices.add(mExtraServicesObjectList.get(position).getName());
                } else {
                    totalPrice -= Float.parseFloat(mExtraServicesObjectList.get(position).getPrice());
                    selectedServices.remove(mExtraServicesObjectList.get(position).getName());
                }

                // Notify the activity about the updated total price
                if (listener != null) {
                    listener.onTotalPriceChanged(totalPrice);
                }
            }
        });

    }

    public List<String> getSelectedServices() {
        return selectedServices;
    }

    @Override
    public int getItemCount() {
        return mExtraServicesObjectList.size();
    }

    public interface OnTotalPriceChangedListener {
        void onTotalPriceChanged(double totalPrice);
    }

    public void setOnTotalPriceChangedListener(OnTotalPriceChangedListener listener) {
        this.listener = listener;
    }

    public class ExtraServicesObjectViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_title, txt_price;
        private CheckBox chBox_ExtraServicesObject;


        public ExtraServicesObjectViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_AdapterObjectDetail);
           // txt_price = itemView.findViewById(R.id.txt_ExtraServiceObjectPrice);
            chBox_ExtraServicesObject = itemView.findViewById(R.id.chBox_AdapterObjectDetail);
        }
    }
}

