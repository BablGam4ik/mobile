package com.example.criminalintent

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import java.util.UUID

private const val TAG = "CrimeDetail"
class CrimeDetailViewModel(): ViewModel() {
    private val crimeRepository = CrimeRepository.get()
    private val crimeIdLiveData = MutableLiveData<UUID>()

    var crimeLiveData: LiveData<Crime?> = crimeIdLiveData.switchMap {
        crimeRepository.getCrime(it)
    }
    fun loadCrime(crimeId: UUID){
        crimeIdLiveData.value = crimeId
    }
    fun saveCrime(crime: Crime){
        crimeRepository.updateCrime(crime)
    }

}