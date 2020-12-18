package com.marcospicco.reingtest.core.actions

import android.content.SharedPreferences
import com.marcospicco.reingtest.core.domain.ListModel
import com.marcospicco.reingtest.core.infrastructure.api.ListService
import com.marcospicco.reingtest.core.infrastructure.representation.ListItemRepresentation
import com.marcospicco.reingtest.core.infrastructure.representation.ListRepresentation
import io.reactivex.SingleObserver
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException

@RunWith(MockitoJUnitRunner::class)
class GetListTest {

    @Mock
    private val listService = Mockito.mock(ListService::class.java)

    @Mock
    private val saveList = Mockito.mock(SaveLocalList::class.java)

    @Mock
    private val getDeletedItems = Mockito.mock(GetDeletedItems::class.java)

    @Mock
    lateinit var observer: SingleObserver<in ListModel>

    @Mock
    private val prefs = Mockito.mock(SharedPreferences::class.java)

    private lateinit var listRepresentation: ListRepresentation

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        val listItemRepresentation = mutableListOf(
            ListItemRepresentation(
                "25469774",
                "The Ampere Altra Review: 2x 80 Cores Arm Server Performance Monster",
                null,
                "random5634",
                "2020-12-18T17:49:55.000Z",
                "https://www.anandtech.com/show/16315/the-ampere-altra-review",
                null
            ),
            ListItemRepresentation(
                "25469399",
                "Facebook's Hypocrisy on Apple's New iOS 14 Privacy Feature",
                null,
                "doix",
                "2020-12-18T17:21:35.000Z",
                "https://thebigtech.substack.com/p/facebook-criticising-apples-ios-14",
                null
            ),
            ListItemRepresentation(
                "25469365",
                "Cyberpunk 2077 Refunds",
                null,
                "andrepd",
                "2020-12-18T17:19:27.000Z",
                "https://www.playstation.com/en-us/cyberpunk-2077-refunds/",
                null
            )
        )

        listRepresentation = ListRepresentation(listItemRepresentation)
    }

    @Test
    fun successWithoutDeltedItemsResponse() {
        val platform = "android"
        Mockito.`when`(listService.getList(platform)).thenReturn(listRepresentation)
        Mockito.`when`(getDeletedItems.doAction(prefs)).thenReturn(null)

        val result = GetList(listService, saveList, getDeletedItems).doAction(prefs, platform).blockingGet()

        assertEquals(result.list.size, 3)
        verify(listService).getList(platform)
        verify(getDeletedItems).doAction(prefs)
        verify(saveList).doAction(prefs, result)
        verifyNoMoreInteractions(listService)
        verifyNoMoreInteractions(getDeletedItems)
        verifyNoMoreInteractions(saveList)
    }

    @Test
    fun successWithDeltedItemsResponse() {
        val platform = "android"
        Mockito.`when`(listService.getList(platform)).thenReturn(listRepresentation)
        Mockito.`when`(getDeletedItems.doAction(prefs)).thenReturn(mutableListOf("25469399"))

        val result = GetList(listService, saveList, getDeletedItems).doAction(prefs, platform).blockingGet()

        assertEquals(result.list.size, 2)
        verify(listService).getList(platform)
        verify(getDeletedItems).doAction(prefs)
        verify(saveList).doAction(prefs, result)
        verifyNoMoreInteractions(listService)
        verifyNoMoreInteractions(getDeletedItems)
        verifyNoMoreInteractions(saveList)
    }

    @Test
    fun errorResponse() {
        val platform = "android"

        Mockito.`when`(listService.getList(platform)).thenThrow(RuntimeException())

        GetList(listService, saveList, getDeletedItems).doAction(prefs, platform).subscribe(observer)
        verify(observer).onError(Mockito.any())
    }
}