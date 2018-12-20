package com.gemasr.moviesapp

import java.io.File

object UnitTestHelper {
    fun getJson(path : String) : String {
        val uri = this.javaClass.classLoader.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}