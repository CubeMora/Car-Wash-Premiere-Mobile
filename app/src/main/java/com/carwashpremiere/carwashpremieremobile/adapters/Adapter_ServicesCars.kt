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
import androidx.recyclerview.widget.RecyclerView
import com.carwashpremiere.carwashpremieremobile.functions.Function_ActivityMap
import com.carwashpremiere.carwashpremieremobile.R
import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_ServicesCars.ServicesCarsViewHolder
import com.carwashpremiere.carwashpremieremobile.model.Model_ServicesCars
import com.squareup.picasso.Picasso

class Adapter_ServicesCars(
    private val mContext: Context,
    private val mServicesCarsList: List<Model_ServicesCars>
) : RecyclerView.Adapter<ServicesCarsViewHolder>() {
    var serviceName: String? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicesCarsViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.adapter_servicescars, parent, false)
        return ServicesCarsViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ServicesCarsViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.txt_name.text = mServicesCarsList[position].title
        holder.txt_description.text = mServicesCarsList[position].short_descritption
        Picasso.get().load(mServicesCarsList[position].icon).into(holder.img_service)
        holder.itemView.tag = mServicesCarsList[position].id
        holder.btn_serviceCar.setOnClickListener {
            val categoryId = mServicesCarsList[position].id
            val activityId = mServicesCarsList[position].screen_name
            val serviceName = mServicesCarsList[position].title
            //Log.e("GET NAME", serviceName);
            val intent = Intent(mContext, Function_ActivityMap.getActivityClass(activityId))
            intent.putExtra("serviceTitle", serviceName)
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mServicesCarsList.size
    }

    class ServicesCarsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txt_name: TextView
        var txt_description: TextView
        var img_service: ImageView
        var btn_serviceCar: Button

        init {
            txt_name = itemView.findViewById(R.id.txt_ServicesObjectsTitle)
            txt_description = itemView.findViewById(R.id.txt_ServicesObjectsDescription)
            img_service = itemView.findViewById(R.id.img_ServicesObjects)
            btn_serviceCar = itemView.findViewById(R.id.btn_ServicesCars)
        }
    }
}