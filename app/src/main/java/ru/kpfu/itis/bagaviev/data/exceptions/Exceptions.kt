package ru.kpfu.itis.bagaviev.data.exceptions

import java.lang.Exception

class AuthException(message: String) : Exception(message)

class RegistrationException(message: String): Exception(message)

class IncorrectTokenException(message: String): Exception(message)

class SuchPhoneAlreadyExistsException(message: String): Exception(message)

class SuchEmailAlreadyExistsException(message: String): Exception(message)

class ContextNotProvidedException() : Exception()