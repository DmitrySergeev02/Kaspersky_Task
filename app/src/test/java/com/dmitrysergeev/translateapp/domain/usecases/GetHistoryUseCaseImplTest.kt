package com.dmitrysergeev.translateapp.domain.usecases

import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import com.dmitrysergeev.translateapp.domain.translation.TranslationRepository
import com.dmitrysergeev.translateapp.domain.usecases.gethistoryusecase.GetHistoryUseCase
import com.dmitrysergeev.translateapp.domain.usecases.gethistoryusecase.GetHistoryUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetHistoryUseCaseImplTest {

    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var translationRepository: TranslationRepository

    val dispatcher = Dispatchers.Unconfined

    lateinit var getHistoryUseCase: GetHistoryUseCase

    @Before
    fun setup(){
        getHistoryUseCase = GetHistoryUseCaseImpl(translationRepository, dispatcher)
    }

    @Test
    fun invokeWhenEmptyHistory() = runTest{
        val expectedResult = emptyList<WordTranslation>()
        coEvery { translationRepository.getHistory() } returns flow {
            emit(expectedResult)
        }

        val emittedValues = mutableListOf<List<WordTranslation>>()
        getHistoryUseCase()
            .collect{ list->
                emittedValues.add(list)
            }

        assertEquals(1,emittedValues.size)
        assertEquals(expectedResult, emittedValues[0])
        coVerify(exactly = 1) {
            translationRepository.getHistory()
        }
        confirmVerified()
    }

    @Test
    fun invokeWhenHistoryItemsExists() = runTest{
        val expectedResult = listOf(
            WordTranslation(0,"собака","dog"),
            WordTranslation(1,"мяч","ball"),
        )
        coEvery { translationRepository.getHistory() } returns flow {
            emit(expectedResult)
        }

        val emittedValues = mutableListOf<List<WordTranslation>>()
        getHistoryUseCase()
            .collect{ list->
                emittedValues.add(list)
            }

        assertEquals(1,emittedValues.size)
        assertEquals(expectedResult[0], emittedValues[0][0])
        assertEquals(expectedResult[1], emittedValues[0][1])
        coVerify(exactly = 1) {
            translationRepository.getHistory()
        }
        confirmVerified()
    }



}