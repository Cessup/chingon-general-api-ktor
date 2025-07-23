package com.cessup.data.exceptions

class AuthorizationException(cause: Throwable) : Exception(cause)

class NotFoundException(message: String) : RuntimeException(message)