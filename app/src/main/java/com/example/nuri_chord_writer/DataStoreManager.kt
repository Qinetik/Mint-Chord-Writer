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

class DataStoreManager(context: Context) {
    private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name="SONGS")
    private val dataStore = context.dataStore

    suspend fun getSongIds() {
        val stringIds = this.read("song_ids")
        Log.d("aaa", stringIds.toString())
    }

    suspend fun save(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        dataStore.edit {songs ->
            songs[dataStoreKey] = value
        }
    }

    private suspend fun read(key: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
        /*
        return dataStore.data
            .catch { exception ->
                throw exception
            }
            .map {pref ->
                Log.d("aaa", "asdfasdfasdf")
                val values = pref[songs] ?: "it doesnt exist bro"
                values
            }.toString()*/
    }


}