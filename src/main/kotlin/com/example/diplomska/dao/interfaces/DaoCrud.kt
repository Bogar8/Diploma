package com.example.diplomska.dao.interfaces

interface DaoCrud<T> {
    fun getById(id: Int): T?
    fun getAll(): List<T>
    fun insert(obj: T): Boolean
    fun update(obj: T): Boolean
    fun delete(obj: T): Boolean
}