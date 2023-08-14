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
import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_ExtraServicesCar.ExtraServicesCarViewHolder
import com.carwashpremiere.carwashpremieremobile.model.Model_ExtraServicesGeneral

class Adapter_ExtraServicesCar(
    private val mContext: Context,
    private var mExtraServicesCarsList: List<Model_ExtraServicesGeneral>
) : RecyclerView.Adapter<ExtraServicesCarViewHolder>() {
    private var totalPrice = 0.0
    private val selectedServices: MutableList<String> = ArrayList()
    private var listener: OnTotalPriceChangedListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtraServicesCarViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.adapter_extraservicescar, parent, false)
        return ExtraServicesCarViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ExtraServicesCarViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.txt_title.text = mExtraServicesCarsList[position].title
        holder.txt_price.text = "$ " + mExtraServicesCarsList[position].price
        holder.itemView.tag = mExtraServicesCarsList[position].id
        holder.chBox_ExtraServicesCar.setOnCheckedChangeListener { buttonView, isChecked -> // Update the total price based on whether the checkbox is checked or unchecked
            if (isChecked) {
                totalPrice += mExtraServicesCarsList[position].price.toFloat()
                selectedServices.add(mExtraServicesCarsList[position].title)
            } else {
                totalPrice -= mExtraServicesCarsList[position].price.toFloat()
                selectedServices.remove(mExtraServicesCarsList[position].title)
            }

            // Notify the activity about the updated total price
            if (listener != null) {
                listener!!.onTotalPriceChanged(totalPrice)
            }
        }
    }

    fun getSelectedServices(): List<String> {
        return selectedServices
    }
    fun setData(dataList: List<Model_ExtraServicesGeneral>) {
        mExtraServicesCarsList = dataList
    }

    override fun getItemCount(): Int {
        return mExtraServicesCarsList.size
    }

    interface OnTotalPriceChangedListener {
        fun onTotalPriceChanged(totalPrice: Double)
    }

    fun setOnTotalPriceChangedListener(listener: OnTotalPriceChangedListener?) {
        this.listener = listener
    }

    inner class ExtraServicesCarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_title: TextView
        val txt_price: TextView
        val chBox_ExtraServicesCar: CheckBox

        init {
            txt_title = itemView.findViewById(R.id.txt_AdapterObjectDetail)
            txt_price = itemView.findViewById(R.id.txt_ExtraServiceObjectPrice)
            chBox_ExtraServicesCar = itemView.findViewById(R.id.chBox_AdapterObjectDetail)
        }
    }
}