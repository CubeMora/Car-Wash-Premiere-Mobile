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

public class Adapter_DetailsObject extends RecyclerView.Adapter<Adapter_DetailsObject.DetailsObjectViewHolder> {
    private Context mContext;
    private List<Model_DetailsObject> mDetailsObjectList;
    private List<String> selectedAdaptersList = new ArrayList<>();

    private List<String> selectedDetails = new ArrayList<>();

    public Adapter_DetailsObject(Context mContext, List<Model_DetailsObject> mDetailsObjectList) {
        this.mContext = mContext;
        this.mDetailsObjectList = mDetailsObjectList;
    }

    @NonNull
    @Override
    public DetailsObjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_detailsobject, parent, false);
        return new DetailsObjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsObjectViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.txt_title.setText(mDetailsObjectList.get(position).getTitle());
        holder.itemView.setTag(mDetailsObjectList.get(position).getId());

        holder.chBox_DetailsObject.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Add the title of the selected adapter to the list if the checkbox is checked
                if (isChecked) {
                    selectedAdaptersList.add(mDetailsObjectList.get(position).getTitle());
                    selectedDetails.add(mDetailsObjectList.get(position).getTitle());
                } else {
                    selectedAdaptersList.remove(mDetailsObjectList.get(position).getTitle());
                    selectedDetails.remove(mDetailsObjectList.get(position).getTitle());
                }
            }
        });

    }
    public List<String> getSelectedDetails() {
        return selectedDetails;
    }
    @Override
    public int getItemCount() {
        return mDetailsObjectList.size();
    }

    public List<String> getSelectedAdaptersList() {
        return selectedAdaptersList;
    }

    public class DetailsObjectViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_title;
        private CheckBox chBox_DetailsObject;

        public DetailsObjectViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_title = itemView.findViewById(R.id.txt_AdapterObjectDetail);

            chBox_DetailsObject = itemView.findViewById(R.id.chBox_AdapterObjectDetail);
        }
    }
}
