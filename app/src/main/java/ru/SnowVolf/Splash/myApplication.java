package ru.SnowVolf.Splash;

import android.app.Application;

public class myApplication extends Application
{

    @Override
    public void onCreate()
    {
        super.onCreate();
        CrashUncaughtExceptionHandler.setDefaultUncaughtExceptionHandler(this); // Обязательно
    }
}

