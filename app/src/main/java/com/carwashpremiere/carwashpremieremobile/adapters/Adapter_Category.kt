package com.carwashpremiere.carwashpremieremobile.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.carwashpremiere.carwashpremieremobile.functions.Function_ActivityMap
import com.carwashpremiere.carwashpremieremobile.R
import com.carwashpremiere.carwashpremieremobile.model.Model_Category
import com.squareup.picasso.Picasso

class Adapter_Category(
    private val mContext: Context,
    private val mCategoryList: List<Model_Category>
) : RecyclerView.Adapter<Adapter_Category.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.adapter_category, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.txt_name.text = mCategoryList[position].title
        holder.txt_description.text = mCategoryList[position].description

        Picasso.get().load(mCategoryList[position].imageUrl).into(holder.img_category)
        holder.itemView.tag = mCategoryList[position].id

        // Load image from URL using Picasso
        Picasso.get().load(mCategoryList[position].imageUrl).into(holder.img_category)
        holder.itemView.tag = mCategoryList[position].id
        holder.btn_category.setOnClickListener {
            val categoryId = mCategoryList[position].id
            val activityId = mCategoryList[position].screen_name
            var intent: Intent? = null
            val activityClass: Class<*> = Function_ActivityMap.getActivityClass(activityId)
            intent = Intent(mContext, activityClass)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mCategoryList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txt_name: TextView
        var txt_description: TextView
        var img_category: ImageView
        var btn_category: Button

        init {
            txt_name = itemView.findViewById(R.id.txt_AdapterTitleCategory)
            txt_description = itemView.findViewById(R.id.txt_AdapterDescriptionCategory)
            img_category = itemView.findViewById(R.id.img_AdapterCategory)
            btn_category = itemView.findViewById(R.id.btn_AdapterCategory)
        }
    }
}