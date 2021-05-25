package com.example.ferreteria

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_datos_productos.*
import kotlinx.android.synthetic.main.activity_zona_admin.*


class ZonaAdmin :AppCompatActivity() {

    private lateinit var listaComentario: MutableList<Comentario>
    private lateinit var listaVista1: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zona_admin)

        irAl()
        salir()

        listaComentario = mutableListOf()
        listaVista1 = listaCom


        //////bloque de ordenar lista

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Comentarios")

        myRef.addValueEventListener(object : ValueEventListener {
            //se debeimplemetar los metodos
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    listaComentario.clear()//elimina por si tiene algo en cache
                    for (p1 in snapshot.children) {
                        val prod1 = p1.getValue(Comentario::class.java)
                        listaComentario.add(prod1!!)
                    }
                    val adapter = AdaptadorComentarios(
                        this@ZonaAdmin,
                        R.layout.filas_cometario,
                        listaComentario
                    )
                    listaVista1.adapter = adapter
                }
            }

            //se debe implemeta los metodos
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun irAl() {
        volverInicio2.setOnClickListener {
            val cambio = Intent(this, DatosProducto::class.java)
            startActivity(cambio)
        }
    }


    private fun salir() {
        salir.setOnClickListener {
            val cambio = Intent(this, MainActivity::class.java)
            startActivity(cambio)
        }
    }


}