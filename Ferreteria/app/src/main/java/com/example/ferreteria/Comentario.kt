package com.example.ferreteria

class Comentario(
                    val id:String,
                    val autor:String,
                    val titulo:String,
                    val contenido:String) {

    constructor():this("","","",""){}
}