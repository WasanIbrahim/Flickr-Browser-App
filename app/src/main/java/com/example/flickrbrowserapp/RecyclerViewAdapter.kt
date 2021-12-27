package com.example.flickrbrowserapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flickrbrowserapp.model.Photo
import kotlinx.android.synthetic.main.item_row.view.*

class RecyclerViewAdapter(var photo: List<Photo>): RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>() {
    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val photo = photo[position]

        holder.itemView.apply {
            titleText.text = photo.title
            if (photo.url_h != null) {
                Glide.with(context)
                    .load(photo.url_h)
                    .into(imageView)

            } else {
                //set no image found
                imageView.setBackgroundResource(R.drawable.noimage)

            }
        }
    }

    override fun getItemCount(): Int {
        return photo.size
    }
    fun update(data: List<Photo>) {
        this.photo = data
        notifyDataSetChanged()
    }


}
















