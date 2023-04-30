package com.example.happyplaces.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.happyplaces.R
import com.example.happyplaces.databinding.ItemHappyPlaceBinding
import com.example.happyplaces.models.HappyPlaceModel

open class HappyPlacesAdapter (
    private val context:Context,
    private val list:ArrayList<HappyPlaceModel>
        ):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var onClickListener:OnClickListener?=null
    class ViewHolder(binding: ItemHappyPlaceBinding):RecyclerView.ViewHolder(binding.root){

        val ivPlaceImg=binding.ivPlaceImage
        val tvTitle=binding.tvTitle
        val tvDescription=binding.tvDescription
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(ItemHappyPlaceBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model=list[position]

        if (holder is ViewHolder){
            holder.ivPlaceImg.setImageURI(Uri.parse(model.image))
            holder.tvTitle.text=model.title
            holder.tvDescription.text=model.description
            holder.itemView.setOnClickListener{
                if (onClickListener!=null){
                    onClickListener!!.onClick(position,model)
                }
            }
        }
    }
    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener=onClickListener
    }

    interface OnClickListener{
        fun onClick(position: Int,model: HappyPlaceModel)
    }

}