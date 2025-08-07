package com.cessup.data.database

import com.google.inject.Inject
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

/**
 * MongoConfig is a object about database configuration in this case is mongodb
 *
 * There are configurations for this framework
 *
 * @author
 *     Cessup
 * @since 1.0
 */

class MongoConfig @Inject constructor(connectionString: String,
                                       userDBString: String,
                                       productDBString: String) {

    private val client : CoroutineClient = KMongo.createClient(connectionString).coroutine
    private val userDB : CoroutineDatabase = client.getDatabase(userDBString)
    private val productsDB : CoroutineDatabase = client.getDatabase(productDBString)

    /**
     * This function give a client from database
     *
     * @return [CoroutineClient] the object is a client from database
     */
    fun getClient(): CoroutineClient = client

    /**
     * This function give a collection from database
     * User collection have information about users
     *
     * @return [CoroutineDatabase] the object is a collection from database
     */
    fun getUserDb() : CoroutineDatabase = userDB

    /**
     * This function give a collection from database
     * Product is a collection from db
     *
     * @return [CoroutineDatabase] the object is a collection from database
     */
    fun getProductsDB() : CoroutineDatabase = productsDB

}