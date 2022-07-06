package com.example.diplomska.dao.interfaces

import com.example.diplomska.model.User
import com.example.diplomska.model.UserLevel
import java.time.LocalDateTime

interface DaoUser : DaoCrud<User> {
    fun getByUsername(username: String): User?
    fun getByLevel(level: UserLevel): ArrayList<User>
    fun getLastLoggedInAfterDate(date: LocalDateTime): ArrayList<User>
    fun getLastLoggedInBeforeDate(date: LocalDateTime): ArrayList<User>
    fun login(username: String, password: String): User?
}