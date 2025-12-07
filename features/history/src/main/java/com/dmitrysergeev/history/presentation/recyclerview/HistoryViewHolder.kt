package com.dmitrysergeev.history.presentation.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.dmitrysergeev.history.R
import com.dmitrysergeev.history.databinding.TranslateItemBinding
import com.dmitrysergeev.history.domain.entities.HistoryTranslation

class HistoryViewHolder(
    private val binding: TranslateItemBinding
): RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: HistoryTranslation){
        val resources = binding.root.resources
        binding.baseWord.text = resources.getString(R.string.translate_item_base_word, item.input)
        binding.translationWord.text  = resources.getString(R.string.translate_item_translation, item.output)
    }

}