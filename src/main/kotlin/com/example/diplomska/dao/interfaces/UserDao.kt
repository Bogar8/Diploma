package com.example.diplomska.dao.interfaces

import com.example.diplomska.dao.interfaces.DaoCrud
import com.example.diplomska.model.Product
import com.example.diplomska.model.User
import java.time.LocalDateTime

interface UserDao: DaoCrud<User> {
    fun getByUsername(username: String): User?
    fun getByLevel(level: Int): ArrayList<User>
    fun getLastLoggedInAfterDate(date: LocalDateTime): ArrayList<User>
    fun getLastLoggedInBeforeDate(date: LocalDateTime): ArrayList<User>
    fun login(user: User): User?
}