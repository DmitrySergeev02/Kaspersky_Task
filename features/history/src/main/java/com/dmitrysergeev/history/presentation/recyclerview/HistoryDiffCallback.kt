package com.dmitrysergeev.history.presentation.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.dmitrysergeev.history.domain.entities.HistoryTranslation

class HistoryDiffCallback(
    private val oldFavouritesItems: List<HistoryTranslation>,
    private val newFavouritesItems: List<HistoryTranslation>,
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