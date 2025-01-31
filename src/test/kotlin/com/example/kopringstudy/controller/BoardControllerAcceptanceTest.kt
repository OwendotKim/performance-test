package com.example.kopringstudy.controller

import com.example.kopringstudy.domain.BoardRepository
import com.example.kopringstudy.fixture.BoardFixture
import com.example.kopringstudy.helper.IntegrationHelper
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

@SuppressWarnings("NonAsciiCharacters")
class BoardControllerAcceptanceTest : IntegrationHelper() {

    @Autowired
    private lateinit var boardRepository: BoardRepository

    @Test
    fun 게시글_생성() {
        // given
        val board = BoardFixture.게시글_생성();

        // when
        val result = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(board)
                .post("/api/boards")
                .then().log().all()
                .extract()

        // then
        assertEquals(result.statusCode(), HttpStatus.CREATED.value())
    }

    @Test
    fun 게시글_단건_조회() {
        // given
        boardRepository.save(BoardFixture.게시글_생성())

        // when
        val result = RestAssured.given().log().all()
                .get("/api/boards/1")
                .then().log().all()
                .extract()

        // then
        assertEquals(result.statusCode(), HttpStatus.OK.value())
    }
}
