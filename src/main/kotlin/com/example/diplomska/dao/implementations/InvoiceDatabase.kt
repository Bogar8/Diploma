package com.example.diplomska.dao.implementations

import com.example.diplomska.dao.interfaces.DaoInvoice
import com.example.diplomska.model.Invoice
import com.example.diplomska.model.Product
import com.example.diplomska.model.User
import com.example.diplomska.util.DatabaseUtil
import com.example.diplomska.util.DocumentUtil
import com.mongodb.client.MongoCollection
import org.bson.Document
import org.litote.kmongo.*
import java.time.LocalDateTime

object InvoiceDatabase : DaoInvoice {

    private fun getCollection(): MongoCollection<Document> {
        return DatabaseUtil.getDatabaseConnection().getCollection(Invoice.DATABASE_NAME)
    }

    override fun getBySeller(seller: User): ArrayList<Invoice> {
        val answer = getCollection().find(Invoice::seller eq seller)
        val invoices: ArrayList<Invoice> = ArrayList()
        answer.forEach {
            invoices.add(DocumentUtil.decode(it))
        }
        return invoices
    }

    override fun getWithPriceGreaterThan(price: Double): ArrayList<Invoice> {
        val answer = getCollection().find(Invoice::totalPrice gt price)
        val invoices: ArrayList<Invoice> = ArrayList()
        answer.forEach {
            invoices.add(DocumentUtil.decode(it))
        }
        return invoices
    }

    override fun getAfterDate(date: LocalDateTime): ArrayList<Invoice> {
        val answer = getCollection().find(Invoice::date gt date)
        val invoices: ArrayList<Invoice> = ArrayList()
        answer.forEach {
            invoices.add(DocumentUtil.decode(it))
        }
        return invoices
    }

    override fun getBeforeDate(date: LocalDateTime): ArrayList<Invoice> {
        val answer = getCollection().find(Invoice::date lt date)
        val invoices: ArrayList<Invoice> = ArrayList()
        answer.forEach {
            invoices.add(DocumentUtil.decode(it))
        }
        return invoices
    }

    override fun getBetweenDates(dateFrom: LocalDateTime, dateTo: LocalDateTime): ArrayList<Invoice> {
        val invoices: ArrayList<Invoice> = ArrayList()
        val all = getAll()
        all.forEach {
            if (it.date in dateFrom..dateTo) {
                invoices.add(it)
            }
        }
        return invoices
    }

    override fun getById(id: String): Invoice? {
        val answer = getCollection().findOne { Invoice::_id eq id }
        if (answer != null) {
            return DocumentUtil.decode(answer)
        }
        return null
    }

    override fun getAll(): List<Invoice> {
        val answer = getCollection().find()
        val invoices: ArrayList<Invoice> = ArrayList()
        answer.forEach {
            invoices.add(DocumentUtil.decode(it))
        }
        return invoices
    }

    override fun insert(obj: Invoice): Boolean {
        val sameID = getById(obj._id)
        if (sameID != null) {
            return false
        }
        val result = getCollection().insertOne(DocumentUtil.encode(obj))
        return result.wasAcknowledged()
    }

    override fun update(obj: Invoice): Boolean {
        val result = getCollection().replaceOneById(id = obj._id, DocumentUtil.encode(obj))
        return result.wasAcknowledged()
    }

    override fun delete(obj: Invoice): Boolean {
        val result = getCollection().deleteOne(Product::_id eq obj._id, DocumentUtil.encode(obj))
        return result.wasAcknowledged()
    }
}