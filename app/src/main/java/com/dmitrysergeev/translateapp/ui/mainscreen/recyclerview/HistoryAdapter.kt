package com.dmitrysergeev.translateapp.ui.mainscreen.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysergeev.translateapp.R
import com.dmitrysergeev.translateapp.data.translation.db.history.HistoryDbEntity
import com.dmitrysergeev.translateapp.databinding.TranslateItemBinding

class HistoryAdapter(
    private val onDelete: (HistoryDbEntity) -> Unit
): RecyclerView.Adapter<HistoryViewHolder>() {
    var historyItems: List<HistoryDbEntity> = emptyList()
        set(newValue){
            val diffCallback = HistoryDiffCallback(oldHistoryItems = field, newHistoryItems = newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TranslateItemBinding.inflate(inflater, parent, false)
        return HistoryViewHolder(binding)
    }

    override fun getItemCount(): Int = historyItems.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = historyItems[position]
        val resources = holder.itemView.resources
        holder.apply {
            binding.baseWord.text = resources.getString(R.string.translate_item_base_word, history.baseWord)
            binding.translationWord.text = resources.getString(R.string.translate_item_translation, history.translation)
            binding.actionIcon.setOnClickListener {
                onDelete(history)
            }
        }
    }
}