package com.carwashpremiere.carwashpremieremobile.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.carwashpremiere.carwashpremieremobile.functions.Function_ActivityMap
import com.carwashpremiere.carwashpremieremobile.R
import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_ServicesObjects.ServicesObjectsViewHolder
import com.carwashpremiere.carwashpremieremobile.model.Model_ServicesObjects
import com.squareup.picasso.Picasso

class Adapter_ServicesObjects(
    private val mContext: Context,
    private val mServicesObjectsList: List<Model_ServicesObjects>
) : RecyclerView.Adapter<ServicesObjectsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesObjectsViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.adapter_servicesobjects, parent, false)
        return ServicesObjectsViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ServicesObjectsViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.txt_name.text = mServicesObjectsList[position].title
        holder.txt_description.text = mServicesObjectsList[position].description
        Picasso.get().load(mServicesObjectsList[position].imgUrl).into(holder.img_service)
        holder.itemView.tag = mServicesObjectsList[position].id
        holder.btn_serviceObject.setOnClickListener {
            val objectId = mServicesObjectsList[position].id
            Toast.makeText(mContext, "Object ID: $objectId", Toast.LENGTH_SHORT).show()
            val activityId = mServicesObjectsList[position].screen_name
            var intent: Intent? = null
            val activityClass: Class<*> = Function_ActivityMap.getActivityClass(activityId)
            intent = Intent(mContext, activityClass)
            intent.putExtra("objectTitle", mServicesObjectsList[position].title)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mServicesObjectsList.size
    }

    class ServicesObjectsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txt_name: TextView
        var txt_description: TextView
        var img_service: ImageView
        var btn_serviceObject: Button

        init {
            txt_name = itemView.findViewById(R.id.txt_ServicesObjectsTitle)
            txt_description = itemView.findViewById(R.id.txt_ServicesObjectsDescription)
            img_service = itemView.findViewById(R.id.img_ServicesObjects)
            btn_serviceObject = itemView.findViewById(R.id.btn_ServicesObjects)
        }
    }
}