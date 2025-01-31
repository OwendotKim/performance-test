package com.example.kopringstudy.controller.dto

import com.example.kopringstudy.domain.Board

data class BoardResponse(
        var id: Long?,
        var title: String,
        var content: String
) {

    companion object {

        fun from(board: Board): BoardResponse = BoardResponse(
                id = board.id,
                title = board.title,
                content = board.content
        )

        fun fromByMe(board: Board): BoardResponse {
            return BoardResponse(board.id, board.title, board.content)
        }
    }
}
