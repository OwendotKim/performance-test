package com.example.kopringstudy.infrastructure

import com.example.kopringstudy.domain.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

interface BoardJpaRepository : JpaRepository<Board, Long> {

    override fun findAll(): List<Board>

    override fun findById(id: Long): Optional<Board>

    override fun <S : Board> save(entity: S): S

    override fun deleteById(id: Long)
}
