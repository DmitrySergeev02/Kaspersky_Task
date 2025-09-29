package com.dmitrysergeev.translateapp.ui.favouritesscreen.recyclerview

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dmitrysergeev.translateapp.R
import com.dmitrysergeev.translateapp.data.translation.db.favourites.FavouriteDbEntity
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import com.dmitrysergeev.translateapp.databinding.TranslateItemBinding

class FavouritesViewHolder(
    private val binding: TranslateItemBinding
): RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: WordTranslation, onClick: (WordTranslation)->Unit){
        val resources = binding.root.resources
        binding.baseWord.text = resources.getString(R.string.translate_item_base_word, item.originalWord)
        binding.translationWord.text  = resources.getString(R.string.translate_item_translation, item.translation)
        binding.actionIcon.setImageResource(R.drawable.broken_heart_icon)
        binding.actionIcon.drawable.setTint(ContextCompat.getColor(binding.root.context, R.color.red))
        binding.actionIcon.setOnClickListener {
            onClick(item)
        }
    }

}