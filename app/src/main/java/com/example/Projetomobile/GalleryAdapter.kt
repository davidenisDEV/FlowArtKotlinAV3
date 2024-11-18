package com.example.Projetomobile.ui.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.Projetomobile.R

class GalleryAdapter(
    private val obras: List<Obra>
) : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    inner class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_view_gallery)
        val nomeTextView: TextView = itemView.findViewById(R.id.nome_user)

        fun bind(obra: Obra) {
            Glide.with(itemView.context)
                .load(obra.imagem_url)
                .placeholder(R.drawable.container_components)
                .error(R.drawable.error_image)
                .into(imageView)

            nomeTextView.text = obra.nome
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gallery, parent, false)
        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        holder.bind(obras[position])
    }

    override fun getItemCount(): Int = obras.size
}
