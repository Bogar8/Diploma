package com.example.diplomska.util

import com.mongodb.client.MongoDatabase
import org.litote.kmongo.KMongo

object DatabaseUtil {
    fun getConnection(): MongoDatabase {
        val client = KMongo.createClient("mongodb+srv://bogar:geslo@cluster0.iberm.mongodb.net/?retryWrites=true&w=majority") //get com.mongodb.MongoClient new instance
        return client.getDatabase("Diploma") //normal java driver usage
    }
}