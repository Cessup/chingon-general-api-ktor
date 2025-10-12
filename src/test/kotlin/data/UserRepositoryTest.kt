package com.cessup.data

import com.cessup.data.repositories.UserRepositoryImpl
import com.cessup.domain.models.session.Role
import com.cessup.domain.models.session.Type
import com.cessup.domain.models.session.User
import com.cessup.domain.models.session.UserDetails
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.reactivestreams.client.MongoClients
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.bson.codecs.configuration.CodecRegistries.fromProviders
import org.bson.codecs.configuration.CodecRegistries.fromRegistries
import org.bson.codecs.pojo.PojoCodecProvider
import org.bson.types.ObjectId
import org.junit.jupiter.api.*
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue
import kotlin.test.assertNull

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryImplTest {

    private lateinit var repository: UserRepositoryImpl

    @BeforeAll
    fun setup() {
        val pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build())
        val codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry)

        val settings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString("mongodb://localhost:27017"))
            .codecRegistry(codecRegistry)
            .build()

        val client = MongoClients.create(settings)
        val database = client.getDatabase("session_db")

        repository = UserRepositoryImpl(database)
    }

    @BeforeEach
    fun clean() = runTest {
        repository.users.deleteMany(org.bson.Document()).awaitFirstOrNull()
    }

    @Test
    fun `insertUser should insert a user and allow retrieval by id`() = runBlocking {
        val user = createTestUser()

        val insertResult = repository.insertUser(user)
        assertTrue(insertResult, "Insert should succeed")

        val fetchedUser = repository.findById(user.id)
        assertNotNull(fetchedUser)
        assertEquals(user.email, fetchedUser.email)
        assertEquals(user.phone, fetchedUser.phone)
    }

    @Test
    fun `insertType should insert a type`() = runBlocking {
        val user = createTestUser()
        val role = createTestRole()

        val insertUserResult = repository.insertUser(user)
        assertTrue(insertUserResult, "Insert should succeed")
        val insertRoleResult = repository.insertRole(role)
        assertTrue(insertRoleResult, "Insert should succeed")

        val type = Type(ObjectId(),user.id,role.id)
        val insertTypeResult = repository.insertType(type)
        assertTrue(insertTypeResult, "Insert should succeed")
    }

    @Test
    fun `insertRole should insert a role`() = runBlocking {
        val role = createTestRole()
        val insertResult = repository.insertRole(role)
        assertTrue(insertResult, "Insert should succeed")
    }


    @Test
    fun `updateUserDetails should update details for existing user`() = runBlocking {
        val user = createTestUser()
        repository.insertUser(user)

        val newDetails = user.details.copy(name = "UpdatedName")
        val updateResult = repository.updateUserDetails(user.id, newDetails)
        assertTrue(updateResult, "Update should succeed")

        val updatedUser = repository.findById(user.id)
        assertEquals("UpdatedName", updatedUser?.details?.name)
    }

    @Test
    fun `updatePassword should update password for existing user`() = runBlocking {
        val user = createTestUser()
        repository.insertUser(user)

        val newPassword = "newSecurePassword"
        val updateResult = repository.updatePassword(user.email, newPassword)
        assertTrue(updateResult, "Password update should succeed")

        val updatedUser = repository.findByEmail(user.email)
        assertEquals(newPassword, updatedUser?.password)
    }

    @Test
    fun `deleteUser should delete user by id`() = runBlocking {
        val user = createTestUser()
        repository.insertUser(user)

        val deleteResult = repository.deleteUser(user.id)
        assertTrue(deleteResult, "Delete should succeed")

        val deletedUser = repository.findById(user.id)
        assertNull(deletedUser, "User should no longer exist")
    }

    @Test
    fun `findByEmail should find user by email`() = runBlocking {
        val user = createTestUser()
        repository.insertUser(user)

        val fetchedUser = repository.findByEmail(user.email)
        assertNotNull(fetchedUser)
        assertEquals(user.phone, fetchedUser.phone)
    }

    @Test
    fun `findByPhone should find user by phone`() = runBlocking {
        val user = createTestUser()
        repository.insertUser(user)

        val fetchedUser = repository.findByPhone(user.phone)
        assertNotNull(fetchedUser)
        assertEquals(user.email, fetchedUser.email)
    }

    @Test
    fun `updateRole should update name for existing role`() = runBlocking {
        val role = createTestRole()
        repository.insertRole(role)

        val newRole= role.copy(name = "UpdatedName")

        val updateResult = repository.updateRole(newRole)
        assertTrue(updateResult, "Role update should succeed")
    }

    @Test
    fun `findRoles should find a list of roles `() = runBlocking {
        val role = createTestRole()
        repository.insertRole(role)

        val fetchedRoles= repository.findRoles()
        assertNotNull(fetchedRoles)
        assertContains(fetchedRoles,role)
    }

    private fun createTestUser(): User {
        return User(
            id = ObjectId(),
            email = "test@example.com",
            phone = "1234567890",
            nickname = "tester",
            password = "password",
            details = UserDetails(
                id = ObjectId(),
                name = "John",
                lastName = "Doe",
                address = "123 Main St",
                gender = "male",
                birthdate = 946684800000
            )
        )
    }

    private fun createTestRole(): Role {
        return Role(
            id = ObjectId(),
            code = 1,
            name = "Administrator"
        )
    }
}