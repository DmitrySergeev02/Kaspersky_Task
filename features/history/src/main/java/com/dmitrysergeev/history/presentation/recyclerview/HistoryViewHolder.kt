package com.dmitrysergeev.history.presentation.recyclerview

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysergeev.history.R
import com.dmitrysergeev.history.databinding.TranslateItemBinding
import com.dmitrysergeev.history.domain.entities.HistoryTranslation

class HistoryViewHolder(
    private val binding: TranslateItemBinding
): RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: HistoryTranslation, onClick: (HistoryTranslation)->Unit){
        val resources = binding.root.resources
        binding.baseWord.text = resources.getString(R.string.translate_item_base_word, item.input)
        binding.translationWord.text  = resources.getString(R.string.translate_item_translation, item.output)
        binding.actionIcon.setImageResource(R.drawable.broken_heart_icon)
        binding.actionIcon.drawable.setTint(ContextCompat.getColor(binding.root.context, R.color.red))
        binding.actionIcon.setOnClickListener {
            onClick(item)
        }
    }

}