package com.cessup.di

import com.cessup.data.database.MongoConfig
import com.cessup.data.services.Encrypt
import com.cessup.data.repositories.UserRepositoryImpl
import com.cessup.domain.repositories.UserRepository
import com.cessup.domain.usecases.SignInUseCase
import com.cessup.domain.usecases.SignUpUseCase
import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Singleton
import org.yaml.snakeyaml.Yaml


class AppModule() : AbstractModule() {

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
            custom["storeDB"].toString()
        )
    }

    @Provides
    @Singleton
    fun provideEncrypt(): Encrypt = Encrypt

    @Provides @Singleton
    fun provideUserRepository(mongoConfig:MongoConfig): UserRepository =
        UserRepositoryImpl(mongoConfig.getUserDb())

    @Provides
    fun provideSignUpUseCase(userRepository: UserRepository, encrypt: Encrypt): SignUpUseCase =
        SignUpUseCase(userRepository, encrypt)

    @Provides
    fun provideSignInUseCase(userRepository: UserRepository): SignInUseCase =
        SignInUseCase(userRepository)
}
