package com.cessup.di

import com.cessup.data.database.MongoConfig
import com.cessup.data.repositories.EatableRepositoryImpl
import com.cessup.data.repositories.ProductRepositoryImpl
import com.cessup.domain.repositories.UserRepository
import com.cessup.data.repositories.UserRepositoryImpl
import com.cessup.data.services.Security
import com.cessup.domain.repositories.EatableRepository
import com.cessup.domain.repositories.ProductRepository
import com.cessup.domain.usecases.eatable.drink.DeleteDrinkUseCase
import com.cessup.domain.usecases.eatable.drink.GetDrinksUseCase
import com.cessup.domain.usecases.eatable.drink.NewDrinkUseCase
import com.cessup.domain.usecases.eatable.drink.UpdateDrinkUseCase
import com.cessup.domain.usecases.eatable.meal.DeleteMealUseCase
import com.cessup.domain.usecases.eatable.meal.GetMealsUseCase
import com.cessup.domain.usecases.eatable.meal.NewMealUseCase
import com.cessup.domain.usecases.eatable.meal.UpdateMealUseCase
import com.cessup.domain.usecases.products.DeleteProductUseCase
import com.cessup.domain.usecases.products.FindBySerialNumberUseCase
import com.cessup.domain.usecases.products.RegisterProductUseCase
import com.cessup.domain.usecases.products.UpdateDetailsProductUseCase
import com.cessup.domain.usecases.products.UpdateProductUseCase
import com.cessup.domain.usecases.session.AuthenticateUseCase
import com.cessup.domain.usecases.session.DeleteRoleUseCase
import com.cessup.domain.usecases.session.DeleteUserUseCase
import com.cessup.domain.usecases.session.GetRoleUseCase
import com.cessup.domain.usecases.session.GetUserUseCase
import com.cessup.domain.usecases.session.RegisterRoleUseCase
import com.cessup.domain.usecases.session.RegisterUserUseCase
import com.cessup.domain.usecases.session.ResetPasswordUseCase
import com.cessup.domain.usecases.session.UpdateRoleUseCase
import com.cessup.domain.usecases.session.UpdateUserDetailsUseCase
import org.koin.dsl.module
import org.yaml.snakeyaml.Yaml

/**
 * AppModules is a class with some modules for injection in this application.
 *
 * @author
 *     Cessup
 * @since 1.0
 */
val appModule = module {

    /**
     * This function start to configure the framework to mongo
     * @return [MongoConfig] the objet to use the database
     */
    single<MongoConfig> {
        val input = Thread.currentThread().contextClassLoader.getResourceAsStream("application.yaml")
        val config = Yaml().load<Map<String, Any>>(input)

        val ktor = config["ktor"] as Map<*, *>
        val custom = ktor["mongodb"] as Map<*, *>

        MongoConfig(
            custom["databaseUri"].toString(),
            custom["sessionDB"].toString(),
            custom["productDB"].toString(),
            custom["eatableDB"].toString()
        )
    }

    /**
     * This function to encrypt
     *
     * @return [Security] the object to use the encryption
     */
    single<Security>{ Security }


    /**
     * This function start to configure the framework
     *
     * @param MongoConfig the MongoConfig got configuration about database
     *
     * @return [UserRepository] the object to use the User Repository
     */
    single<UserRepository> {
        val config: MongoConfig = get()
        UserRepositoryImpl(config.userDB)
    }

    /**
     * This function start to configure the framework
     *
     * @param MongoConfig the MongoConfig got configuration about database
     *
     * @return [ProductRepositoryImpl] the object to use the User Repository
     */
    single<ProductRepository> {
        val config: MongoConfig = get()
        ProductRepositoryImpl(config.productsDB)
    }

    /**
     * This function start to configure the framework
     *
     * @param MongoConfig the MongoConfig got configuration about database
     *
     * @return [UserRepository] the object to use the User Repository
     */
    single<EatableRepository> {
        val config: MongoConfig = get()
        EatableRepositoryImpl(config.eatableDB)
    }
}

val useCaseModule = module {
    single { AuthenticateUseCase(get(),get()) }
    single { DeleteRoleUseCase(get()) }
    single { DeleteUserUseCase(get()) }
    single { GetRoleUseCase(get()) }
    single { GetUserUseCase(get(),get()) }
    single { RegisterRoleUseCase(get()) }
    single { RegisterUserUseCase(get(),get()) }
    single { ResetPasswordUseCase(get(),get()) }
    single { UpdateRoleUseCase(get()) }
    single { UpdateUserDetailsUseCase(get()) }

    single { DeleteProductUseCase(get()) }
    single { FindBySerialNumberUseCase(get()) }
    single { RegisterProductUseCase(get()) }
    single { UpdateProductUseCase(get()) }
    single { UpdateDetailsProductUseCase(get()) }

    single { DeleteDrinkUseCase(get()) }
    single { GetDrinksUseCase(get()) }
    single { NewDrinkUseCase(get()) }
    single { UpdateDrinkUseCase(get()) }

    single { DeleteMealUseCase(get()) }
    single { GetMealsUseCase(get()) }
    single { NewMealUseCase(get()) }
    single { UpdateMealUseCase(get()) }
}