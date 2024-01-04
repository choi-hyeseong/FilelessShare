package com.example.filelessshare.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class FileEncodeDTO(val isSuccess : Boolean, val message : String, val data : String?)