package com.dmitrysergeev.translateapp.ui.favouritesscreen.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.dmitrysergeev.translateapp.data.translation.db.favourites.FavouriteDbEntity

class FavouritesDiffCallback(
    private val oldFavouritesItems: List<FavouriteDbEntity>,
    private val newFavouritesItems: List<FavouriteDbEntity>,
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavouritesItems.size

    override fun getNewListSize(): Int = newFavouritesItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldFavouritesItems[oldItemPosition]
        val newItem = newFavouritesItems[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldFavouritesItems[oldItemPosition]
        val newItem = newFavouritesItems[newItemPosition]
        return oldItem == newItem
    }
}