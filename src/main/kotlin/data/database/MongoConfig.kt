package com.cessup.data.database

import com.google.inject.Inject
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

class MongoConfig @Inject constructor(connectionString: String,
                                       userDBString: String,
                                       storeDBString: String) {

    private val client : CoroutineClient = KMongo.createClient(connectionString).coroutine
    private val userDB : CoroutineDatabase = client.getDatabase(userDBString)
    private val storageDB : CoroutineDatabase = client.getDatabase(storeDBString)

    fun getClient(): CoroutineClient = client

    fun getUserDb() : CoroutineDatabase = userDB

    fun getStoreDB() : CoroutineDatabase = storageDB

}