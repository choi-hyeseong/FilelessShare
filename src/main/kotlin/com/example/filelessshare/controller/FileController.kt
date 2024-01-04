package com.example.filelessshare.controller

import com.example.filelessshare.dto.FileDecodeDTO
import com.example.filelessshare.dto.FileEncodeDTO
import com.example.filelessshare.service.FileService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile

@Controller
class FileController(private val fileService: FileService) {

    @PostMapping("/request")
    fun upload(file : MultipartFile) : ResponseEntity<FileEncodeDTO> {
        val response = fileService.encode(file)
        return if (response.isSuccess) ResponseEntity.ok(response) else ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    //blahblah.com/download?data=~~~~~
    @GetMapping("/download")
    fun download(@RequestParam(value = "data", required = true) data : String, response : HttpServletResponse) : String {
        val dto : FileDecodeDTO = fileService.decode(data)
        response.setHeader("Content-Disposition", "attachment;filename=" + dto.name);
        response.outputStream.use {
            var read = 0;
            val buffer = ByteArray(1024)
            while (true) { // 1024바이트씩 계속 읽으면서 outputStream에 저장, -1이 나오면 더이상 읽을 파일이 없음
                read = dto.data.read(buffer)
                if (read == -1)
                    break
                it.write(buffer, 0, read);
            }
        }
        return ""
    }
}