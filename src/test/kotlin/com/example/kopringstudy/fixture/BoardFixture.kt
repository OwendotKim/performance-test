package com.example.kopringstudy.fixture

import com.example.kopringstudy.domain.Board

class BoardFixture {

    companion object {
        fun 게시글_생성(): Board {
            return Board("title", "content")
        }
    }
}
