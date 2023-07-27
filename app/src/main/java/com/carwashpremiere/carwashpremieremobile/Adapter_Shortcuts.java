package com.carwashpremiere.carwashpremieremobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_Shortcuts extends RecyclerView.Adapter<Adapter_Shortcuts.ShortcutsViewHolder> {
    private Context mContext;
    private List<Model_Shortcuts> mShortcutsList;

    public Adapter_Shortcuts(Context mContext, List<Model_Shortcuts> mShortcutsList) {
        this.mContext = mContext;
        this.mShortcutsList = mShortcutsList;
    }

    @NonNull
    @Override
    public ShortcutsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_shortcuts, parent, false);
        return new ShortcutsViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ShortcutsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txt_name.setText(mShortcutsList.get(position).getName());

        Picasso.get().load(mShortcutsList.get(position).getIcon()).into(holder.img_Shortcut);
        holder.itemView.setTag(mShortcutsList.get(position).getId());

        holder.btn_Shortcut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //
                //Toast.makeText(mContext, "Ekish", Toast.LENGTH_SHORT).show();
                //Show a toast with the id of the clicked item
                //Toast.makeText(mContext,  "mCategoryList.get(id)" + v.getTag(), Toast.LENGTH_SHORT).show();
                int categoryId = mShortcutsList.get(position).getId();

                Toast.makeText(mContext, "Category ID: " + categoryId, Toast.LENGTH_SHORT).show();
                String activityId = mShortcutsList.get(position).getActivity();
                String serviceName = mShortcutsList.get(position).getName();
                Intent intent = null;

                Class<?> activityClass = new Function_ActivityMap().getActivityClass(activityId);

                intent = new Intent(mContext, activityClass);
                intent.putExtra("serviceTitle", serviceName);

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mShortcutsList.size();
    }

    public static class ShortcutsViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name;
        ImageView img_Shortcut;
        ImageButton btn_Shortcut;

        public ShortcutsViewHolder(View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_ShortcutsName);
            btn_Shortcut = itemView.findViewById(R.id.btn_Shortcuts);
            img_Shortcut = itemView.findViewById(R.id.img_ShortcutsIco);
        }
    }
}
