package com.kurmakaeva.anastasia.lockquote

import android.app.Application
import android.content.Context
import com.kurmakaeva.anastasia.lockquote.repository.GeniusRepo
import com.kurmakaeva.anastasia.lockquote.repository.InterfaceGeniusRepo
import com.kurmakaeva.anastasia.lockquote.service.GeniusSearchService
import com.kurmakaeva.anastasia.lockquote.service.retrofit
import com.kurmakaeva.anastasia.lockquote.viewmodel.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        context = this

        val networkModule = module {
            single {
                retrofit.create(GeniusSearchService::class.java)
            }
        }

        val repositoryModule = module {
            viewModel { SearchViewModel(get() as InterfaceGeniusRepo) }
            single { GeniusRepo(get()) as InterfaceGeniusRepo }
        }

        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule, repositoryModule))
        }
    }

    companion object {
        var context: Context? = null
            private set
    }
}