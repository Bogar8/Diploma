package com.example.diplomska.dao.interfaces

import com.example.diplomska.dao.interfaces.DaoCrud
import com.example.diplomska.model.Product
import com.example.diplomska.model.User
import java.time.LocalDateTime

interface UserDao: DaoCrud<User> {
    fun getByUsername(username: String): User?
    fun getByLevel(level: Int): List<User>
    fun getLastLoggedInAfterDate(date: LocalDateTime): List<Product>
    fun getLastLoggedInBeforeDate(date: LocalDateTime): List<Product>
}