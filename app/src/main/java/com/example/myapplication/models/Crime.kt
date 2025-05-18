package com.example.myapplication.models

import java.util.Date
import java.util.UUID

data class Crime(
    val id: UUID = UUID.randomUUID(),
    var title: String = "",  // Изменили val на var
    val date: Date = Date(),
    var isSolved: Boolean = false  // Изменили val на var
)