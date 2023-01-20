package com.example.nuri_chord_writer

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name="SONGS")

class DataStoreManager(context: Context) {

    private val dataStore = context.dataStore

    suspend fun resetData() {
        dataStore.edit {
            it.clear()
        }
    }

    suspend fun getSongIds(): String {
        val stringIds = this.read("song_ids")
        return stringIds.toString()
    }

    suspend fun save(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        try {
            dataStore.edit { songs ->
                songs[dataStoreKey] = value
            }
        } catch (error: IOException) {
            Log.d("aaa", error.toString())
        }
    }

    suspend fun read(key: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    suspend fun remove(key: String) {
        dataStore.edit {
            it.remove(stringPreferencesKey(key))
        }
    }


}