package com.dmitrysergeev.translateapp.data.translation.network

import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import com.dmitrysergeev.translateapp.data.translation.network.model.WordTranslationApi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class RetrofitTranslationDataSourceTest {

    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var skyEngApi: SkyEngApi

    @InjectMockKs
    lateinit var retrofitTranslationDataSource: RetrofitTranslationDataSource

    @Test
    fun getTranslationCallsEndpointAndReturnsTranslations() = runTest{
        // arrange
        val query = "Стол"
        coEvery { skyEngApi.getMeanings(query) } returns getListOfWordTranslationsApi()

        // act
        val translationsFlow = retrofitTranslationDataSource.getTranslations("Стол")
        val emittedValues = mutableListOf<List<WordTranslation>>()
        translationsFlow.collect{ list->
            emittedValues.add(list)
        }

        // assert
        assertEquals(
            listOf(
                WordTranslation(id = 0, originalWord = "Стол", translation = "table"),
                WordTranslation(id = 1, originalWord = "Стол", translation = "wolf"),
                WordTranslation(id = 2, originalWord = "Стол", translation = "soccer")
            ),
            emittedValues[0]
        )
        assertEquals(emittedValues.size,1)

        coVerify(exactly = 1) {
            skyEngApi.getMeanings(query)
        }
        confirmVerified(skyEngApi)
    }

    @Test
    fun `get empty translations from endpoint for unknown word`() = runTest {
        val query = "СловоКотороеНеИмеетПеревода"
        coEvery { skyEngApi.getMeanings(query) } returns emptyList()

        val translationsFlow = retrofitTranslationDataSource.getTranslations("СловоКотороеНеИмеетПеревода")
        val emittedValues = mutableListOf<List<WordTranslation>>()
        translationsFlow.collect{ list->
            emittedValues.add(list)
        }

        assertEquals(
            emptyList<WordTranslation>(),
            emittedValues[0]
        )
        assertEquals(emittedValues.size,1)

        coVerify(exactly = 1) {
            skyEngApi.getMeanings(query)
        }
        confirmVerified(skyEngApi)
    }

    private fun getListOfWordTranslationsApi(): List<WordTranslationApi> = listOf(
        getWordTranslation1(), getWordTranslation2(), getWordTranslation3()
    )

    private fun getWordTranslation1(): WordTranslationApi = WordTranslationApi(
        id = 0,
        text = "table"
    )

    private fun getWordTranslation2(): WordTranslationApi = WordTranslationApi(
        id = 1,
        text = "wolf"
    )

    private fun getWordTranslation3(): WordTranslationApi = WordTranslationApi(
        id = 2,
        text = "soccer"
    )
}