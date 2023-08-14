package com.carwashpremiere.carwashpremieremobile.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.carwashpremiere.carwashpremieremobile.functions.Function_ActivityMap
import com.carwashpremiere.carwashpremieremobile.R
import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_Shortcuts.ShortcutsViewHolder
import com.carwashpremiere.carwashpremieremobile.model.Model_Shortcuts
import com.squareup.picasso.Picasso

class Adapter_Shortcuts(
    private val mContext: Context,
    private val mShortcutsList: List<Model_Shortcuts>
) : RecyclerView.Adapter<ShortcutsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShortcutsViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.adapter_shortcuts, parent, false)
        return ShortcutsViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ShortcutsViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.txt_name.text = mShortcutsList[position].title
        Picasso.get().load(mShortcutsList[position].imgUrl).into(holder.img_Shortcut)
        holder.itemView.tag = mShortcutsList[position].id
        holder.btn_Shortcut.setOnClickListener { //

            //Show a toast with the id of the clicked item
            //Toast.makeText(mContext,  "mCategoryList.get(id)" + v.getTag(), Toast.LENGTH_SHORT).show();
            val categoryId = mShortcutsList[position].id

            val activityId = mShortcutsList[position].screen_name
            val serviceName = mShortcutsList[position].title

            val activityClass: Class<*> = Function_ActivityMap.getActivityClass(activityId)

            var intent = Intent(mContext, activityClass)
            intent.putExtra("serviceTitle", serviceName)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mShortcutsList.size
    }

    class ShortcutsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txt_name: TextView
        var img_Shortcut: ImageView
        var btn_Shortcut: ImageButton

        init {
            txt_name = itemView.findViewById(R.id.txt_ShortcutsName)
            btn_Shortcut = itemView.findViewById(R.id.btn_Shortcuts)
            img_Shortcut = itemView.findViewById(R.id.img_ShortcutsIco)
        }
    }
}