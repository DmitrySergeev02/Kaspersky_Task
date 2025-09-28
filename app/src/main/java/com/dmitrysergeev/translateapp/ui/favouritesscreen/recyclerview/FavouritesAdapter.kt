package com.dmitrysergeev.translateapp.ui.favouritesscreen.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import com.dmitrysergeev.translateapp.databinding.TranslateItemBinding

class FavouritesAdapter(
    private val onClick: (WordTranslation)->Unit
): RecyclerView.Adapter<FavouritesViewHolder>() {

    var favouritesItems: List<WordTranslation> = emptyList()
        set(newValue) {
            val diffCallback = FavouritesDiffCallback(
                oldFavouritesItems = field,
                newFavouritesItems = newValue
            )
            val diff = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diff.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TranslateItemBinding.inflate(layoutInflater, parent, false)
        return FavouritesViewHolder(binding)
    }

    override fun getItemCount(): Int = favouritesItems.size

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        val item = favouritesItems[position]
        holder.onBind(item, onClick)
    }
}