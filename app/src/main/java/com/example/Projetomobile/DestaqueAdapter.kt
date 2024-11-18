package com.example.Projetomobile.ui.destaque

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.Projetomobile.R

class DestaqueAdapter(
    private val obras: List<Obra>
) : RecyclerView.Adapter<DestaqueAdapter.DestaqueViewHolder>() {

    inner class DestaqueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.destaque_image)

        fun bind(obra: Obra) {
            // Usar Glide para carregar a imagem
            Glide.with(itemView.context)
                .load(obra.imagem_url) // Ajustado para usar o campo correto do Firestore
                .placeholder(R.drawable.container_components)
                .error(R.drawable.error_image)
                .into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestaqueViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_destaque, parent, false)
        return DestaqueViewHolder(view)
    }

    override fun onBindViewHolder(holder: DestaqueViewHolder, position: Int) {
        holder.bind(obras[position])
    }

    override fun getItemCount(): Int = obras.size
}

