package com.example.kopringstudy.infrastructure

import com.example.kopringstudy.domain.Board
import com.example.kopringstudy.domain.BoardRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class BoardRepositoryImpl @Autowired constructor(
        private var boardJpaRepository: BoardJpaRepository
) : BoardRepository {

    override fun findAll(): List<Board> {
        return boardJpaRepository.findAll()
    }

    override fun findById(id: Long): Optional<Board> {
        return boardJpaRepository.findById(id)
    }

    override fun save(board: Board): Board {
        return boardJpaRepository.save(board)
    }

    override fun deleteById(id: Long) {
        boardJpaRepository.deleteById(id)
    }
}
