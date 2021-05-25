package com.example.ferreteria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_registro_comentario.*

class RegistroComentario : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()//enstancia conectada a la db
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_comentario)
        guardaMensaje()
        irAlma()


                ///aparece el usaurio que escribe
        val user = auth.currentUser
        user?.let {
            val usuario = user.email

            db.collection("trabajadores").document(usuario).get().addOnSuccessListener {
                insertAutor.setText(it.get("Nombre:") as String?)

            }
        }
    }




    private fun guardaMensaje() {
        title = "Mensaje"
        guardaCome.setOnClickListener {

                val db = FirebaseDatabase.getInstance().reference.child("Comentarios")

                if (insertTitulo.text.isNotEmpty()
                        && insertComentario.text.isNotEmpty()) {

                    val comentarioID = db.push().key.toString()//cogemos la id creada automaticamente y la guardamos en una variable
                    val prod = Comentario(comentarioID, insertAutor.text.toString(), insertTitulo.text.toString(), insertComentario.text.toString())
                    //pasamos a mascula para luego aplicar un orde en la colocacion

                    db.child(comentarioID).setValue(prod).addOnCompleteListener {
                        Toast.makeText(this, "Mensaje Guardado", Toast.LENGTH_SHORT).show()

                        insertTitulo.setText("")
                        insertComentario.setText("")
                    }

                } else {
                    Toast.makeText(this, "Rellene todos los campos", Toast.LENGTH_SHORT)
                            .show()//si no se rellena los campos
                }
            }
        }





    private fun irAlma() {
        volveAlmacen.setOnClickListener {
            val cambio = Intent(this, DatosProducto::class.java)
            startActivity(cambio)
        }
    }

}