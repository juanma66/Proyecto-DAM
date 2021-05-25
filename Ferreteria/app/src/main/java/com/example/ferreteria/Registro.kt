package com.example.ferreteria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_registro.*

class Registro : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()//enstancia conectada a la db
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        registro()
        volver()
    }


    private fun registro() {
        title = "Registro nuevo trabajador"
        reRegistro.setOnClickListener {
            if (reEmail.text.isNotEmpty() && reContrasema.text.isNotEmpty()) {
                    if (validadContrasena() && validadCampos()) {
                        auth.createUserWithEmailAndPassword(reEmail.text.toString(), reContrasema.text.toString()).addOnCompleteListener {

                        if (it.isSuccessful) {
                            Toast.makeText(this, "Usuario registrado", Toast.LENGTH_SHORT).show()//si no se rellena los campos

                            //pasar lod datos
                            db.collection("trabajadores")//tabla trabajadoes
                                    .document(reEmail.text.toString()).set(hashMapOf(
                                            "Nombre:" to reNombre.text.toString().trim(),
                                            "Apellidos:" to reApellidos.text.toString().trim(),
                                            "Teléfono:" to reTelefono.text.toString().trim(),
                                            "Email:" to reEmail.text.toString().trim()
                                    ))

                            //cambio de activity
                            reRegistro()

                        } else {
                            Toast.makeText(this, "Error, campo Email Incorrecto", Toast.LENGTH_SHORT).show()//si no se rellena los campos
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Rellene los campos", Toast.LENGTH_SHORT).show()//si no se rellena los campos
            }
        }
    }


    private fun validadContrasena(): Boolean {
        val primera = reContrasema
        val segunda = reContrasema2

         if (primera.length()<9){
             Toast.makeText(this, "Más de 9 caracteres para la contraseña", Toast.LENGTH_SHORT).show()//si no se rellena los campos
             return false
         }
        if(primera.text.toString() == segunda.text.toString()) {//se verifica que las contraseñas son iguales
            return true
        } else {
            Toast.makeText(this, "Contraseña no coiciden", Toast.LENGTH_SHORT).show()//si no se rellena los campos
            return false
        }
    }


    private fun validadCampos(): Boolean {
        val vNombre = reNombre
        val vApellido = reApellidos
        val vTelefono = reTelefono

            if (vNombre.text.isEmpty()) {
                Toast.makeText(this, "Rellene el campo Nombre", Toast.LENGTH_SHORT).show()//si no se rellena los campos
                return false
            } else {
                if (vApellido.text.isEmpty()) {
                    Toast.makeText(this, "Rellene el campo Apellidos", Toast.LENGTH_SHORT).show()//si no se rellena los campos
                    return false
                } else {
                    if (vTelefono.text.isEmpty()) {
                        Toast.makeText(this, "Rellene el campo Telefóno", Toast.LENGTH_SHORT).show()//si no se rellena los campos
                        return false
                    }
                }
            }
            return true

    }




   private fun reRegistro(){//boton que vuelve
            val cambio = Intent(this, MainActivity::class.java)
            startActivity(cambio)
    }



    private fun volver(){//boton que vuelve
        reVolver.setOnClickListener {
            val cambio = Intent(this, MainActivity::class.java)
            startActivity(cambio)
        }
    }

   }
