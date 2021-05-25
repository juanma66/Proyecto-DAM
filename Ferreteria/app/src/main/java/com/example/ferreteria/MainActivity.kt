package com.example.ferreteria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTheme(R.style.PantallaInicio)//esto no hace falta
        login()
        irRegsitro()
    }

        fun login() {
            title = "Autenticación"
            idAcceso.setOnClickListener {
                if (idEmail.text.isNotEmpty() && idPassword.text.isNotEmpty()) {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(
                        idEmail.text.toString(),
                        idPassword.text.toString()

                    ).addOnCompleteListener() {//si todo va bien
                        if (it.isSuccessful) {
                            if(idEmail.text.toString()=="ilerna@hotmail.com"){
                                Toast.makeText(this, "Admin", Toast.LENGTH_SHORT)
                                        .show()//si no se rellena los campos(this, Inicio::class.java)
                                irAdmin()
                            }else{
                            Toast.makeText(this, "Almacen", Toast.LENGTH_SHORT)
                                .show()//si no se rellena los campos(this, Inicio::class.java)

                            irAlmacen()
                            }
                        } else {
                            idEmail.setText("")//borrar datos
                            idPassword.setText("")

                            Toast.makeText(this, "Error en la autenticación", Toast.LENGTH_SHORT)
                                .show()//si no se rellena los campos
                        }
                    }
                        .addOnFailureListener {
                            Toast.makeText(this, "Error en la autenticación", Toast.LENGTH_SHORT)
                        }
                } else {
                    Toast.makeText(this, "Rellene los campos vacios", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }


//////////////////////////////////ir a registro
            private  fun irRegsitro(){
                idGuarda.setOnClickListener{
                    val cambio =Intent(this,Registro::class.java)
                    startActivity(cambio)
                }
          }


            private fun irAlmacen(){
                val cambio = Intent(this, DatosProducto::class.java)
                startActivity(cambio)
            }


            private fun irAdmin(){
                val cambio = Intent(this, ZonaAdmin::class.java)
                startActivity(cambio)
            }


        }


