package com.chingari.chingariweatherdemo

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.demo.sncr_demo.core.worker.GetWeatherDataPeriodicWorker
import com.demo.sncr_demo.domain.model.WeatherResponse
import org.koin.core.KoinComponent
import java.util.concurrent.TimeUnit
import androidx.lifecycle.Observer
import com.demo.sncr_demo.datasource.local.GetWeatherLocalRepository
import java.util.UUID

class MainViewModel() : ViewModel(), KoinComponent {
    // TODO: Implement the ViewModel

    var PERIODIC_WORKER_TAG = "PeriodicWorkRequest"
    var ONETIME_WORKER_TAG = "OneTimeWorkRequest"

    val _currentWeather: MutableLiveData<WeatherResponse> by lazy {
        MutableLiveData<WeatherResponse>()
    }


    lateinit var id: UUID

    fun getCurrentWeather(ctx: LifecycleOwner) {

        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val mWorkManager = WorkManager.getInstance(ctx as Context)

        GetWeatherLocalRepository.getWeatherResponse ?: run {
            val oneTimework = OneTimeWorkRequestBuilder<GetWeatherDataPeriodicWorker>().build()
            mWorkManager.enqueueUniqueWork(ONETIME_WORKER_TAG, ExistingWorkPolicy.KEEP, oneTimework)
            mWorkManager.getWorkInfoByIdLiveData(oneTimework.id)
                .observe(ctx, object : Observer<WorkInfo?> {
                    override fun onChanged(workInfo: WorkInfo?) {
                        updateUI(workInfo)

                    }
                })
        }

        val twoHourPeriodicWork = PeriodicWorkRequestBuilder<GetWeatherDataPeriodicWorker>(
            2,
            TimeUnit.HOURS,
            10,
            TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()
        mWorkManager.enqueueUniquePeriodicWork(
            PERIODIC_WORKER_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            twoHourPeriodicWork
        )
        mWorkManager.getWorkInfoByIdLiveData(twoHourPeriodicWork.id)
            .observe(ctx, object : Observer<WorkInfo?> {
                override fun onChanged(workInfo: WorkInfo?) {
                    updateUI(workInfo)

                }
            })


    }

    private fun updateUI(workInfo: WorkInfo?) {
        when {
            workInfo!!.state == WorkInfo.State.RUNNING -> {

            }
            workInfo.state == WorkInfo.State.ENQUEUED -> {

            }
            workInfo.state.isFinished ||
                    workInfo.state == WorkInfo.State.SUCCEEDED -> {

                _currentWeather.postValue(GetWeatherLocalRepository.getWeatherResponse)
            }
            else -> {
                Log.d("", "Something went wrong")
            }
        }

    }

}

