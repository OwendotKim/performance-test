package com.example.kopringstudy.service

import com.example.kopringstudy.domain.Board
import com.example.kopringstudy.fixture.BoardFixture.Companion.게시글_생성
import com.example.kopringstudy.infrastructure.BoardFakeRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

@SuppressWarnings("NonAsciiCharacters")
class BoardServiceTest() {

    private lateinit var boardFakeRepository: BoardFakeRepository
    private lateinit var boardService: BoardService

    @BeforeEach
    fun setup() {
        boardFakeRepository = BoardFakeRepository();
        boardService = BoardService(boardFakeRepository)
    }

    @Test
    fun 게시글_저장() {
        // given
        val board = 게시글_생성()

        // when & then
        val result = assertDoesNotThrow {
            boardService.write(board)
        }
    }

    @Test
    fun 게시글을_모두_찾는다() {
        // given
        val savedBoard = boardFakeRepository.save(게시글_생성())

        // when
        val found = boardService.findAll()

        // then
        assertEquals(found.size, 1)
        assertEquals(found.get(0), savedBoard)
    }

    @Test
    fun 게시글_단건_조회() {
        // given
        val savedBoard = boardFakeRepository.save(게시글_생성())

        // when
        val result = boardService.findById(id = savedBoard.id!!)

        // then
        assertEquals(result, savedBoard)
    }

    @Test
    fun 게시글_업데이트() {
        // given
        val savedBoard = boardFakeRepository.save(게시글_생성())

        // when & then
        boardService.edit(savedBoard.id!!, Board("edit", "edit"));

        // then
        val result = boardFakeRepository.findById(savedBoard.id!!)
        assertEquals(result.isPresent, true)
        assertEquals(result.get().title, "edit")
    }
}
