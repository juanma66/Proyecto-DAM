package com.example.ferreteria

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_registro_almacen.*

class RegistroAlmacen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_almacen)

        guardaDatos()
        irAlmacen()
    }


    private fun guardaDatos() {
        title="Registro Producto"
        guardabtn.setOnClickListener {
            val db = FirebaseDatabase.getInstance().reference.child("Productos")
            if (InsertProducto.text.isNotEmpty() && InsertPrecio.text.isNotEmpty()
                    && InsertCantidad.text.isNotEmpty()){

                val productoId = db.push().key.toString()//cogemos la id creada automaticamente y la guardamos en una variable
                val prod = Productos(productoId,
                    InsertProducto.text.toString(),
                    InsertPrecio.text.toString(),
                    InsertCantidad.text.toString(),
                    idSpinner.selectedItem.toString())
                 //pasamos a mascula para luego aplicar un orde en la colocacion
                db.child(productoId).setValue(prod).addOnCompleteListener {
                    Toast.makeText(this, "Producto Guardado", Toast.LENGTH_SHORT).show()

                    InsertProducto.setText("")//borrar datos
                    InsertPrecio.setText("")
                    InsertCantidad.setText("")
                }
            } else {
                Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT)
                        .show()//si no se rellena los campos
            }
        }
    }



    private fun irAlmacen(){//boton que vuelve
        almacenbtn.setOnClickListener{
            val cambio = Intent(this, DatosProducto::class.java)
            startActivity(cambio)
        }
    }


}