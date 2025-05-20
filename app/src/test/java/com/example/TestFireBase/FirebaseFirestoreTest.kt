package com.example.TestFireBase
import com.google.firebase.firestore.FirebaseFirestore
import org.junit.Assert.*
import org.junit.Test

class FirebaseFirestoreTest {

    @Test
    fun testFirestoreInstance() {
        val firestore = FirebaseFirestore.getInstance()
        assertNotNull(firestore)
    }
}
