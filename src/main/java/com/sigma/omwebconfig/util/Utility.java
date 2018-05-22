package com.sigma.omwebconfig.util;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Properties;

public enum Utility {
	INSTANCE;
	private static final Logger logger = LoggerFactory.getLogger(Utility.class);
	public void initiateAPI(final String scriptName) {
		try {
			String path=readPropertiesFile("path");
			final String target = new String(path + scriptName);
			final Runtime runtime = Runtime.getRuntime();
			final Process proc = runtime.exec(target);
		} catch (Exception exception) {
			logger.error("error occurred..");
		}
	}
	public String getMemory(final String scriptName) {
		StringBuffer output = null;
		try {
			String path=readPropertiesFile("path");
			final String target = new String(path + scriptName);
			final Runtime runtime = Runtime.getRuntime();
			final Process proc = runtime.exec(target);
			proc.waitFor();
			output = new StringBuffer();
			final BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
		} catch (Exception exception) {
			logger.error("error occurred.." + exception.getMessage());
		}
		return output.toString();
	}

	public String getLogs(final String fileName) {
		String strLine = "";
		try {
			final FileInputStream fstream = new FileInputStream(fileName);
			final BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			final StringBuffer stringBuffer = new StringBuffer();
			try {
				while (br.ready()) {
					stringBuffer.append(br.readLine());
				}
			} catch (IOException e) {
			}
			strLine = stringBuffer.toString();
			System.out.println(strLine.toString());
			fstream.close();
		} catch (Exception exception) {
			logger.error("error occurred.." + exception.getMessage());
		}
		return strLine;
	}

	public String getMemory() {
		final double number = Runtime.getRuntime().totalMemory() / 1.073741824E9;
		return String.valueOf(number);
	}

	public void getNohupLogFile(final HttpSession session, final HttpServletResponse response) {
		try {
			final String filePathToBeServed = readPropertiesFile("noHubLogPath");
			final File fileToDownload = new File(filePathToBeServed);
			final InputStream inputStream = new FileInputStream(fileToDownload);
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment;filename=nohup.txt");
			IOUtils.copy(inputStream, (OutputStream) response.getOutputStream());
			response.flushBuffer();
			inputStream.close();
		} catch (Exception exception) {
			logger.error("error occurred.." + exception.getMessage());
		}
	}

	public void getApplicationLogFile(final HttpSession session, final HttpServletResponse response) {
		try {
			final String filePathToBeServed = readPropertiesFile("applicationLogPath");
			final File fileToDownload = new File(filePathToBeServed);
			final InputStream inputStream = new FileInputStream(fileToDownload);
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment;filename=application.txt");
			IOUtils.copy(inputStream, (OutputStream) response.getOutputStream());
			response.flushBuffer();
			inputStream.close();
		} catch (Exception exception) {
			logger.error("error occurred.." + exception.getMessage());
		}
	}

	public void deleteFileFolder(String path) {
		File file = new File(path);
		if (file.exists()) {
			do {
				delete(file);
			} while (file.exists());
			new File(path).mkdir();
		} else {
			logger.info("file not found..");
		}
	}

	private void delete(File file) {
		if (file.isDirectory()) {
			String fileList[] = file.list();
			if (fileList.length == 0) {
				logger.info("Deleting Directory : {}", file.getPath());
				file.delete();
			} else {
				int size = fileList.length;
				for (int i = 0; i < size; i++) {
					String fileName = fileList[i];
					String fullPath = file.getPath() + "/" + fileName;
					File fileOrFolder = new File(fullPath);
					delete(fileOrFolder);
				}
			}
		} else {
			file.delete();
		}
	}
	private String readPropertiesFile(String pathName) throws IOException {
		FileReader reader = new FileReader("config.properties");
		Properties p = new Properties();
		p.load(reader);
		return p.getProperty(pathName).toString();
	}
}
