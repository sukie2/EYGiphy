package com.sukitha.ey.eygiphy.data.local

import TestDispatcherRule
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.sukitha.ey.eygiphy.domain.data.Giphy
import junit.framework.Assert.assertFalse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class GiphyDaoTest {

    @get: Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var itemDao: GiphyDao
    private lateinit var itemDb: GiphyDatabase

    @Before
    fun create() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        itemDb = Room
            .inMemoryDatabaseBuilder(context, GiphyDatabase::class.java)
            .build()
        itemDao = itemDb.giphyDao
    }

    @After
    fun cleanup() {
        itemDb.close()
    }

    @Test
    fun addFavouriteGiphy_shouldReturn_theItem_inFlow() = runTest {
        val item1 = Giphy(id = "1", title = "happy", url = "")
        val item2 = Giphy(id = "2", title = "hello", url = "")
        itemDao.insertGiphy(item1)
        itemDao.insertGiphy(item2)

        itemDao.getGiphy().test {
            val list = awaitItem()
            assert(list.contains(item1))
            assert(list.contains(item2))
            cancel()
        }
    }

    @Test
    fun removeFavouriteGiphy_shouldNotReturn_theItem_inFlow() = runTest {
        val item1 = Giphy(id = "1", title = "happy", url = "")
        val item2 = Giphy(id = "2", title = "hello", url = "")
        itemDao.insertGiphy(item1)
        itemDao.insertGiphy(item2)

        itemDao.deleteGiphy(item2)

        itemDao.getGiphy().test {
            val list = awaitItem()
            assertFalse(list.contains(item2))
            cancel()
        }
    }
}