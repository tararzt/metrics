package com.rzt.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.rzt.exception.ActionFailureException;

public class FileOperations {

	private static final Logger LOGGER = LogManager.getLogger(FileOperations.class);

	private String absolutePath;

	public void writeToFile( String content, String fileName )
	{
		FileWriterWithEncoding fw = null;
		BufferedWriter bw = null;
		try
		{
			File file = new File(this.absolutePath + "/" + fileName + ".json");

			boolean isFileCreated = false;
			if( !file.exists() )
			{
				isFileCreated = file.createNewFile();
			}

			if( isFileCreated )
			{
				fw = new FileWriterWithEncoding(file.getAbsoluteFile(), Charset.forName("UTF-8"));
				bw = new BufferedWriter(fw);
				bw.write(content);
			}
			else
			{

				String data = FileUtils.readFileToString(file.getAbsoluteFile());
				data = data.replace(data.charAt(data.length() - 1), ',');
				data = data + content.substring(1, content.length());

				fw = new FileWriterWithEncoding(file.getAbsoluteFile(), Charset.forName("UTF-8"));
				bw = new BufferedWriter(fw);
				bw.write(data);
			}

			LOGGER.info("Successfully written data to the file - " + fileName);
		}
		catch( IOException e )
		{
			LOGGER.error("Error in writing to a file", e);
			throw new ActionFailureException(e);
		}
		finally
		{
			if( bw != null )
			{
				try
				{
					bw.close();
				}
				catch( IOException e )
				{
					LOGGER.error("Error in closing connections", e);
					throw new ActionFailureException(e);
				}
			}
			if( fw != null )
			{
				try
				{
					fw.close();
				}
				catch( IOException e )
				{
					LOGGER.error("Error in closing conections", e);
					throw new ActionFailureException(e);
				}
			}
		}
	}

	public void createDirectory( String absolutePath )
	{

		File files = new File(absolutePath);
		if( !files.exists() )
		{
			if( files.mkdirs() )
			{
				LOGGER.info("Directory created!");
			}
			else
			{
				LOGGER.error("Failed to create directories!");
				this.absolutePath = absolutePath;
				return;
			}
		}
		this.absolutePath = files.getAbsolutePath();
	}
}
