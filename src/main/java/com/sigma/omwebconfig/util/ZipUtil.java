package com.sigma.omwebconfig.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;

import com.sigma.omwebconfig.impl.OMwebconfigServiceImpl;
import com.sigma.omwebconfig.service.OMwebconfigServices;

public enum ZipUtil {
	INSTANCE;
	private static final String Logs_Path = "/home/tenzindawa/dansberg/om-web-3.1.2/logs";

	public void createZipsAndDeleteLogs() {
		final Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				System.out.println("5 seconds passed");
				try {
					final String newZip = Logs_Path + LocalDate.now().getDayOfWeek().name() + ".zip";
					zipFolder(Logs_Path, newZip);
					System.out.println("zip created successfully...");
					File directory = new File(Logs_Path);
					delete(directory);
					createFolder(Logs_Path);
					System.out.println("folder logs created successfully....");
					OMwebconfigServices omServices = (OMwebconfigServices) new OMwebconfigServiceImpl();
					omServices.cleanRestart("cleanRestart.sh");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 0, 3 * 24 * 60 * 60 * 1000);
	}

	private void createFolder(String dir) {
		File file = new File(dir);
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
	}

	private void delete(File file) throws IOException {
		if (file.isDirectory()) {
			if (file.list().length == 0) {
				file.delete();
				System.out.println("Directory is deleted : " + file.getAbsolutePath());
			} else {
				String files[] = file.list();
				for (String temp : files) {
					File fileDelete = new File(file, temp);
					delete(fileDelete);
				}
				if (file.list().length == 0) {
					file.delete();
					System.out.println("Directory is deleted : " + file.getAbsolutePath());
				}
			}
		} else {
			file.delete();
			System.out.println("File is deleted : " + file.getAbsolutePath());
		}
	}

	private void zipFolder(String srcFolder, String destZipFile) throws Exception {
		ZipOutputStream zip = null;
		FileOutputStream fileWriter = null;
		fileWriter = new FileOutputStream(destZipFile);
		zip = new ZipOutputStream(fileWriter);
		addFolderToZip("", srcFolder, zip);
		zip.flush();
		zip.close();
	}

	private void addFileToZip(String path, String srcFile, ZipOutputStream zip) throws Exception {
		File folder = new File(srcFile);
		if (folder.isDirectory()) {
			addFolderToZip(path, srcFile, zip);
		} else {
			byte[] buf = new byte[1024];
			int len;
			FileInputStream in = new FileInputStream(srcFile);
			zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
			while ((len = in.read(buf)) > 0) {
				zip.write(buf, 0, len);
			}
		}
	}
	private void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws Exception {
		File folder = new File(srcFolder);
		for (String fileName : folder.list()) {
			if (path.equals("")) {
				addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
			} else {
				addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
			}
		}
	}
	public void createZipLogs(final HttpSession session, final HttpServletResponse response) throws Exception {
		final String newZipPath = Logs_Path + ".zip";
		zipFolder(Logs_Path, newZipPath);
		try {
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment;filename=logs.zip");
			ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
			// create a list to add files to be zipped
			ArrayList<File> files = new ArrayList<>(2);
			files.add(new File(newZipPath));
			for (File file : files) {
				zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
				FileInputStream fileInputStream = new FileInputStream(file);
				IOUtils.copy(fileInputStream, zipOutputStream);
				fileInputStream.close();
				zipOutputStream.closeEntry();
			}
			zipOutputStream.close();
		} catch (Exception e) {
			System.out.println("Request could not be completed at this moment. Please try again.");
			e.printStackTrace();
		}
	}
}
