package com.example.kopringstudy.domain

import java.util.*

interface BoardRepository {

    fun findAll(): List<Board>;

    fun findById(id: Long): Optional<Board>

    fun save(board: Board): Board

    fun deleteById(id: Long)
}
