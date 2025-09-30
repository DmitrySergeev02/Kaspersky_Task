package com.dmitrysergeev.translateapp.data.translation

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dmitrysergeev.translateapp.data.translation.db.TranslateDatabase
import com.dmitrysergeev.translateapp.data.translation.db.favourites.BaseWordAndTranslation
import com.dmitrysergeev.translateapp.data.translation.db.favourites.FavouriteDao
import com.dmitrysergeev.translateapp.data.translation.db.favourites.FavouriteDbEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TranslateDatabaseFavouriteDaoTest {

    private lateinit var favouriteDao: FavouriteDao
    private lateinit var db: TranslateDatabase

    @Before
    fun createDatabase(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            TranslateDatabase::class.java
        ).build()
        favouriteDao = db.favouriteDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    @Test
    fun getFavouritesEmpty() = runBlocking{
        val favouriteItems = favouriteDao.getFavourites().first()

        assertEquals(0, favouriteItems.size)
    }

    @Test
    fun insertOneFavouriteItem() = runBlocking{
        val favouriteItem = getFavouriteDbEntity1()
        val favouriteItemsBeforeInsertion = favouriteDao.getFavourites().first()

        favouriteDao.addFavourite(favouriteItem)
        val favouriteItemsAfterInsertion = favouriteDao.getFavourites().first()

        assertEquals(0, favouriteItemsBeforeInsertion.size)
        assertEquals(1, favouriteItemsAfterInsertion.size)
        assertEquals(favouriteItem.baseWord, favouriteItemsAfterInsertion[0].baseWord)
        assertEquals(favouriteItem.translation, favouriteItemsAfterInsertion[0].translation)
    }

    @Test
    fun insertTwoFavouriteItems() = runBlocking {
        val favouriteItem1 = getFavouriteDbEntity1()
        val favouriteItem2 = getFavouriteDbEntity2()

        favouriteDao.addFavourite(favouriteItem1)
        favouriteDao.addFavourite(favouriteItem2)
        val favouriteItemsAfterSecondInsertion = favouriteDao.getFavourites().first()

        assertEquals(2,favouriteItemsAfterSecondInsertion.size)
        assertEquals(favouriteItem1.baseWord, favouriteItemsAfterSecondInsertion[0].baseWord)
        assertEquals(favouriteItem1.translation, favouriteItemsAfterSecondInsertion[0].translation)
        assertEquals(favouriteItem2.baseWord, favouriteItemsAfterSecondInsertion[1].baseWord)
        assertEquals(favouriteItem2.translation, favouriteItemsAfterSecondInsertion[1].translation)
    }

    @Test
    fun deleteFavouriteByBaseWordAndTranslation() = runBlocking {
        val favouriteItem1 = getFavouriteDbEntity1()
        val favouriteItem2 = getFavouriteDbEntity2()
        val baseWordAndTranslation = BaseWordAndTranslation(favouriteItem1.baseWord,favouriteItem1.translation)

        favouriteDao.addFavourite(favouriteItem1)
        favouriteDao.addFavourite(favouriteItem2)
        favouriteDao.deleteFavouriteByBaseWordAndTranslation(baseWordAndTranslation)
        val favouriteItemsAfterDelete = favouriteDao.getFavourites().first()

        assertEquals(1, favouriteItemsAfterDelete.size)
        assertEquals(favouriteItem2.baseWord, favouriteItemsAfterDelete[0].baseWord)
        assertEquals(favouriteItem2.translation, favouriteItemsAfterDelete[0].translation)
    }

    @Test
    fun getFavouriteByBaseWordAndTranslationWhenItExists() = runBlocking {
        val favouriteItem1 = getFavouriteDbEntity1()
        val favouriteItem2 = getFavouriteDbEntity2()
        val baseWord = favouriteItem1.baseWord
        val translation = favouriteItem1.translation

        favouriteDao.addFavourite(favouriteItem1)
        favouriteDao.addFavourite(favouriteItem2)
        val emittedValue = favouriteDao.getFavouriteByBaseWordAndTranslation(baseWord, translation).first()

        assertEquals(baseWord, emittedValue!!.baseWord)
        assertEquals(translation, emittedValue.translation)
    }

    @Test
    fun getFavouriteByBaseWordAndTranslationWhenItDoesNotExists() = runBlocking {
        val favouriteItem1 = getFavouriteDbEntity1()
        val favouriteItem2 = getFavouriteDbEntity2()
        val baseWord = favouriteItem1.baseWord
        val translation = favouriteItem1.translation

        favouriteDao.addFavourite(favouriteItem2)
        val emittedValue = favouriteDao.getFavouriteByBaseWordAndTranslation(baseWord, translation).first()

        assertNull(emittedValue)
    }

    fun getFavouriteDbEntity1(): FavouriteDbEntity = FavouriteDbEntity(
        0,
        "table",
        "стол"
    )

    fun getFavouriteDbEntity2(): FavouriteDbEntity = FavouriteDbEntity(
        0,
        "dog",
        "собака"
    )


}