package com.example.ferreteria

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_actualizar_productos.view.*
import kotlinx.android.synthetic.main.activity_fila_producto.view.*

class AdaptarProductos (val contenido:Context, val layoutId:Int, val listaProducto:List<Productos>)
    : ArrayAdapter<Productos>(contenido, layoutId, listaProducto){

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // return super.getView(position, convertView, parent
        val layoutInflater: LayoutInflater = LayoutInflater.from(contenido)
        val view:View=layoutInflater.inflate(layoutId,null)

        val producto=view.productoIn
        val precio=view.precioIn
        val cantidad=view.cantidadIn
        val seccion=view.seccioIn

        val actuBtn=view.actualizaBoton
        val eliBtn=view.imagenEliminar

        val lista= listaProducto[position]

        producto.text="PRODUCTO:  "+lista.producto
        precio.text="PRECIO:  "+lista.precio
        cantidad.text="CANTIDAD:  "+lista.cantidad
        seccion.text=lista.seccion


        actuBtn.setOnClickListener {
            actualizarRegistro(lista)
        }

        eliBtn.setOnClickListener {
            eliminarRegistro(lista)
        }

        return view
    }



    private fun actualizarRegistro(producto: Productos){

        val builder= AlertDialog.Builder(contenido)
        builder.setTitle("Actualizar Producto")
        val inflater=LayoutInflater.from(contenido)
        val view=inflater.inflate(R.layout.activity_actualizar_productos, null)

        val spinner=view.findViewById<Spinner>(R.id.idSpinner2)
        spinner.selectedItem.toString()

        val product=view.productoAc
        val precio=view.precioAc
        val cantidad=view.cantidadAc
        val seccion=view.seccionAc

        product.setText(producto.producto)
        precio.setText(producto.precio)
        cantidad.setText(producto.cantidad)
        seccion.setText("Sección actual: "+producto.seccion)

        builder.setView(view)

        builder.setPositiveButton("Actualizar", object : DialogInterface.OnClickListener{//implement ainterfa
        override fun onClick(dialog:DialogInterface, which:Int) {
            val db = FirebaseDatabase.getInstance().getReference("Productos")

            val cambioProducto = product.text.toString().trim()
            val cambioPrecio = precio.text.toString().trim()
            val cambioCantidad = cantidad.text.toString().trim()

            val cambioRegistro = Productos(producto.id, cambioProducto, cambioPrecio, cambioCantidad,  spinner.selectedItem.toString())
            db.child(cambioRegistro.id).setValue(cambioRegistro)
            Toast.makeText(contenido, "Producto Actualizado", Toast.LENGTH_SHORT).show()
        }
        })

        builder.setNegativeButton("Cancelar", object :DialogInterface.OnClickListener{
            override fun onClick(dialog:DialogInterface, which:Int) {
            }
        })
        val alert=builder.create()
        alert.show()
    }




    private fun eliminarRegistro(produc: Productos){
        val db=FirebaseDatabase.getInstance().getReference("Productos")
        db.child(produc.id).removeValue()
        Toast.makeText(contenido,"Registro Eliminado", Toast.LENGTH_SHORT).show()
    }



}
