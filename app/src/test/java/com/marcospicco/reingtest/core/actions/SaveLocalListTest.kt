package com.marcospicco.reingtest.core.actions

import android.content.Context.MODE_PRIVATE
import android.os.Build
import com.marcospicco.reingtest.core.infrastructure.representation.ListItemRepresentation
import com.marcospicco.reingtest.core.infrastructure.representation.ListRepresentation
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class SaveLocalListTest {

    @Mock
    private val getDeletedItems = Mockito.mock(GetDeletedItems::class.java)

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
    fun doActionTest() {
        val listModel = listRepresentation.toModel()

        val prefs = RuntimeEnvironment.application.getSharedPreferences("prefs", MODE_PRIVATE)

        SaveLocalList().doAction(prefs, listModel)

        val result = GetLocalList(getDeletedItems).doAction(prefs)

        assertEquals(result.list.size, listModel.list.size)
        assertEquals(result.list[0].id, listModel.list[0].id)
        assertEquals(result.list[1].id, listModel.list[1].id)
        assertEquals(result.list[2].id, listModel.list[2].id)
    }
}