package com.example.myfirebaseexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.myfirebaseexample.api.FirebaseApiAdapter
import com.example.myfirebaseexample.api.response.BiciResponse
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    // Referenciar campos de las interfaz
    private lateinit var idSpinner: Spinner
    private lateinit var serviceTypeSpinner: Spinner
    private lateinit var namesField: EditText
    private lateinit var costField: EditText
    private lateinit var brandField: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonLoad: Button

    // Referenciar la API
    private var firebaseApi = FirebaseApiAdapter()

    // Mantener los nombres e IDs de las armas
    private var biciList = arrayListOf<String>()
    private var servicesList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        idSpinner = findViewById(R.id.idSpinner)
        serviceTypeSpinner = findViewById(R.id.serviceTypeSpinneer)
        namesField = findViewById(R.id.namesField)
        costField = findViewById(R.id.costField)
        brandField = findViewById(R.id.brandField)
        servicesList.add("A domicilio")
        servicesList.add("En local")

        val serviceAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, servicesList)
        with(serviceTypeSpinner) {
            adapter = serviceAdapter
            setSelection(0, false)
            gravity = Gravity.CENTER
        }

        buttonLoad = findViewById(R.id.buttonLoad)
        buttonLoad.setOnClickListener {
            Toast.makeText(this, "Cargando información", Toast.LENGTH_SHORT).show()
            runBlocking {
                getBiciFromApi()
            }
        }

        buttonSave = findViewById(R.id.buttonSave)
        buttonSave.setOnClickListener {
            Toast.makeText(this, "Guardando información", Toast.LENGTH_SHORT).show()
            runBlocking {
                sendBiciToApi()
            }
        }

        runBlocking {
            populateIdSpinner()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private suspend fun populateIdSpinner() {
        val response = GlobalScope.async(Dispatchers.IO) {
            firebaseApi.getBicis()
        }
        val bicis = response.await()
        bicis?.forEach { entry ->
            biciList.add("${entry.key}: ${entry.value.name}")
        }
        val biciAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, biciList)
        with(idSpinner) {
            adapter = biciAdapter
            setSelection(0, false)
            gravity = Gravity.CENTER
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private suspend fun getBiciFromApi() {
        val selectedItem = idSpinner.selectedItem.toString()
        val biciId = selectedItem.subSequence(0, selectedItem.indexOf(":")).toString()
        println("Loading ${biciId}... ")
        val biciResponse = GlobalScope.async(Dispatchers.IO) {
            firebaseApi.getBici(biciId)
        }
        val bici = biciResponse.await()
        brandField.setText(bici?.brand)
        namesField.setText("${bici?.names}")
        costField.setText("${bici?.cost}")
    }

    @OptIn(DelicateCoroutinesApi::class)
    private suspend fun sendBiciToApi() {
        val brandName = brandField.text.toString()
        val names = namesField.text.toString()
        val cost = costField.text.toString().toLong()
        val bici = BiciResponse("", brandName, names, cost, "")
        val BiciResponse = GlobalScope.async(Dispatchers.IO) {
            firebaseApi.setBici(bici)
        }
        BiciResponse.await()
        brandField.setText(bici.brand)
        namesField.setText(bici.names)
        costField.setText("${bici.cost}")

        biciList= arrayListOf<String>()
        populateIdSpinner()
    }

}
