package com.example.myfirebaseexample.api.response

import com.google.gson.annotations.SerializedName

data class BiciResponse(
    @SerializedName("00_Tipo") var type: String,
    @SerializedName("20_Servicio") var brand: String,
    @SerializedName("14_Cliente") var names: String,
    @SerializedName("16_Costo") var cost: Long
)
