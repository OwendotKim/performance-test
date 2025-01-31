package com.example.kopringstudy.infrastructure

import com.example.kopringstudy.fixture.BoardFixture.Companion.게시글_생성
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class BoardJpaRepositoryTest(
        @Autowired private val boardJpaRepository: BoardJpaRepository
) {

    @Test
    fun 게시글_저장() {
        // given
        val board = 게시글_생성()

        // when
        val savedBoard = boardJpaRepository.save(board)

        // then
        assertEquals(board.title, savedBoard.title)
    }

    @Test
    fun 게시글_단건_조회() {
        // given
        val savedBoard = boardJpaRepository.save(게시글_생성())
        val id = savedBoard.id

        // when
        val result = id?.let {
            boardJpaRepository.findById(it).get()
        }

        // then
        assertEquals(savedBoard, result)
    }

    @Test
    fun 게시글_모두_조회() {
        // given
        val savedBoard = boardJpaRepository.save(게시글_생성())

        // when
        val result = boardJpaRepository.findAll()

        // then
        assertEquals(result.size, 1)
        assertEquals(result.get(0), savedBoard)
    }

    @Test
    fun 게시글_삭제() {
        // given
        val savedBoard = boardJpaRepository.save(게시글_생성())

        // when
        boardJpaRepository.delete(savedBoard)

        // then
        val result = boardJpaRepository.findAll()
        assertEquals(result.isEmpty(), true)
    }
}
