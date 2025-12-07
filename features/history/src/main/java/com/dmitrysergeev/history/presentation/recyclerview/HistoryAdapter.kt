package com.dmitrysergeev.history.presentation.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysergeev.history.databinding.TranslateItemBinding
import com.dmitrysergeev.history.domain.entities.HistoryTranslation

class HistoryAdapter: RecyclerView.Adapter<HistoryViewHolder>() {

    var favouritesItems: List<HistoryTranslation> = emptyList()
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
        holder.onBind(item)
    }
}