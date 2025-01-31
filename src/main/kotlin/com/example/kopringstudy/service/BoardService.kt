package com.example.kopringstudy.service

import com.example.kopringstudy.domain.Board
import com.example.kopringstudy.domain.BoardRepository
import com.example.kopringstudy.exception.BoardNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService(
        private val boardRepository: BoardRepository
) {

    @Transactional(readOnly = true)
    fun findAll(): List<Board> {
        return boardRepository.findAll()
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): Board {
        return boardRepository.findById(id).orElseThrow { BoardNotFoundException() }
    }

    @Transactional
    fun write(board: Board): Board {
        return boardRepository.save(board)
    }

    @Transactional
    fun edit(id: Long, updateBoard: Board): Board {
        val board: Board = boardRepository.findById(id).orElseThrow { BoardNotFoundException() }

        board.update(updateBoard.title, updateBoard.content)

        return board
    }

    @Transactional
    fun delete(id: Long): String {
        boardRepository.deleteById(id)
        return "삭제 완료";
    }
}
