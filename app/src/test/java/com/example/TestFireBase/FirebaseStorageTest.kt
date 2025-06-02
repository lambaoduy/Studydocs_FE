package com.example.TestFireBase
import com.google.firebase.storage.FirebaseStorage
import org.junit.Assert.*
import org.junit.Test

class FirebaseStorageTest {

    @Test
    fun testStorageInstance() {
        val storage = FirebaseStorage.getInstance()
        assertNotNull(storage)
    }
}
