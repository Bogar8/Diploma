package com.example.diplomska.dao.implementations

import com.example.diplomska.dao.interfaces.UserDao
import com.example.diplomska.model.Invoice
import com.example.diplomska.model.Product
import com.example.diplomska.model.User
import com.example.diplomska.util.DatabaseUtil
import com.example.diplomska.util.DocumentUtil
import com.mongodb.client.MongoCollection
import org.bson.Document
import org.litote.kmongo.*
import java.time.LocalDateTime

class UserDatabase : UserDao {

    private fun getCollection(): MongoCollection<Document> {
        return DatabaseUtil.getDatabaseConnection().getCollection(User.DATABASE_NAME)
    }

    override fun getByUsername(username: String): User? {
        val answer = getCollection().findOne { User::username eq username }
        if (answer != null) {
            return DocumentUtil.decode(answer)
        }
        return null
    }

    override fun getByLevel(level: Int): ArrayList<User> {
        val answer = getCollection().find(User::level eq level)
        val users: ArrayList<User> = ArrayList()
        answer.forEach {
            users.add(DocumentUtil.decode(it))
        }
        return users
    }

    override fun getLastLoggedInAfterDate(date: LocalDateTime): ArrayList<User> {
        val answer = getCollection().find(User::lastLogin gt date)
        val users: ArrayList<User> = ArrayList()
        answer.forEach {
            users.add(DocumentUtil.decode(it))
        }
        return users
    }

    override fun getLastLoggedInBeforeDate(date: LocalDateTime): ArrayList<User> {
        val answer = getCollection().find(User::lastLogin lt date)
        val users: ArrayList<User> = ArrayList()
        answer.forEach {
            users.add(DocumentUtil.decode(it))
        }
        return users
    }

    override fun getById(id: String): User? {
        val answer = getCollection().findOne { User::_id eq id }
        if (answer != null) {
            return DocumentUtil.decode(answer)
        }
        return null
    }

    override fun getAll(): ArrayList<User> {
        val answer = getCollection().find()
        val users: ArrayList<User> = ArrayList()
        answer.forEach {
            users.add(DocumentUtil.decode(it))
        }
        return users
    }

    override fun insert(obj: User): Boolean {
        val result = getCollection().insertOne(DocumentUtil.encode(obj))
        return result.wasAcknowledged()
    }

    override fun update(obj: User): Boolean {
        val result = getCollection().updateOneById(DocumentUtil.encode(obj))
        return result.wasAcknowledged()
    }

    override fun delete(obj: User): Boolean {
        val result = getCollection().deleteOneById(DocumentUtil.encode(obj))
        return result.wasAcknowledged()
    }
}