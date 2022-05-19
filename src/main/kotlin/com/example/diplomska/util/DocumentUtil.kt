package com.example.diplomska.util

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bson.Document
import org.litote.kmongo.json

object DocumentUtil {
    inline fun <reified T> decode(document: Document): T {

        return Json.decodeFromString<T>(document.json)
    }

    inline fun <reified T> encode(obj: T): Document {
        return Document.parse(Json.encodeToString<T>(obj))
    }
}