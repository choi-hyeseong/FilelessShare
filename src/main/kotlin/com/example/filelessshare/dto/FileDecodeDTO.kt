package com.example.filelessshare.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.io.InputStream
import java.io.OutputStream


@JsonInclude(JsonInclude.Include.NON_NULL)
class FileDecodeDTO(val isSuccess : Boolean, val name : String, val data : InputStream)