package com.frozy.autil.debug;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class LogWriter
{
	private final static SimpleDateFormat DataFormat = new SimpleDateFormat("MM-dd HH:mm:ss.ms");

	private static String LogFile;

	private static LogWriter Instance;

	private Context mContext;

	private LogWriter(Context context)
	{
		this.mContext = context;
		this.init();
	}

	public synchronized static LogWriter getInstance(Context context)
	{
		if (Instance == null)
			Instance = new LogWriter(context);
		return Instance;
	}

	private void init()
	{
		String logFolder = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/"
				+ mContext.getPackageName();
		File path = new File(logFolder);
		if (!path.exists())
			path.mkdirs();

		LogFile = logFolder + "/logcat.log";
		File file = new File(LogFile);

		/*
		 * If the size of log file is too big, remove this file and create a new
		 * one.
		 */
		if (file.length() > 1024 << 10)
		{
			file.delete();
			try
			{
				file.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public String getLogFile()
	{
		return LogFile;
	}

	public void i(String tag, String log)
	{
		Log.i(tag, log);

		writeLog2File("I", tag, log);
	}

	public void e(String tag, String log)
	{
		Log.e(tag, log);

		writeLog2File("E", tag, log);
	}

	/**
	 * Format the log as same as that in logcat view, so that I can copy it to
	 * <a href="http://alanwm88.appspot.com/"><b>Log Page creator</b></a> to
	 * view the log conveniently
	 * <p>
	 * Thanks for AlanJavar, who is the author of this site.
	 * </p>
	 * 
	 * @param level
	 *            The level of the log, such as "I", "D", "E" and so on.
	 * @param tag
	 *            The tag of the log
	 * @param log
	 *            The log message
	 * @return Formatted Log Text
	 */
	private static String getFormatLog(String level, String tag, String log)
	{
		StringBuilder format_log = new StringBuilder();
		format_log.append(DataFormat.format(new Date())).append(": ").append(level).append("/").append(tag).append("(")
				.append(android.os.Process.myPid()).append(")").append(": ").append(log).append("\n");
		System.out.println(format_log.toString());
		return format_log.toString();
	}

	private static void writeLog2File(String level, String tag, String log)
	{
		File file = new File(LogFile);
		FileWriter fw = null;
		try
		{
			fw = new FileWriter(file, true);
			fw.append(getFormatLog(level, tag, log));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (null != fw)
			{
				try
				{
					fw.flush();
					fw.close();
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		}
	}
}
