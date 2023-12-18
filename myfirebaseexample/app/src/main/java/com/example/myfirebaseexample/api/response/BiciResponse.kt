package com.example.myfirebaseexample.api.response

import com.google.gson.annotations.SerializedName

data class BiciResponse(
    @SerializedName("00_Id") var id: String,
    @SerializedName("01_Servicio") var name: String,
    @SerializedName("14_Cliente") var names: String,
    @SerializedName("16_Costo") var cost: Long,
    @SerializedName("20_Servicio") var brand: String
)
