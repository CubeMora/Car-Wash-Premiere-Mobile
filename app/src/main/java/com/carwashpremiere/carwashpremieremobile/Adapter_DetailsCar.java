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

public class Adapter_DetailsCar extends RecyclerView.Adapter<Adapter_DetailsCar.DetailsCarViewHolder> {
    private Context mContext;
    private List<Model_DetailsCar> mDetailsCarList;
    private List<String> selectedAdaptersList = new ArrayList<>();

    private List<String> selectedDetails = new ArrayList<>();

    public Adapter_DetailsCar(Context mContext, List<Model_DetailsCar> mDetailsCarList) {
        this.mContext = mContext;
        this.mDetailsCarList = mDetailsCarList;
    }

    @NonNull
    @Override
    public DetailsCarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_detailscar, parent, false);
        return new DetailsCarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsCarViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txt_title.setText(mDetailsCarList.get(position).getTitle());
        holder.itemView.setTag(mDetailsCarList.get(position).getId());

        holder.chBox_DetailsCar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Add the title of the selected adapter to the list if the checkbox is checked
                if (isChecked) {
                    selectedAdaptersList.add(mDetailsCarList.get(position).getTitle());
                    selectedDetails.add(mDetailsCarList.get(position).getTitle());
                } else {
                    selectedAdaptersList.remove(mDetailsCarList.get(position).getTitle());
                    selectedDetails.remove(mDetailsCarList.get(position).getTitle());
                }
            }
        });

    }
    public List<String> getSelectedDetails() {
        return selectedDetails;
    }
    @Override
    public int getItemCount() {
        return mDetailsCarList.size();
    }

    public List<String> getSelectedAdaptersList() {
        return selectedAdaptersList;
    }

    public class DetailsCarViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_title;
        private CheckBox chBox_DetailsCar;

        public DetailsCarViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_AdapterObjectDetail);

            chBox_DetailsCar = itemView.findViewById(R.id.chBox_AdapterObjectDetail);
        }
    }
}

