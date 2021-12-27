package com.example.flickrbrowserapp.temp

import com.example.flickrbrowserapp.temp.Photo

data class PhotosX(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val photo: List<Photo>,
    val total: Int
)