package com.example.ferreteria

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_actualizar_productos.*
import kotlinx.android.synthetic.main.activity_datos_productos.*
import kotlinx.android.synthetic.main.activity_datos_usuario.*
import kotlinx.android.synthetic.main.activity_datos_usuario.view.*
import kotlinx.android.synthetic.main.activity_fila_producto.*
import kotlinx.android.synthetic.main.activity_registro.*

class DatosProducto : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()//enstancia conectada a la db
    private val auth = FirebaseAuth.getInstance()

    private lateinit var listaAlmacen: MutableList<Productos>
    private lateinit var listaVista: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datos_productos)

        re2()
        re()
        vcom()
        recuperaUser()
        datosUsuario()

        listaAlmacen = mutableListOf()
        listaVista = listview1

        //////bloque de ordenar lista
                val ref: Query = FirebaseDatabase.getInstance().reference//realizamos un query para colocar en ta tbla Producto un orde apatir de el camopo seccion
                        .child("Productos")
                        .orderByChild("seccion")

        ref.addValueEventListener(object : ValueEventListener {
            //se debeimplemetar los metodos
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    listaAlmacen.clear()//elimina por si tiene algo en cache
                    for (p in snapshot.children) {
                        val prod = p.getValue(Productos::class.java)
                        listaAlmacen.add(prod!!)
                    }
                    val adapter = AdaptarProductos(
                            this@DatosProducto,
                            R.layout.activity_fila_producto,
                            listaAlmacen)

                    listaVista.adapter = adapter
                }
            }
            //se debe implemeta los metodos
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun re() {
        actualizaBoton2.setOnClickListener {
            val cambio = Intent(this, RegistroAlmacen::class.java)
            startActivity(cambio)
        }
    }

    private fun vcom() {
        idComi.setOnClickListener {
            val cambio = Intent(this, RegistroComentario::class.java)
            startActivity(cambio)
        }
    }



    private fun re2() {
        volverInicio.setOnClickListener {
            val cambio = Intent(this, MainActivity::class.java)
            startActivity(cambio)
        }
    }


    private fun recuperaUser() {///recuperamos el usuario auntetnicado cogemor el email y se lo pasamos a el documento de tabla trabajadore asi nos sacara el nomere de ese trabajador
        val user = auth.currentUser
        if (user != null) {//si es distinto a null
            user?.let {
                val usuario = user.email

                db.collection("trabajadores").document(usuario).get().addOnSuccessListener {
                    correoUsuario.setText(it.get("Nombre:") as String?)
                }
            }
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
    }


    private fun datosUsuario() {
        mainLoginBtn.setOnClickListener {
            val user = auth.currentUser
                user?.let {
                    val usuario = user.email

                    db.collection("trabajadores").document(usuario).get().addOnSuccessListener {

                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("Cambiar datos")
                        val inflater = LayoutInflater.from(this)
                        val vista = inflater.inflate(R.layout.activity_datos_usuario, null)

                        vista.nombreUs.setText(it.get("Nombre:") as String?)
                        vista.apellidosUs.setText(it.get("Apellidos:") as String?)
                        vista.emailUs.setText(it.get("Email:") as String?)
                        vista.telefonoUser.setText(it.get("Telefóno:") as String?)

                        builder.setView(vista)//para que se visualice en la ventana

                        builder.setPositiveButton("Cambiar", object : DialogInterface.OnClickListener{//implement ainterfa
                                override fun onClick(dialog:DialogInterface, which:Int) {

                                 finish(); startActivity(getIntent());//para refresca la activity y se cambie el nombre

                                db.collection("trabajadores").document(usuario).set(
                                        hashMapOf("Nombre:" to vista.nombreUs.text.toString().trim(),
                                                   "Apellidos:" to vista.apellidosUs.text.toString().trim(),
                                                  "Email:" to vista.emailUs.text.toString().trim(),
                                                  "Telefóno:" to vista.telefonoUser.text.toString().trim())) }
                        })


                        builder.setNegativeButton("Cancelar", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, which: Int) {
                    }
                })
                val alert = builder.create()
                alert.show()
                    }
          }
      }
    }


}
