package com.example.kopringstudy.controller

import com.example.kopringstudy.controller.dto.BoardResponse
import com.example.kopringstudy.domain.Board
import com.example.kopringstudy.service.BoardService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class BoardController(
    private val boardService: BoardService
) {

    @GetMapping("/boards")
    fun findAllBoards(): ResponseEntity<List<Board>> {
        Thread.sleep(1000) // 1초(1000ms) 동안 지연
        return ResponseEntity(boardService.findAll(), HttpStatus.OK)
    }

    @GetMapping("/boards/{id}")
    fun findBoard(@PathVariable("id") id: Long): ResponseEntity<BoardResponse> {
        // 불필요한 연산 추가 (더미 계산 작업)
        val board = boardService.findById(id)
        val dummyLoad = (1..10000).sumOf { it * it }
        return ResponseEntity.ok(BoardResponse.from(board))
    }

    @PostMapping("/boards")
    fun write(@RequestBody board: Board): ResponseEntity<Board> {
        Thread.sleep(1000) // 1초(1000ms) 동안 지연
        return ResponseEntity<Board>(boardService.write(board), HttpStatus.CREATED)
    }

    @PutMapping("/boards/{id}")
    fun edit(@PathVariable id: Long, @RequestBody board: Board): ResponseEntity<Board> {
        return ResponseEntity(boardService.edit(id, board), HttpStatus.OK)
    }

    @DeleteMapping("/boards/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<String> {
        return ResponseEntity<String>(boardService.delete(id), HttpStatus.OK)
    }
}