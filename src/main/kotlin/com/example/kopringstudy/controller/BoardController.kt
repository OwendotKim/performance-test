package com.example.kopringstudy.controller

import com.example.kopringstudy.controller.dto.BoardResponse
import com.example.kopringstudy.domain.Board
import com.example.kopringstudy.service.BoardService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import java.util.*
import java.text.SimpleDateFormat
import java.util.Date

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
        println(dummyLoad)
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

    @PostMapping("/boards/upload")
    fun uploadFile(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        return try {
            val uniqueFolderName = UUID.randomUUID().toString() // UUID 변수 생성
            val uploadDir = Path.of("uploads", uniqueFolderName) // 랜덤 폴더 생성
            Files.createDirectories(uploadDir) // 폴더 생성

            val targetLocation = uploadDir.resolve(file.originalFilename!!)

            file.inputStream.use { inputStream ->
                Files.newOutputStream(targetLocation).use { outputStream ->
                    val buffer = ByteArray(1024 * 1024) // 1MB 크기의 청크
                    var bytesRead: Int
                    var totalBytesRead = 0L

                    while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                        outputStream.write(buffer, 0, bytesRead)
                        totalBytesRead += bytesRead
                        println("Uploaded chunk of size: $bytesRead bytes, Total uploaded: $totalBytesRead bytes")
                    }
                }
            }

            ResponseEntity.ok("File uploaded successfully: Folder=$uniqueFolderName, File=${file.originalFilename}")
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: ${e.message}")
        }
    }

    @GetMapping("/boards/zip")
    fun zipAndCleanUploads(): ResponseEntity<String> {
        return try {
            val uploadsDir = Path.of("uploads") // 업로드 폴더 경로
            if (!Files.exists(uploadsDir)) {
                return ResponseEntity.ok("Uploads directory does not exist. Nothing to zip.")
            }

            // ZIP 파일 이름을 날짜 기반으로 생성
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val zipFileName = "$timestamp.zip"
            val zipFilePath = uploadsDir.resolve(zipFileName)

            // 압축할 파일 리스트 가져오기 (기존 ZIP 제외)
            val filesToZip = Files.walk(uploadsDir)
                .filter { Files.isRegularFile(it) && !it.toString().endsWith(".zip") }
                .collect(Collectors.toList()) // Stream을 List로 변환


            if (filesToZip.isEmpty()) {
                return ResponseEntity.ok("No new files to zip.")
            }

            // ZIP 생성
            ZipOutputStream(FileOutputStream(zipFilePath.toFile())).use { zipOut ->
                filesToZip.forEach { filePath ->
                    zipOut.putNextEntry(ZipEntry(uploadsDir.relativize(filePath).toString()))
                    Files.copy(filePath, zipOut)
                    zipOut.closeEntry()
                }
            }

            // 압축 완료 후 원본 파일 삭제
            filesToZip.forEach { Files.delete(it) }

            ResponseEntity.ok("ZIP created successfully: $zipFileName")
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create ZIP: ${e.message}")
        }
    }

}