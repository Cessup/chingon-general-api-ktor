package com.cessup.data.entities

import com.cessup.domain.models.session.Role
import org.bson.Document
import com.cessup.domain.models.session.UserDetails
import com.cessup.domain.models.session.User
import com.cessup.domain.models.session.Type

fun User.toDocument(): Document = Document()
    .append("_id", id)
    .append("email", email)
    .append("phone", phone)
    .append("nickname", nickname)
    .append("password", password)
    .append("details", details.toDocument())

fun UserDetails.toDocument(): Document = Document()
    .append("_id", id)
    .append("name", name)
    .append("lastName", lastName)
    .append("address", address)
    .append("gender", gender)
    .append("birthdate", birthdate)

fun Type.toDocument(): Document = Document()
    .append("_id", id)
    .append("idUser", idUser)
    .append("idRole", idRole)

fun Role.toDocument(): Document = Document()
    .append("_id", id)
    .append("code", code)
    .append("name", name)

fun Document.toUser(): User =
    User(
        id = getObjectId("_id"),
        email = getString("email"),
        phone = getString("phone"),
        nickname = getString("nickname"),
        password = getString("password"),
        details = (get("details") as Document).toUserDetails()
    )

fun Document.toUserDetails(): UserDetails =
    UserDetails(
        id = getObjectId("_id"),
        name = getString("name"),
        lastName = getString("lastName"),
        address = getString("address"),
        gender = getString("gender"),
        birthdate = getLong("birthdate")
    )


fun Document.toType(): Type =
    Type(
        id = getObjectId("_id"),
        idUser = getObjectId("idUser"),
        idRole = getObjectId("idRole")
    )

fun Document.toRole(): Role =
    Role(
        id = getObjectId("_id"),
        code = getInteger("code"),
        name = getString("name")
    )