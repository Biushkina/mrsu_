package com.example.university

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterClass(private val dataList: ArrayList<DataClass>): RecyclerView.Adapter<AdapterClass.ViewHolderClass>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolderClass(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val currentItem = dataList[position]
        holder.rvTitle.text = currentItem.dataTitle
        holder.imgExpand.setImageResource(currentItem.dataImage)
        holder.rvInfo.text = currentItem.infoList

        holder.imgExpand.setOnClickListener{
            if(holder.rvInfo.visibility == View.GONE){
                holder.rvInfo.visibility = View.VISIBLE
                holder.imgExpand.setImageResource(R.drawable.baseline_expand_less_24)
            }
            else{
                holder.rvInfo.visibility = View.GONE
                holder.imgExpand.setImageResource(R.drawable.baseline_expand_more_24)
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolderClass(itemView: View):RecyclerView.ViewHolder(itemView) {
        val rvTitle: TextView = itemView.findViewById(R.id.title)
        val imgExpand:ImageView = itemView.findViewById(R.id.img_expand)
        val rvInfo: TextView = itemView.findViewById(R.id.info)

    }

}