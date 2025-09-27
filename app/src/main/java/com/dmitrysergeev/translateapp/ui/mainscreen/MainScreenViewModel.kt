package com.dmitrysergeev.translateapp.ui.mainscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmitrysergeev.translateapp.data.translation.TranslationRepository
import com.dmitrysergeev.translateapp.data.translation.api.ApiTranslationRepository
import com.dmitrysergeev.translateapp.data.translation.api.SkyEngApi
import com.dmitrysergeev.translateapp.utils.InputValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class MainScreenViewModel: ViewModel() {

    private val apiTranslationRepository: TranslationRepository

    private val _mainScreenUiState: MutableStateFlow<MainScreenUiState> = MutableStateFlow(MainScreenUiState("",""))
    val mainScreenUiState  = _mainScreenUiState.asStateFlow()

    init {
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

    fun translateText(query: String){
        val trimmedString = query.trim()
        if (InputValidator.isCorrect(trimmedString)){
            viewModelScope.launch {
                apiTranslationRepository.getTranslations(query)
                    .catch { error->
                        _mainScreenUiState.value = MainScreenUiState(
                            "",
                            "Во время запроса произошла ошибка, повторите попытку позже"
                        )
                        Log.d(TAG, error.message ?: "unknown error")
                    }
                    .collect{ words->
                        if (words.isEmpty()){
                            _mainScreenUiState.value = MainScreenUiState(
                                "",
                                "Для введённого слова не найден перевод"
                            )
                        } else {
                            _mainScreenUiState.value = MainScreenUiState(
                                words[0].text,
                                ""
                            )
                        }
                    }
            }
        } else {

            _mainScreenUiState.value = MainScreenUiState(
                "",
                "Введите одно корректное слово на русском языке"
            )
        }
    }

    companion object{
        const val TAG = "MainScreenViewModelTag"
    }
}