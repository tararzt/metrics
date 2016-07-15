package com.rzt.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rzt.exception.ActionFailureException;

public class FileOperations {

	private static final Logger logger = LoggerFactory.getLogger(FileOperations.class);

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

			logger.info("Successfully written data to the file - " + fileName);
		}
		catch( IOException e )
		{
			logger.error(ErrorCode.COMMON_EXCEPTION, e);
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
					logger.error(ErrorCode.COMMON_EXCEPTION, e);
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
					logger.error(ErrorCode.COMMON_EXCEPTION, e);
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
				logger.info("Directory created!");
			}
			else
			{
				logger.error("Failed to create directories!");
				this.absolutePath = absolutePath;
				return;
			}
		}
		this.absolutePath = files.getAbsolutePath();
	}
}
