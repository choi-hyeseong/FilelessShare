package com.example.filelessshare.service

import com.example.filelessshare.dto.FileDecodeDTO
import com.example.filelessshare.dto.FileEncodeDTO
import org.apache.logging.log4j.util.Base64Util
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.Base64
import java.util.zip.Deflater
import java.util.zip.Inflater

@Service
class FileService {

    private val deflater : Deflater = Deflater()
    private val inflater : Inflater = Inflater()

    fun encode(file : MultipartFile) : FileEncodeDTO {
        val isSuccess : Boolean
        val message : String
        val encode : String?
        if (file.bytes.isEmpty()) {
            isSuccess = false
            message = "파일이 비어있습니다."
            encode = null
        }
        else {
            deflater.setLevel(Deflater.BEST_COMPRESSION)
            deflater.setInput(file.bytes)
            deflater.finish()
            val stream = ByteArrayOutputStream(file.bytes.size)
            val buf = ByteArray(1024)
             while (!deflater.finished()) {
                    val count = deflater.deflate(buf);
                    stream.write(buf, 0, count);
             }
            try {
                stream.close();
            } catch (e : IOException) {
                e.printStackTrace()
                }

        // Get the compressed data
        val compressedData = stream.toByteArray();
            encode = "${Base64.getUrlEncoder().encodeToString(compressedData)}.${file.originalFilename?.split(".")?.get(1)}"
            message = "성공"
            isSuccess = true
        }
        return FileEncodeDTO(isSuccess, message, encode)

    }

    fun decode(input : String) : FileDecodeDTO {
        val split = input.split(".")
        val encoded = Base64.getUrlDecoder().decode(split[0])
        val ext = ".${split[1]}"
        inflater.setInput(encoded)
        val stream = ByteArrayOutputStream(100000)
        val buf = ByteArray(1024)
        while (!inflater.finished()) {
            val count = inflater.inflate(buf);
            stream.write(buf, 0, count);
        }
        try {
            stream.close();
        } catch (e : IOException) {
        }

        // Get the compressed data
        return FileDecodeDTO(true, "${System.currentTimeMillis()}${ext}", ByteArrayInputStream(stream.toByteArray()))
    }
}