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

public class Adapter_ExtraServicesCar extends RecyclerView.Adapter<Adapter_ExtraServicesCar.ExtraServicesCarViewHolder> {
    private double totalPrice;
    private Context mContext;
    private List<Model_ExtraServicesCar> mExtraServicesCarsList;
    private List<String> selectedServices = new ArrayList<>();

    private OnTotalPriceChangedListener listener;

    public Adapter_ExtraServicesCar(Context mContext, List<Model_ExtraServicesCar> mExtraServicesCarsList) {
        this.mContext = mContext;
        this.mExtraServicesCarsList = mExtraServicesCarsList;
    }

    @NonNull
    @Override
    public ExtraServicesCarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_extraservicescar, parent, false);
        return new ExtraServicesCarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExtraServicesCarViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txt_title.setText(mExtraServicesCarsList.get(position).getTitle());
        holder.txt_price.setText("$ " + mExtraServicesCarsList.get(position).getPrice());
        holder.itemView.setTag(mExtraServicesCarsList.get(position).getId());

        holder.chBox_ExtraServicesCar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Update the total price based on whether the checkbox is checked or unchecked
                if (isChecked) {
                    totalPrice += Float.parseFloat(mExtraServicesCarsList.get(position).getPrice());
                    selectedServices.add(mExtraServicesCarsList.get(position).getTitle());
                } else {
                    totalPrice -= Float.parseFloat(mExtraServicesCarsList.get(position).getPrice());
                    selectedServices.remove(mExtraServicesCarsList.get(position).getTitle());
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
        return mExtraServicesCarsList.size();
    }

    public interface OnTotalPriceChangedListener {
        void onTotalPriceChanged(double totalPrice);
    }

    public void setOnTotalPriceChangedListener(OnTotalPriceChangedListener listener) {
        this.listener = listener;
    }



    public class ExtraServicesCarViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_title, txt_price;
        private CheckBox chBox_ExtraServicesCar;


        public ExtraServicesCarViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_AdapterObjectDetail);
            txt_price = itemView.findViewById(R.id.txt_ExtraServiceObjectPrice);
            chBox_ExtraServicesCar = itemView.findViewById(R.id.chBox_AdapterObjectDetail);
        }
    }
}
