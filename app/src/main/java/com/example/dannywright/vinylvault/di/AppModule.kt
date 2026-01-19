package com.example.dannywright.vinylvault.di

import android.app.Application
import androidx.room.Room
import com.example.dannywright.vinylvault.data.local.VinylVaultDatabase
import com.example.dannywright.vinylvault.data.local.dao.ReleaseDao
import com.example.dannywright.vinylvault.data.remote.DiscogsApiService
import com.example.dannywright.vinylvault.data.repository.VinylRepositoryImpl
import com.example.dannywright.vinylvault.domain.repository.VinylRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val original = chain.request()
                val originalUrl = original.url

                val url = originalUrl.newBuilder()
                    .addQueryParameter("token", "rKjPECDmkuZrwsaNIqrnQKTsDfBcnlaOsnyfAjEG")
                    .build()

                val request = original.newBuilder()
                    .url(url)
                    .header("User-Agent", "VinylVault/1.0")
                    .build()

                chain.proceed(request)
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideDiscogsApi(client: OkHttpClient): DiscogsApiService {
        return Retrofit.Builder()
            .baseUrl(DiscogsApiService.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DiscogsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideVinylVaultDatabase(app: Application): VinylVaultDatabase {
        return Room.databaseBuilder(
            app,
            VinylVaultDatabase::class.java,
            "vinyl_vault_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideReleaseDao(database: VinylVaultDatabase): ReleaseDao {
        return database.releaseDao()
    }

    @Provides
    @Singleton
    fun provideVinylRepository(
        api: DiscogsApiService,
        dao: ReleaseDao
    ): VinylRepository {
        return VinylRepositoryImpl(api, dao)
    }
}