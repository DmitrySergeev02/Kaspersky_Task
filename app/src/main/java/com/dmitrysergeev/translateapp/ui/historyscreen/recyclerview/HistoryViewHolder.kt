package com.dmitrysergeev.translateapp.ui.historyscreen.recyclerview

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysergeev.translateapp.R
import com.dmitrysergeev.translateapp.databinding.TranslateItemBinding
import com.dmitrysergeev.translateapp.domain.translation.entities.WordTranslation

class HistoryViewHolder(
    private val binding: TranslateItemBinding
): RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: WordTranslation, onClick: (WordTranslation)->Unit){
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