package android.hardik.attendancemanager

import android.app.Application
import timber.log.Timber

class AttendanceManagerApplication() : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}