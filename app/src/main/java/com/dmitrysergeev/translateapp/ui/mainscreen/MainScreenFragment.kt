package com.dmitrysergeev.translateapp.ui.mainscreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dmitrysergeev.translateapp.MainActivity.Companion.TAG
import com.dmitrysergeev.translateapp.data.translation.api.ApiTranslationRepository
import com.dmitrysergeev.translateapp.data.translation.api.SkyEngApi
import com.dmitrysergeev.translateapp.databinding.FragmentMainScreenBinding
import com.dmitrysergeev.translateapp.utils.InputValidator
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class MainScreenFragment: Fragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private val binding: FragmentMainScreenBinding
        get() = checkNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    private lateinit var apiTranslationRepository: ApiTranslationRepository

    private fun initRetrofit(){
        val okHttpClient = OkHttpClient.Builder()
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://dictionary.skyeng.ru/api/public/v1/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val skyEngApi = retrofit.create<SkyEngApi>()
        apiTranslationRepository = ApiTranslationRepository(skyEngApi)
    }

    private fun showSnackBarWithText(text: String){
        Snackbar.make(
            binding.mainScreenLayout,
            text,
            Snackbar.LENGTH_LONG
        )
            .show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRetrofit()

        binding.searchButton.setOnClickListener {
            val inputString = binding.queryInput.text.toString().trim()
            if (InputValidator.isCorrect(inputString)){
                viewLifecycleOwner.lifecycleScope.launch {
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                        apiTranslationRepository.getTranslations(inputString)
                            .catch { error->
                                binding.wordTranslation.text = ""
                                showSnackBarWithText(error.message ?: "Неизвестная ошибка")
                            }
                            .collect{ words->
                                if (words.isEmpty()){
                                    binding.wordTranslation.text = ""
                                    showSnackBarWithText("Перевод введённого слова не найден")
                                } else {
                                    binding.wordTranslation.text = words[0].text
                                }
                            }
                    }
                }
            } else {
                showSnackBarWithText("Введите корректное слово на русском языке")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}