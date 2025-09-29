package com.dmitrysergeev.translateapp.ui.favouritesscreen

import com.dmitrysergeev.translateapp.data.translation.db.favourites.BaseWordAndTranslation
import com.dmitrysergeev.translateapp.data.translation.entities.WordTranslation
import com.dmitrysergeev.translateapp.domain.usecases.deletefavouritebybasewordandtranslationusecase.DeleteFavouriteByBaseWordAndTranslationUseCase
import com.dmitrysergeev.translateapp.domain.usecases.getfavouritetranslationsusecase.GetFavouriteTranslationsUseCase
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class FavouritesScreenViewModelTest {

    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var getFavouriteTranslationsUseCase: GetFavouriteTranslationsUseCase

    @MockK
    lateinit var deleteFavouriteByBaseWordAndTranslationUseCase: DeleteFavouriteByBaseWordAndTranslationUseCase

    fun getMutableListOfWordTranslation(): MutableList<WordTranslation> =  mutableListOf(
        WordTranslation(0,"собака", "dog"),
        WordTranslation(0,"рука", "hand"),
        WordTranslation(0,"мяч", "ball")
    )

    @Test
    fun initWithSomeFavourites() = runBlocking {
        val favourites = getMutableListOfWordTranslation()
        coEvery { getFavouriteTranslationsUseCase() } returns flowOf(favourites)

        val viewModel = FavouritesScreenViewModel(
            getFavouriteTranslationsUseCase,
            deleteFavouriteByBaseWordAndTranslationUseCase
        )

        // Задержка для инициализации
        delay(1000)

        val favouritesList = viewModel.favouriteItems.value

        assertEquals(favourites.reversed(), favouritesList)
    }

    @Test
    fun updateStateFlowWhenFavouritesDeleted() = runBlocking {
        val favouritesFlow = MutableStateFlow(getMutableListOfWordTranslation())
        val wordTranslationToDelete = favouritesFlow.value[1]
        val expectedFavourites = getMutableListOfWordTranslation().also { it.removeAt(1) }
        coEvery { getFavouriteTranslationsUseCase() } returns favouritesFlow
        coEvery {
            deleteFavouriteByBaseWordAndTranslationUseCase(
                BaseWordAndTranslation(wordTranslationToDelete.originalWord, wordTranslationToDelete.translation)
            )
        } answers {
            val current = favouritesFlow.value.toMutableList()
            current.remove(wordTranslationToDelete)
            favouritesFlow.value = current
        }
        val viewModel = FavouritesScreenViewModel(
            getFavouriteTranslationsUseCase,
            deleteFavouriteByBaseWordAndTranslationUseCase
        )

        delay(1000)

        viewModel.deleteFromFavourites(wordTranslationToDelete)

        delay(1000)

        val favouritesList = viewModel.favouriteItems.value

        assertEquals(expectedFavourites.reversed(), favouritesList)
    }


}