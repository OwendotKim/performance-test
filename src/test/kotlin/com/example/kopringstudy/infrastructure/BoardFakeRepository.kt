package com.example.kopringstudy.infrastructure

import com.example.kopringstudy.domain.Board
import com.example.kopringstudy.domain.BoardRepository
import java.util.*

class BoardFakeRepository(
        private val map: MutableMap<Long, Board> = hashMapOf<Long, Board>(),
        private var id: Long = 1L
) : BoardRepository {

    override fun save(board: Board): Board {
        board.id = this.id
        map.put(id, board)
        this.id += 1

        return board
    }

    override fun findAll(): List<Board> {
        return map.values.toList()
    }

    override fun findById(id: Long): Optional<Board> {
        return map.values.stream()
                .filter { it -> it.id == id }
                .findAny();
    }

    override fun deleteById(id: Long) {
        map.remove(id)
    }
}
