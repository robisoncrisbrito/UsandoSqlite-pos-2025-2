package br.edu.utfpr.usandosqlite_2025_2.entity

import com.google.firebase.firestore.DocumentId

data class Cadastro(
    @DocumentId
    val _id: String = "", // ID agora é nullable e tem valor padrão
    val nome: String = "",   // Valor padrão adicionado
    val telefone: String = "" // Valor padrão adicionado
)
