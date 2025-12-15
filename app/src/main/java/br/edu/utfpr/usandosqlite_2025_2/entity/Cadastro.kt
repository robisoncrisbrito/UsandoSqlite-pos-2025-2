package br.edu.utfpr.usandosqlite_2025_2.entity

import com.google.firebase.firestore.DocumentId

data class Cadastro(
    @DocumentId
    val _id: String? = null,
    val nome: String = "",
    val telefone: String = ""
)
