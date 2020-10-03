package com.example.signupsample.repository

import android.content.Context
import com.example.signupsample.model.User
import java.io.*

// 매우 단순하게 구현된 DB
object UserRepository {
    private const val filename = "user.txt"

    private lateinit var context: Context

    fun init(context: Context) {
        this.context = context.applicationContext
    }

    fun add(user: User) {
        val writer = PrintWriter(context.openFileOutput(filename, Context.MODE_APPEND))
        writer.println(user.emailAddress)
        writer.close()
    }

    fun containsByEmail(emailAddress: CharSequence): Boolean {
        return try {
            val reader = BufferedReader(InputStreamReader(context.openFileInput(filename)))
            val contains = reader.lines().anyMatch { it!!.contentEquals(emailAddress) }
            reader.close()
            contains
        } catch (e: FileNotFoundException) {
            false
        }
    }
}