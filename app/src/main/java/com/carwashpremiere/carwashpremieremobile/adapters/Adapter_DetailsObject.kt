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
import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_DetailsObject.DetailsObjectViewHolder
import com.carwashpremiere.carwashpremieremobile.model.Model_DetailsGeneral
import com.carwashpremiere.carwashpremieremobile.model.Model_ExtraServicesGeneral

class Adapter_DetailsObject(
    private val mContext: Context,
    private var mDetailsObjectList: List<Model_DetailsGeneral>
) : RecyclerView.Adapter<DetailsObjectViewHolder>() {
    private val selectedAdaptersList: MutableList<String> = ArrayList()
    private val selectedDetails: MutableList<String> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailsObjectViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.adapter_detailsobject, parent, false)
        return DetailsObjectViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: DetailsObjectViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.txt_title.text = mDetailsObjectList[position].title
        holder.itemView.tag = mDetailsObjectList[position].id
        holder.chBox_DetailsObject.setOnCheckedChangeListener { buttonView, isChecked -> // Add the title of the selected adapter to the list if the checkbox is checked
            if (isChecked) {
                selectedAdaptersList.add(mDetailsObjectList[position].title)
                selectedDetails.add(mDetailsObjectList[position].title)
            } else {
                selectedAdaptersList.remove(mDetailsObjectList[position].title)
                selectedDetails.remove(mDetailsObjectList[position].title)
            }
        }
    }

    fun getSelectedDetails(): List<String> {
        return selectedDetails
    }

    override fun getItemCount(): Int {
        return mDetailsObjectList.size
    }
    fun setData(dataList: List<Model_DetailsGeneral>) {
        mDetailsObjectList = dataList
    }
    fun getSelectedAdaptersList(): List<String> {
        return selectedAdaptersList
    }

    inner class DetailsObjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_title: TextView
        val chBox_DetailsObject: CheckBox

        init {
            txt_title = itemView.findViewById(R.id.txt_AdapterObjectDetail)
            chBox_DetailsObject = itemView.findViewById(R.id.chBox_AdapterObjectDetail)
        }
    }
}