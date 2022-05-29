package com.example.diplomska.dao.implementations

import com.example.diplomska.dao.interfaces.DaoProduct
import com.example.diplomska.model.Category
import com.example.diplomska.model.Product
import com.example.diplomska.util.DatabaseUtil
import com.example.diplomska.util.DocumentUtil
import com.mongodb.client.MongoCollection
import org.bson.Document
import org.litote.kmongo.*

object ProductDatabase : DaoProduct {

    private fun getCollection(): MongoCollection<Document> {
        return DatabaseUtil.getDatabaseConnection().getCollection(Product.DATABASE_NAME)
    }

    override fun getByBarcode(barcode: Int): Product? {
        val answer = getCollection().findOne { Product::barcode eq barcode }
        if (answer != null) {
            return DocumentUtil.decode(answer)
        }
        return null
    }

    override fun getInStock(): ArrayList<Product> {
        val answer = getCollection().find(Product::stock gt 0)
        val products: ArrayList<Product> = ArrayList()
        answer.forEach {
            products.add(DocumentUtil.decode(it))
        }
        return products
    }

    override fun getOutOfStock(): ArrayList<Product> {
        val answer = getCollection().find(Product::stock eq 0)
        val products: ArrayList<Product> = ArrayList()
        answer.forEach {
            products.add(DocumentUtil.decode(it))
        }
        return products
    }

    override fun getByCategory(category: Category): ArrayList<Product> {
        val answer = getCollection().find(Product::category eq category)
        val products: ArrayList<Product> = ArrayList()
        answer.forEach {
            products.add(DocumentUtil.decode(it))
        }
        return products
    }

    override fun getActiveProducts(): ArrayList<Product> {
        val answer = getCollection().find(Product::isActive eq true)
        val products: ArrayList<Product> = ArrayList()
        answer.forEach {
            products.add(DocumentUtil.decode(it))
        }
        return products
    }

    override fun getInActiveProducts(): ArrayList<Product> {
        val answer = getCollection().find(Product::isActive eq false)
        val products: ArrayList<Product> = ArrayList()
        answer.forEach {
            products.add(DocumentUtil.decode(it))
        }
        return products
    }

    override fun getById(id: String): Product? {
        val answer = getCollection().findOne { Product::_id eq id }
        if (answer != null) {
            return DocumentUtil.decode(answer)
        }
        return null
    }

    override fun getAll(): List<Product> {
        val answer = getCollection().find()
        val products: ArrayList<Product> = ArrayList()
        answer.forEach {
            products.add(DocumentUtil.decode(it))
        }
        return products
    }

    override fun insert(obj: Product): Boolean {
        val sameID = getById(obj._id)
        if (sameID != null) {
            return false
        }
        val result = getCollection().insertOne(DocumentUtil.encode(obj))
        return result.wasAcknowledged()
    }

    override fun update(obj: Product): Boolean {
        val result = getCollection().replaceOneById(id = obj._id, DocumentUtil.encode(obj))
        return result.wasAcknowledged()
    }

    override fun delete(obj: Product): Boolean {
        val result = getCollection().deleteOne(Product::_id eq obj._id, DocumentUtil.encode(obj))
        return result.wasAcknowledged()
    }
}