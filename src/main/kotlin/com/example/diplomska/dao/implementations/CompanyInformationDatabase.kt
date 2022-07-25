package com.example.diplomska.dao.implementations

import com.example.diplomska.dao.interfaces.DaoCompanyInformation
import com.example.diplomska.model.CompanyInformation
import com.example.diplomska.util.DatabaseUtil
import com.mongodb.client.MongoCollection
import org.bson.Document
import org.litote.kmongo.deleteOne
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.replaceOneById
import tornadofx.*
import java.util.*

object CompanyInformationDatabase : DaoCompanyInformation {
    private fun getCollection(): MongoCollection<Document> {
        return DatabaseUtil.getDatabaseConnection().getCollection(CompanyInformation.DATABASE_NAME)
    }

    override fun getByName(name: String): CompanyInformation? {
        val answer = getCollection().findOne { CompanyInformation::name eq name }
        if (answer != null) {
            val companyInformation = CompanyInformation()
            companyInformation.updateModel(loadJsonObject(answer.toJson()))
            return companyInformation
        }
        return null
    }

    override fun getById(id: String): CompanyInformation? {
        val answer = getCollection().findOne { CompanyInformation::_id eq id }
        if (answer != null) {
            val companyInformation = CompanyInformation()
            companyInformation.updateModel(loadJsonObject(answer.toJson()))
            return companyInformation
        }
        return null
    }

    override fun getAll(): ArrayList<CompanyInformation> {
        val answer = getCollection().find()
        val companyInformations: ArrayList<CompanyInformation> = ArrayList()
        answer.forEach {
            val companyInformation = CompanyInformation()
            companyInformation.updateModel(loadJsonObject(it.toJson()))
            companyInformations.add(companyInformation)
        }
        return companyInformations
    }

    override fun insert(obj: CompanyInformation): Boolean {
        val sameID = getById(obj._id)
        val sameName = getByName(obj.name)
        if (sameID != null || sameName != null) {
            return false
        }
        obj._id = UUID.randomUUID().toString()
        val result = getCollection().insertOne(Document.parse(obj.toJSON().toString()))
        return result.wasAcknowledged()
    }

    override fun update(obj: CompanyInformation): Boolean {
        val result = getCollection().replaceOneById(id = obj._id, Document.parse(obj.toJSON().toString()))
        return result.wasAcknowledged()
    }

    override fun delete(obj: CompanyInformation): Boolean {
        val result =
            getCollection().deleteOne(CompanyInformation::_id eq obj._id, Document.parse(obj.toJSON().toString()))
        return result.wasAcknowledged()
    }
}