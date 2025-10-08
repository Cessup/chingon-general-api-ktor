package data

import com.cessup.data.repositories.UserRepositoryImpl
import com.cessup.domain.models.session.User
import com.cessup.domain.models.session.UserDetails
import com.cessup.domain.repositories.UserRepository
import com.mongodb.reactivestreams.client.MongoClients
import kotlinx.coroutines.runBlocking
import org.bson.types.ObjectId
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.testcontainers.containers.MongoDBContainer

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryTest {

    private val mongoContainer = MongoDBContainer("mongo:6.0")
    private lateinit var repository: UserRepository

    @BeforeAll
    fun setup(): Unit = runBlocking {
        mongoContainer.start()
        val client = MongoClients.create(mongoContainer.connectionString)
        val db = client.getDatabase("test_db")

        repository = UserRepositoryImpl(db)
    }

    @AfterAll
    fun tearDown() {
        mongoContainer.stop()
    }

    @Test
    fun `insert and find user`() = runBlocking {
        val user = createUser()

        repository.insertUser(user)
        val found = repository.findById(user.id)

        Assertions.assertNotNull(found)
        Assertions.assertEquals(user.email, found?.email)
    }

    @Test
    fun `update user details`() = runBlocking {
        val user = createUser()
        repository.insertUser(user)

        val newDetails = user.details.copy(name = "NewName")
        repository.updateUserDetails(user.id.toString(), newDetails)

        val updated = repository.findById(user.id)
        Assertions.assertEquals("NewName", updated?.details?.name)
    }

    @Test
    fun `update user password`() = runBlocking {
        val user = createUser()
        repository.insertUser(user)

        repository.updatePassword(user.email, "newPassword123")
        val updated = repository.findById(user.id)
        Assertions.assertEquals("newPassword123", updated?.password)
    }

    @Test
    fun `delete user`() = runBlocking {
        val user = createUser()
        repository.insertUser(user)

        repository.deleteUser(user.id)
        val deleted = repository.findById(user.id)
        Assertions.assertNull(deleted)
    }

    @Test
    fun `find by email`() = runBlocking {
        val user = createUser()
        repository.insertUser(user)

        val found = repository.findByEmail(user.email)
        Assertions.assertNotNull(found)
        Assertions.assertEquals(user.phone, found?.phone)
    }

    @Test
    fun `find by phone`() = runBlocking {
        val user = createUser()
        repository.insertUser(user)

        val found = repository.findByPhone(user.phone)
        Assertions.assertNotNull(found)
        Assertions.assertEquals(user.email, found?.email)
    }

    private fun createUser(): User = User(
            id = ObjectId(),
            email = "example_one@gmail.com",
            phone = "5511223344",
            nickname = "tester",
            password = "securepass",
            details = UserDetails(
                id = ObjectId(),
                name = "John",
                lastName = "Doe",
                address = "123 Main St",
                gender = "male",
                birthdate = 946684800000 // Jan 1, 2000
            )
        )

}