package com.example.Projetomobile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.Projetomobile.R

class DestaqueAdapter(private val images: List<Int>) :
    RecyclerView.Adapter<DestaqueAdapter.DestaqueViewHolder>() {

    inner class DestaqueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.destaque_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestaqueViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_destaque, parent, false)
        return DestaqueViewHolder(view)
    }

    override fun onBindViewHolder(holder: DestaqueViewHolder, position: Int) {
        holder.imageView.setImageResource(images[position])
    }

    override fun getItemCount(): Int = images.size
}
