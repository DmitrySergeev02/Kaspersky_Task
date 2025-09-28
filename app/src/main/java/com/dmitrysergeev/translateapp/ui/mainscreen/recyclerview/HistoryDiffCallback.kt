package com.dmitrysergeev.translateapp.ui.mainscreen.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.dmitrysergeev.translateapp.data.translation.db.history.HistoryDbEntity
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation

class HistoryDiffCallback(
    private val oldHistoryItems: List<WordTranslation>,
    private val newHistoryItems: List<WordTranslation>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int =  oldHistoryItems.size

    override fun getNewListSize(): Int = newHistoryItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldHistoryItems[oldItemPosition]
        val newItem = newHistoryItems[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldHistoryItems[oldItemPosition]
        val newItem = newHistoryItems[newItemPosition]
        return  oldItem == newItem
    }
}