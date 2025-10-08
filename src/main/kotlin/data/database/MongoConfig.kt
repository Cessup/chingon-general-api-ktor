package com.cessup.data.database

import com.google.inject.Inject
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import com.mongodb.reactivestreams.client.MongoDatabase

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
                                      val userDBString: String,
                                      val productDBString: String,
                                      val eatableDBString: String
    ) {



    val mongoClient: MongoClient = MongoClients.create(connectionString)
    /**
     * This function give a collection from database
     * User collection have information about users
     */
    val userDB: MongoDatabase
        get() = mongoClient.getDatabase(userDBString)
    /**
     * This function give a collection from database
     * Product is a collection from db
     */
    val productsDB: MongoDatabase
        get()= mongoClient.getDatabase(productDBString)
    /**
     * This function give a database
     * Eatable is a database
     */
    val eatableDB: MongoDatabase
        get()= mongoClient.getDatabase(eatableDBString)

}