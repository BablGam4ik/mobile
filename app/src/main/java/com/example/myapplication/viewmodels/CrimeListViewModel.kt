package com.example.myapplication.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myapplication.models.Crime
import java.util.Date

class CrimeListViewModel : ViewModel() {
    val crimes = List(10) { index ->
        Crime(
            title = "Crime #$index",
            date = Date(),
            isSolved = index % 2 == 0
        )
    }
}