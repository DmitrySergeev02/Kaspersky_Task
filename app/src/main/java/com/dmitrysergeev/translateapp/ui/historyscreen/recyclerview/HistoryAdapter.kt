package com.dmitrysergeev.translateapp.ui.historyscreen.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysergeev.translateapp.databinding.TranslateItemBinding
import com.dmitrysergeev.translateapp.domain.translation.entities.WordTranslation

class HistoryAdapter(
    private val onClick: (WordTranslation)->Unit
): RecyclerView.Adapter<HistoryViewHolder>() {

    var favouritesItems: List<WordTranslation> = emptyList()
        set(newValue) {
            val diffCallback = HistoryDiffCallback(
                oldFavouritesItems = field,
                newFavouritesItems = newValue
            )
            val diff = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diff.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TranslateItemBinding.inflate(layoutInflater, parent, false)
        return HistoryViewHolder(binding)
    }

    override fun getItemCount(): Int = favouritesItems.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = favouritesItems[position]
        holder.onBind(item, onClick)
    }
}