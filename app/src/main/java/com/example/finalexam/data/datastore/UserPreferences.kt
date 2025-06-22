package com.example.finalexam.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.finalexam.entity.User
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

// Tạo DataStore instance
private val Context.dataStore by preferencesDataStore(name = "user_prefs")

object UserPreferences {
    private lateinit var appContext: Context
    fun init(context: Context) {
        appContext = context.applicationContext
    }

    private val USER_ID_KEY = stringPreferencesKey("user_id")
    private val TOKEN_KEY = stringPreferencesKey("token_id")
    private val FCM_TOKEN_KEY = stringPreferencesKey("fcm_token")
    private val USER_KEY = stringPreferencesKey("user")
    private val gson = Gson()

    // Lưu userId
    suspend fun saveUser(userId: String, token: String) {
        appContext.dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = userId
            prefs[TOKEN_KEY] = token
        }
        UserProvider.setUserId(userId)
        UserProvider.setToken(token)
    }

    suspend fun saveUserObject(user: User) {
        val json = gson.toJson(user)
        appContext.dataStore.edit { prefs ->
            prefs[USER_KEY] = json
        }
        UserProvider.setUser(user)
    }


    suspend fun saveFcmToken(token: String) {
        appContext.dataStore.edit { prefs ->
            prefs[FCM_TOKEN_KEY] = token
        }
    }

    suspend fun getUser(): User? {
        val json = appContext.dataStore.data
            .map { it[USER_KEY] }
            .firstOrNull()

        return json?.let { gson.fromJson(it, User::class.java) }
    }

    fun getUserId(): Flow<String?> =
        appContext.dataStore.data.map { prefs -> prefs[USER_ID_KEY] }

    fun getToken(): Flow<String?> =
        appContext.dataStore.data.map { prefs -> prefs[TOKEN_KEY] }

    suspend fun getFcmToken(): String? =
        appContext.dataStore.data.map { prefs -> prefs[FCM_TOKEN_KEY] }.first()

    // Xóa userId (logout)
    suspend fun clear() {
        appContext.dataStore.edit { it.clear() }
        UserProvider.clear()
    }
}