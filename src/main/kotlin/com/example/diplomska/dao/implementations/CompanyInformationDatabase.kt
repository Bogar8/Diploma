package com.example.diplomska.dao.implementations

import com.example.diplomska.dao.interfaces.DaoCompanyInformation
import com.example.diplomska.model.CompanyInformation
import com.example.diplomska.util.DatabaseUtil
import com.example.diplomska.util.DocumentUtil
import com.mongodb.client.MongoCollection
import org.bson.Document
import org.litote.kmongo.*

object CompanyInformationDatabase : DaoCompanyInformation {
    private fun getCollection(): MongoCollection<Document> {
        return DatabaseUtil.getDatabaseConnection().getCollection(CompanyInformation.DATABASE_NAME)
    }

    override fun getByName(name: String): CompanyInformation? {
        val answer = getCollection().findOne { CompanyInformation::name eq name }
        if (answer != null) {
            return DocumentUtil.decode(answer)
        }
        return null
    }

    override fun getById(id: String): CompanyInformation? {
        val answer = getCollection().findOne { CompanyInformation::_id eq id }
        if (answer != null) {
            return DocumentUtil.decode(answer)
        }
        return null
    }

    override fun getAll(): List<CompanyInformation> {
        val answer = getCollection().find()
        val companyInformations: ArrayList<CompanyInformation> = ArrayList()
        answer.forEach {
            companyInformations.add(DocumentUtil.decode(it))
        }
        return companyInformations
    }

    override fun insert(obj: CompanyInformation): Boolean {
        val sameID = getById(obj._id)
        val sameName = getByName(obj.name)
        if (sameID != null || sameName != null) {
            return false
        }
        val result = getCollection().insertOne(DocumentUtil.encode(obj))
        return result.wasAcknowledged()
    }

    override fun update(obj: CompanyInformation): Boolean {
        val result = getCollection().replaceOneById(id = obj._id, DocumentUtil.encode(obj))
        return result.wasAcknowledged()
    }

    override fun delete(obj: CompanyInformation): Boolean {
        val result = getCollection().deleteOne(CompanyInformation::_id eq obj._id, DocumentUtil.encode(obj))
        return result.wasAcknowledged()
    }
}