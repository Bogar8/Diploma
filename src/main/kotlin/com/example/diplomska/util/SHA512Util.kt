package com.example.diplomska.util

import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter

object SHA512Util {

    fun hashString(string: String): String {
        val md = MessageDigest.getInstance("SHA-512")
        val encryptedString = md.digest(string.toByteArray())
        return DatatypeConverter.printHexBinary(encryptedString).uppercase()
    }
}