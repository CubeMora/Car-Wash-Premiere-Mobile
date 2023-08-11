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
import com.carwashpremiere.carwashpremieremobile.adapters.Adapter_ExtraServicesObject.ExtraServicesObjectViewHolder
import com.carwashpremiere.carwashpremieremobile.model.Model_ExtraServicesGeneral

class Adapter_ExtraServicesObject(
    private val mContext: Context,
    private var mExtraServicesObjectList: List<Model_ExtraServicesGeneral>
) : RecyclerView.Adapter<ExtraServicesObjectViewHolder>() {
    private var totalPrice = 0.0
    private val selectedServices: MutableList<String> = ArrayList()
    private var listener: OnTotalPriceChangedListener? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExtraServicesObjectViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.adapter_extraservicesobject, parent, false)
        return ExtraServicesObjectViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ExtraServicesObjectViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.txt_title.text = mExtraServicesObjectList[position].title
        // holder.txt_price.setText("$ " + mExtraServicesObjectList.get(position).getPrice());
        holder.itemView.tag = mExtraServicesObjectList[position].id
        holder.chBox_ExtraServicesObject.setOnCheckedChangeListener { buttonView, isChecked -> // Update the total price based on whether the checkbox is checked or unchecked
            if (isChecked) {
                totalPrice += mExtraServicesObjectList[position].price.toFloat()
                selectedServices.add(mExtraServicesObjectList[position].title)
            } else {
                totalPrice -= mExtraServicesObjectList[position].price.toFloat()
                selectedServices.remove(mExtraServicesObjectList[position].title)
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

    override fun getItemCount(): Int {
        return mExtraServicesObjectList.size
    }

    interface OnTotalPriceChangedListener {
        fun onTotalPriceChanged(totalPrice: Double)
    }
    fun setData(dataList: List<Model_ExtraServicesGeneral>) {
        mExtraServicesObjectList = dataList
    }

    fun setOnTotalPriceChangedListener(listener: OnTotalPriceChangedListener?) {
        this.listener = listener
    }

    inner class ExtraServicesObjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_title: TextView
        private val txt_price: TextView? = null
        val chBox_ExtraServicesObject: CheckBox

        init {
            txt_title = itemView.findViewById(R.id.txt_AdapterObjectDetail)
            // txt_price = itemView.findViewById(R.id.txt_ExtraServiceObjectPrice);
            chBox_ExtraServicesObject = itemView.findViewById(R.id.chBox_AdapterObjectDetail)
        }
    }
}