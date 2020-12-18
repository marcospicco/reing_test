package com.marcospicco.reingtest.core.actions

import android.content.Context
import android.os.Build
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class DeleteItemTest {

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun doAction() {
        val prefs = RuntimeEnvironment.application.getSharedPreferences(
            "prefs",
            Context.MODE_PRIVATE
        )

        DeleteItem().doAction(prefs, "1")
        DeleteItem().doAction(prefs, "2")

        val result = GetDeletedItems().doAction(prefs)

        assertEquals(result!!.size, 2)
        assertEquals(result[0], "1")
        assertEquals(result[1], "2")
    }
}