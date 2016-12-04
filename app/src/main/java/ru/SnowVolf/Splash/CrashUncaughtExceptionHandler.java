package ru.SnowVolf.Splash;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

class CrashUncaughtExceptionHandler implements
Thread.UncaughtExceptionHandler
{

    private final File filesDirectory;
    private final SimpleDateFormat formatter;
    private final Thread.UncaughtExceptionHandler previousHandler;

    private String packageName;
    private String versionName;
    private int versionCode;

    private CrashUncaughtExceptionHandler(Context context)
    {
        formatter = new SimpleDateFormat("dd.MM.yy HH:mm:ss", Locale.getDefault());
        PackageManager packageManager = context.getPackageManager();
        try
        {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            packageName = packageInfo.packageName;
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;
        }
        catch (NameNotFoundException ignored)
        {
            packageName = "ru.SnowVolf.Splash";
            versionName = "1.0.1";
            versionCode = 2;
        }
        previousHandler = Thread.getDefaultUncaughtExceptionHandler();
        filesDirectory = context.getExternalFilesDir(null);
    }

    static void setDefaultUncaughtExceptionHandler(Context context)
    {
        Thread.setDefaultUncaughtExceptionHandler(new CrashUncaughtExceptionHandler(context));
    }

    @Override
    public void uncaughtException(Thread thread, Throwable exception)
    {
        Date dumpDate = new Date(System.currentTimeMillis());
        StringBuilder reportBuilder = new StringBuilder();
        reportBuilder
			.append("Date").append(":\t").append(formatter.format(dumpDate)).append("\n")
			.append("PackageName").append(":\t").append(packageName).append("\n")
			.append("VersionName").append(":\t").append(versionName).append("(").append(String.valueOf(versionCode)).append(")\n")
			.append("ThreadName").append(":\t").append(thread.toString()).append("\n");
        processThrowable(exception, reportBuilder);
        if
        (
			filesDirectory != null &&
			(
			filesDirectory.exists() ||
			filesDirectory.mkdirs()
			)
			)
        {
            String pathCrashLog = filesDirectory.getPath() + File.separator + "Crash.log";
            File crashLog = new File(pathCrashLog);
            if
            (
				!crashLog.exists() ||
				crashLog.delete()
				)
            {
                FileWriter fileWriter = null;
                BufferedWriter bufferedWriter = null;
                try
                {
                    fileWriter = new FileWriter(crashLog, true);
                    bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.write(reportBuilder.toString());
                    bufferedWriter.flush();
                }
                catch (IOException ignored)
				{}
                finally
                {
                    try
                    {
                        if (bufferedWriter != null)
                        {
                            bufferedWriter.close();
                        }
                    }
                    catch (IOException ignored)
					{}
                    try
                    {
                        if (fileWriter != null)
                        {
                            fileWriter.close();
                        }
                    }
                    catch (IOException ignored)
					{}
                }
            }
            if (previousHandler != null)
            {
                previousHandler.uncaughtException(thread, exception);
            }
        }
    }

    private void processThrowable(Throwable exception, StringBuilder builder)
    {
        if (exception == null)
        {
            return;
        }
        StackTraceElement[] stackTraceElements = exception.getStackTrace();
        builder
			.append("Exception").append(":\t").append(exception.getClass().getName()).append("\n")
			.append("Message").append(":\t").append(exception.getMessage()).append("\n")
			.append("StackTrace").append(":\n");
        for (StackTraceElement element : stackTraceElements)
        {
            builder.append("\t").append(element.toString()).append("\n");
        }
        processThrowable(exception.getCause(), builder);
    }
}

