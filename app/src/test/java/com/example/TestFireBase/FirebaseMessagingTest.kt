package com.example.TestFireBase

import com.google.firebase.messaging.FirebaseMessaging
import org.junit.Assert.*
import org.junit.Test

class FirebaseMessagingTest {

    @Test
    fun testMessagingInstance() {
        val messaging = FirebaseMessaging.getInstance()
        assertNotNull(messaging)
    }
}
