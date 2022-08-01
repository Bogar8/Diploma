package com.example.diplomska.dao.implementations

import com.example.diplomska.dao.interfaces.DaoProduct
import com.example.diplomska.model.Category
import com.example.diplomska.model.Product
import com.example.diplomska.util.DatabaseUtil
import com.mongodb.client.MongoCollection
import org.bson.Document
import org.litote.kmongo.*
import tornadofx.*
import java.util.*

object ProductDatabase : DaoProduct {

    private fun getCollection(): MongoCollection<Document> {
        return DatabaseUtil.getDatabaseConnection().getCollection(Product.DATABASE_NAME)
    }

    override fun getByBarcode(barcode: Int): Product? {
        val answer = getCollection().findOne { Product::barcode eq barcode }
        if (answer != null) {
            val product = Product()
            product.updateModel(loadJsonObject(answer.toJson()))
            return product
        }
        return null
    }

    override fun getInStock(): ArrayList<Product> {
        val answer = getCollection().find(Product::stock gt 0)
        val products: ArrayList<Product> = ArrayList()
        answer.forEach {
            val product = Product()
            product.updateModel(loadJsonObject(it.toJson()))
            products.add(product)
        }
        return products
    }

    override fun getOutOfStock(): ArrayList<Product> {
        val answer = getCollection().find(Product::stock eq 0)
        val products: ArrayList<Product> = ArrayList()
        answer.forEach {
            val product = Product()
            product.updateModel(loadJsonObject(it.toJson()))
            products.add(product)
        }
        return products
    }

    override fun getByCategory(category: Category): ArrayList<Product> {
        val answer = getCollection().find(Product::category eq category)
        val products: ArrayList<Product> = ArrayList()
        answer.forEach {
            val product = Product()
            product.updateModel(loadJsonObject(it.toJson()))
            products.add(product)
        }
        return products
    }

    override fun getActiveProducts(): ArrayList<Product> {
        val answer = getCollection().find(Product::isActive eq true)
        val products: ArrayList<Product> = ArrayList()
        answer.forEach {
            val product = Product()
            product.updateModel(loadJsonObject(it.toJson()))
            products.add(product)
        }
        return products
    }

    override fun getInActiveProducts(): ArrayList<Product> {
        val answer = getCollection().find(Product::isActive eq false)
        val products: ArrayList<Product> = ArrayList()
        answer.forEach {
            val product = Product()
            product.updateModel(loadJsonObject(it.toJson()))
            products.add(product)
        }
        return products
    }

    override fun getById(id: String): Product? {
        val answer = getCollection().findOne { Product::_id eq id }
        if (answer != null) {
            val product = Product()
            product.updateModel(loadJsonObject(answer.toJson()))
            return product
        }
        return null
    }

    override fun getAll(): ArrayList<Product> {
        val answer = getCollection().find()
        val products: ArrayList<Product> = ArrayList()
        answer.forEach {
            val product = Product()
            product.updateModel(loadJsonObject(it.toJson()))
            products.add(product)
        }
        return products
    }

    override fun insert(obj: Product): Boolean {
        obj._id = UUID.randomUUID().toString()
        val result = getCollection().insertOne(Document.parse(obj.toJSON().toString()))
        return result.wasAcknowledged()
    }

    override fun update(obj: Product): Boolean {
        val result = getCollection().replaceOneById(id = obj._id, Document.parse(obj.toJSON().toString()))
        return result.wasAcknowledged()
    }

    override fun delete(obj: Product): Boolean {
        val result = getCollection().deleteOne(Product::_id eq obj._id, Document.parse(obj.toJSON().toString()))
        return result.wasAcknowledged()
    }
}