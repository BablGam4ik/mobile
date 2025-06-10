package com.example.criminalintent

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.criminalintent.database.CrimeDatabase
import java.util.UUID
import java.util.concurrent.Executor
import java.util.concurrent.Executors

private const val DATABASE_NAME = "crime-database"
class CrimeRepository private constructor(context: Context){
    private val database: CrimeDatabase = Room.databaseBuilder(
        context.applicationContext,
        CrimeDatabase::class.java,
        DATABASE_NAME).build()
    private val crimeDao = database.crimeDAO()
    private val executor: Executor = Executors.newSingleThreadExecutor()

    fun getCrimes(): LiveData<List<Crime>>{
        return crimeDao.getCrimes()
    }
    fun getCrime(uuid: UUID): LiveData<Crime?>{
        return  crimeDao.getCrime(uuid)
    }
    fun updateCrime(crime: Crime){
        executor.execute {
            crimeDao.updateCrime(crime)
        }
    }
    fun addCrime(crime: Crime){
        executor.execute{
            crimeDao.insertCrime(crime)
        }
    }
    companion object{
        private var INSTANCE: CrimeRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null){
                INSTANCE = CrimeRepository(context)
            }
        }
        fun get(): CrimeRepository{
            return INSTANCE?: throw IllegalStateException("Репозиторий должен быть инициализирован")
        }
    }
}