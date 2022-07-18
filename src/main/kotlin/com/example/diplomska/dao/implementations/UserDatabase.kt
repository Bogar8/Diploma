package com.example.diplomska.dao.implementations

import com.example.diplomska.dao.interfaces.DaoUser
import com.example.diplomska.model.User
import com.example.diplomska.model.UserLevel
import com.example.diplomska.util.DatabaseUtil
import com.mongodb.client.MongoCollection
import org.bson.Document
import org.litote.kmongo.*
import tornadofx.*
import java.time.LocalDateTime

object UserDatabase : DaoUser {
    private fun getCollection(): MongoCollection<Document> {
        return DatabaseUtil.getDatabaseConnection().getCollection(User.DATABASE_NAME)
    }

    override fun getByUsername(username: String): User? {
        val answer = getCollection().findOne { User::username eq username }
        if (answer != null) {
            val user = User()
            user.updateModel(loadJsonObject(answer.json))
            return user
        }
        return null
    }

    override fun getByLevel(level: UserLevel): ArrayList<User> {
        val answer = getCollection().find(User::level eq level)
        val users: ArrayList<User> = ArrayList()
        answer.forEach {
            val user = User()
            user.updateModel(loadJsonObject(it.json))
            users.add(user)
        }
        return users
    }

    override fun getLastLoggedInAfterDate(date: LocalDateTime): ArrayList<User> {
        val users: ArrayList<User> = ArrayList()
        val all = getAll()
        all.forEach {
            if (it.lastLogin > date) {
                users.add(it)
            }
        }
        return users
    }

    override fun getLastLoggedInBeforeDate(date: LocalDateTime): ArrayList<User> {
        val users: ArrayList<User> = ArrayList()
        val all = getAll()
        all.forEach {
            if (it.lastLogin < date) {
                users.add(it)
            }
        }
        return users
    }

    override fun getById(id: String): User? {
        val answer = getCollection().findOne { User::_id eq id }
        if (answer != null) {
            val user = User()
            user.updateModel(loadJsonObject(answer.json))
            return user
        }
        return null
    }

    override fun getAll(): ArrayList<User> {
        val answer = getCollection().find()
        val users: ArrayList<User> = ArrayList()
        answer.forEach {
            val user = User()
            user.updateModel(loadJsonObject(it.json))
            users.add(user)
        }
        return users
    }

    override fun insert(obj: User): Boolean {
        val sameID = getById(obj._id)
        val sameUsername = getByUsername(obj.username)
        if (sameID != null || sameUsername != null) {
            return false
        }
        val result = getCollection().insertOne(Document.parse(obj.toJSON().toString()))
        return result.wasAcknowledged()
    }

    override fun update(obj: User): Boolean {
        val result = getCollection().replaceOneById(id = obj._id, Document.parse(obj.toJSON().toString()))
        return result.wasAcknowledged()
    }

    override fun delete(obj: User): Boolean {
        val result = getCollection().deleteOne(User::_id eq obj._id, Document.parse(obj.toJSON().toString()))
        return result.wasAcknowledged()
    }

    override fun login(username: String, password: String): User? {
        val result = getCollection().findOne(User::username eq username, User::password eq password)
        if (result == null) {
            return null
        }
        val user = User()
        user.updateModel(loadJsonObject(result.json))
        return user
    }
}