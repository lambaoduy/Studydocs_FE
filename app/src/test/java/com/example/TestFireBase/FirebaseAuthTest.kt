package com.example.TestFireBase

import com.google.firebase.auth.FirebaseAuth
import org.junit.Assert.*


class FirebaseAuthTest {


    fun testFirebaseAuthInstance() {
        val auth = FirebaseAuth.getInstance()
        assertNotNull(auth)
    }
}
