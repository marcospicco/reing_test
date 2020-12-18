package com.marcospicco.reingtest.presentation.viewmodel

import android.content.SharedPreferences
import com.marcospicco.reingtest.core.actions.*
import com.marcospicco.reingtest.core.domain.ListModel
import com.marcospicco.reingtest.core.infrastructure.representation.ListItemRepresentation
import com.marcospicco.reingtest.core.utils.LifecycleOwnerTest
import com.marcospicco.reingtest.core.utils.StubSchedulerProvider
import com.marcospicco.reingtest.presentation.list.viewmodel.ListViewModel
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException

@RunWith(MockitoJUnitRunner::class)
class ListViewModelTest {

    @Mock
    private val sharedPreferences = Mockito.mock(SharedPreferences::class.java)

    private val schedulerProvider = StubSchedulerProvider()

    @Mock
    private val getList = Mockito.mock(GetList::class.java)

    @Mock
    private val getLocalList = Mockito.mock(GetLocalList::class.java)

    @Mock
    private val deleteItem = Mockito.mock(DeleteItem::class.java)

    private lateinit var viewModel: ListViewModel

    private val lifecycleOwner = LifecycleOwnerTest()

    private lateinit var listModel: ListModel

    private lateinit var platform: String

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        platform = "android"

        viewModel = Mockito.spy(
            ListViewModel(
                sharedPreferences,
                schedulerProvider,
                getList,
                getLocalList,
                deleteItem,
                platform
            )
        )

        lifecycleOwner.lifecycle.addObserver(viewModel)

        listModel = ListModel(
            mutableListOf(
                ListItemRepresentation(
                    "25469774",
                    "The Ampere Altra Review: 2x 80 Cores Arm Server Performance Monster",
                    null,
                    "random5634",
                    "2020-12-18T17:49:55.000Z",
                    "https://www.anandtech.com/show/16315/the-ampere-altra-review",
                    null
                ).toModel(),
                ListItemRepresentation(
                    "25469399",
                    "Facebook's Hypocrisy on Apple's New iOS 14 Privacy Feature",
                    null,
                    "doix",
                    "2020-12-18T17:21:35.000Z",
                    "https://thebigtech.substack.com/p/facebook-criticising-apples-ios-14",
                    null
                ).toModel(),
                ListItemRepresentation(
                    "25469365",
                    "Cyberpunk 2077 Refunds",
                    null,
                    "andrepd",
                    "2020-12-18T17:19:27.000Z",
                    "https://www.playstation.com/en-us/cyberpunk-2077-refunds/",
                    null
                ).toModel()
            )
        )
    }

    @Test
    fun getListSuccess() {

        val single = Single.just(listModel)
        Mockito.`when`(getList.doAction(sharedPreferences, platform)).thenReturn(single)

        lifecycleOwner.onCreate()
        schedulerProvider.triggerActions()

        verify(getList).doAction(sharedPreferences, platform)
        verify(viewModel).renderScreen(listModel)
        verifyNoMoreInteractions(getList)
    }

    @Test
    fun getListError() {

        val runtimeException = RuntimeException()
        Mockito.`when`(getList.doAction(sharedPreferences, platform))
            .thenReturn(Single.error(runtimeException))

        lifecycleOwner.onCreate()
        schedulerProvider.triggerActions()

        verify(getList).doAction(sharedPreferences, platform)
        verify(viewModel).onError(runtimeException)
    }

    @Test
    fun deleteItem() {

        doNothing().`when`(deleteItem).doAction(sharedPreferences, "1")

        viewModel.deleteItem("1")

        verify(deleteItem).doAction(sharedPreferences, "1")
        verifyNoMoreInteractions(deleteItem)
        verifyNoMoreInteractions(sharedPreferences)
        verifyNoMoreInteractions(getList)
        verifyNoMoreInteractions(getLocalList)
    }
}