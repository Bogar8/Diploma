package com.example.diplomska.util

import com.mongodb.client.MongoDatabase
import org.litote.kmongo.KMongo

object DatabaseUtil {


    private var databaseConnection: MongoDatabase

    init {
        databaseConnection = getConnection()
    }

    private fun getConnection(): MongoDatabase {
        val client =
            KMongo.createClient("mongodb+srv://bogar:geslo@cluster0.iberm.mongodb.net/?retryWrites=true&w=majority")
        return client.getDatabase("Diploma")
    }

    fun getDatabaseConnection(): MongoDatabase {
        return databaseConnection
    }

}