package com.dmitrysergeev.translateapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.dmitrysergeev.translateapp.data.translation.api.ApiTranslationRepository
import com.dmitrysergeev.translateapp.data.translation.api.SkyEngApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val okHttpClient = OkHttpClient.Builder()
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://dictionary.skyeng.ru/api/public/v1/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val skyEngApi = retrofit.create<SkyEngApi>()
        val apiTranslationRepository = ApiTranslationRepository(skyEngApi)

        lifecycleScope.launch {
            apiTranslationRepository.getTranslations("нога")
                .catch { error->
                    Log.d(TAG, error.message ?: "Unknown Error")
                }
                .collect{ words->
                    Log.d(TAG, words.toString())
                }
        }
    }

    companion object{
        const val TAG = "MainActivityTag"
    }
}