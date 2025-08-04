package com.example.passgen.di

import android.content.Context
import androidx.room.Room
import com.example.passgen.data.local.PasswordDao
import com.example.passgen.data.local.PasswordDatabase
import com.example.passgen.data.repository.PasswordRepository
import com.example.passgen.data.repository.PasswordRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PasswordDatabase {
        return Room.databaseBuilder(
            context,
            PasswordDatabase::class.java,
            "passwords.db"
        ).build()
    }

    @Provides
    fun provideDao(database: PasswordDatabase): PasswordDao {
        return database.passwordDao()
    }

    @Provides
    fun providePasswordRepository(
        dao: PasswordDao
    ): PasswordRepository {
        return PasswordRepositoryImpl(dao)
    }

}