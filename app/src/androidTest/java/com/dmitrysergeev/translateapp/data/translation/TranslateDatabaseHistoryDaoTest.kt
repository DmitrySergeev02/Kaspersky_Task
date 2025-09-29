package com.dmitrysergeev.translateapp.data.translation

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dmitrysergeev.translateapp.data.translation.db.TranslateDatabase
import com.dmitrysergeev.translateapp.data.translation.db.history.HistoryDao
import com.dmitrysergeev.translateapp.data.translation.db.history.HistoryDbEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TranslateDatabaseHistoryDaoTest {

    private lateinit var historyDao: HistoryDao
    private lateinit var db: TranslateDatabase

    @Before
    fun createDatabase(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            TranslateDatabase::class.java
        ).build()
        historyDao = db.historyDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test fun getEmptyHistory() = runBlocking {
        val historyItems = historyDao.getHistory().first()

        assertEquals(0,historyItems.size)
    }

    @Test
    fun insertOneHistoryItemInDatabase() = runBlocking {
        val historyItem = getHistoryDbEntity1()

        historyDao.addHistoryItem(historyItem)
        val firstHistoryItems = historyDao.getHistory().first()
        val firstHistoryItem = firstHistoryItems[0]

        assertEquals(
            historyItem.baseWord,
            firstHistoryItem.baseWord
        )
        assertEquals(
            historyItem.translation,
            firstHistoryItem.translation,
        )
        assertEquals(
            1,
            firstHistoryItems.size,
        )
    }

    @Test
    fun insertTwoHistoryItemsInDatabase() = runBlocking {
        val historyItem1 = getHistoryDbEntity1()
        val historyItem2 = getHistoryDbEntity2()

        historyDao.addHistoryItem(historyItem1)
        val firstHistoryItems = historyDao.getHistory().first()
        val firstHistoryItem1 = firstHistoryItems[0]

        historyDao.addHistoryItem(historyItem2)
        val secondHistoryItems = historyDao.getHistory().first()
        val firstHistoryItem2 = secondHistoryItems[1]

        assertEquals(historyItem1.baseWord, firstHistoryItem1.baseWord)
        assertEquals(historyItem1.translation, firstHistoryItem1.translation)
        assertEquals(1, firstHistoryItems.size)
        assertEquals(historyItem2.baseWord, firstHistoryItem2.baseWord)
        assertEquals(historyItem2.translation, firstHistoryItem2.translation)
        assertEquals(2, secondHistoryItems.size)
    }

    @Test
    fun insertTwoHistoryItemsInDatabaseAndDeleteFirst() = runBlocking {
        val historyItem1 = getHistoryDbEntity1()
        val historyItem2 = getHistoryDbEntity2()

        historyDao.addHistoryItem(historyItem1)
        historyDao.addHistoryItem(historyItem2)
        val historyItemsAfterInsert = historyDao.getHistory().first()
        historyDao.deleteHistoryItem(historyItemsAfterInsert[0])
        val historyItemsAfterDelete = historyDao.getHistory().first()

        assertEquals(1,historyItemsAfterDelete.size)
        assertEquals(historyItem2.baseWord, historyItemsAfterDelete[0].baseWord)
        assertEquals(historyItem2.translation, historyItemsAfterDelete[0].translation)
    }

    @Test
    fun insertTwoHistoryItemsInDatabaseAndDeleteAll() = runBlocking {
        val historyItem1 = getHistoryDbEntity1()
        val historyItem2 = getHistoryDbEntity2()

        historyDao.addHistoryItem(historyItem1)
        historyDao.addHistoryItem(historyItem2)
        val historyItemsAfterInsert = historyDao.getHistory().first()
        historyDao.deleteHistoryItem(historyItemsAfterInsert[0])
        historyDao.deleteHistoryItem(historyItemsAfterInsert[1])
        val historyItemsAfterDelete = historyDao.getHistory().first()

        assertEquals(0, historyItemsAfterDelete.size)
    }

    private fun getHistoryDbEntity1(): HistoryDbEntity = HistoryDbEntity(
        0,
        "стол",
        "table"
    )

    private fun getHistoryDbEntity2(): HistoryDbEntity = HistoryDbEntity(
        0,
        "волк",
        "wolf"
    )
}