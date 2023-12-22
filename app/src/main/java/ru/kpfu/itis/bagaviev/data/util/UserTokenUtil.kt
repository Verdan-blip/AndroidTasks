package ru.kpfu.itis.bagaviev.data.util

import java.security.MessageDigest

object UserTokenUtil {

    private val messageDigest = MessageDigest.getInstance("MD5")

    fun get(email: String, phone: String): String {
        messageDigest.update((phone + email).toByteArray())
        return String(messageDigest.digest())
    }

}