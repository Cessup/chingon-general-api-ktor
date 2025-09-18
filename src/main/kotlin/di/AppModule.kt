package com.cessup.di

import com.cessup.data.database.MongoConfig
import com.cessup.data.repositories.EatableRepositoryImpl
import com.cessup.data.repositories.ProductRepositoryImpl
import com.cessup.data.services.Encrypt
import com.cessup.data.repositories.UserRepositoryImpl
import com.cessup.domain.repositories.EatableRepository
import com.cessup.domain.repositories.ProductRepository
import com.cessup.domain.repositories.UserRepository
import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import org.yaml.snakeyaml.Yaml

/**
 * AppModule is a class with some modules for injection in this application.
 *
 * @author
 *     Cessup
 * @since 1.0
 */
class AppModule() : AbstractModule() {

    /**
     * This function start to configure the framework to mongo
     * @return [MongoConfig] the objet to use the database
     */
    @Provides
    @Singleton
    fun provideMongoDB(): MongoConfig {
        val input = Thread.currentThread().contextClassLoader.getResourceAsStream("application.yaml")
        val config = Yaml().load<Map<String, Any>>(input)

        val ktor = config["ktor"] as Map<*, *>
        val custom = ktor["mongodb"] as Map<*, *>

        return MongoConfig(
            custom["databaseUri"].toString(),
            custom["sessionDB"].toString(),
            custom["productDB"].toString(),
            custom["eatableDB"].toString()
        )
    }

    /**
     * This function to encrypt
     *
     * @return [Encrypt] the object to use the encryption
     */
    @Provides
    @Singleton
    fun provideEncrypt(): Encrypt = Encrypt

    /**
     * This function start to configure the framework
     *
     * @param MongoConfig the MongoConfig got configuration about database
     *
     * @return [UserRepository] the object to use the User Repository
     */
    @Provides @Singleton
    fun provideUserRepository(mongoConfig:MongoConfig): UserRepository =
        UserRepositoryImpl(mongoConfig.getUserDb() )

    /**
     * This function start to configure the framework
     *
     * @param MongoConfig the MongoConfig got configuration about database
     *
     * @return [UserRepository] the object to use the User Repository
     */
    @Provides @Singleton
    fun provideProductRepository(mongoConfig:MongoConfig): ProductRepository =
        ProductRepositoryImpl(mongoConfig.getProductsDB())

    /**
     * This function start to configure the framework
     *
     * @param MongoConfig the MongoConfig got configuration about database
     *
     * @return [UserRepository] the object to use the User Repository
     */
    @Provides @Singleton
    fun provideDrinkRepository(mongoConfig:MongoConfig): EatableRepository =
        EatableRepositoryImpl(mongoConfig.getEatableDB())
}
