package com.example.diplomska.dao.interfaces

interface DaoCrud<T> {
    fun getById(id: String): T?
    fun getAll(): ArrayList<T>
    fun insert(obj: T): Boolean
    fun update(obj: T): Boolean
    fun delete(obj: T): Boolean
}