package com.example.ferreteria

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.filas_cometario.view.*

class AdaptadorComentarios (val contenid:Context, val id:Int, val listaComentarios:List<Comentario>)
    :ArrayAdapter<Comentario>(contenid,id,listaComentarios){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val layoutInflater: LayoutInflater= LayoutInflater.from(contenid)
        val view: View=layoutInflater.inflate(id,null)

        val eliBtn=view.eliminrCome

        val autor=view.idAutor
        val titulo=view.idTitulo
        val comentario=view.idContenido

        val listaCo=listaComentarios[position]

        autor.text=listaCo.autor
        titulo.text=listaCo.titulo
        comentario.text=listaCo.contenido

        eliBtn.setOnClickListener {
            eliComentaria(listaCo)
        }

        return view
    }


    private fun eliComentaria(come: Comentario){
        val db= FirebaseDatabase.getInstance().getReference("Comentarios")
        db.child(come.id).removeValue()
        Toast.makeText(context,"Mensaje Eliminado", Toast.LENGTH_SHORT).show()
    }

}