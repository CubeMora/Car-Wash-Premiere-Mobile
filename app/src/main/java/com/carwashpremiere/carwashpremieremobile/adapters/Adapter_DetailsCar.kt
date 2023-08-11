package com.carwashpremiere.carwashpremieremobile.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.carwashpremiere.carwashpremieremobile.R
import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_DetailsCar.DetailsCarViewHolder
import com.carwashpremiere.carwashpremieremobile.model.Model_DetailsGeneral

class Adapter_DetailsCar(
    private val mContext: Context,
    private val mDetailsCarList: List<Model_DetailsGeneral>
) : RecyclerView.Adapter<DetailsCarViewHolder>() {
    private val selectedAdaptersList: MutableList<String> = ArrayList()
    private val selectedDetails: MutableList<String> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsCarViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.adapter_detailscar, parent, false)
        return DetailsCarViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: DetailsCarViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.txt_title.text = mDetailsCarList[position].title
        holder.itemView.tag = mDetailsCarList[position].id
        holder.chBox_DetailsCar.setOnCheckedChangeListener { buttonView, isChecked -> // Add the title of the selected adapter to the list if the checkbox is checked
            if (isChecked) {
                selectedAdaptersList.add(mDetailsCarList[position].title)
                selectedDetails.add(mDetailsCarList[position].title)
            } else {
                selectedAdaptersList.remove(mDetailsCarList[position].title)
                selectedDetails.remove(mDetailsCarList[position].title)
            }
        }
    }

    fun getSelectedDetails(): List<String> {
        return selectedDetails
    }

    override fun getItemCount(): Int {
        return mDetailsCarList.size
    }

    fun getSelectedAdaptersList(): List<String> {
        return selectedAdaptersList
    }

    inner class DetailsCarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_title: TextView
        val chBox_DetailsCar: CheckBox

        init {
            txt_title = itemView.findViewById(R.id.txt_AdapterObjectDetail)
            chBox_DetailsCar = itemView.findViewById(R.id.chBox_AdapterObjectDetail)
        }
    }
}