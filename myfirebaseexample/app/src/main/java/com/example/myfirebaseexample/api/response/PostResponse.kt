package com.example.myfirebaseexample.api.response

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("name") var id: String
)
