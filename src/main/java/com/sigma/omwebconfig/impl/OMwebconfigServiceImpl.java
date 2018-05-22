package com.sigma.omwebconfig.impl;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sigma.omwebconfig.service.OMwebconfigServices;
import com.sigma.omwebconfig.util.Utility;
import com.sigma.omwebconfig.util.ZipUtil;

public class OMwebconfigServiceImpl implements OMwebconfigServices {
	private static final Logger logger = LoggerFactory.getLogger(OMwebconfigServiceImpl.class);	
	private final Utility utility = Utility.INSTANCE;
	private final ZipUtil zipUtil = ZipUtil.INSTANCE;

	@Override
	public String start(final String scriptName) {
		this.utility.initiateAPI(scriptName);
		return "started!";
	}

	@Override
	public String stop(final String scriptName) {
		this.utility.initiateAPI(scriptName);
		return "stopped!";
	}

	@Override
	public String cleanRestart(final String scriptName) {
		this.utility.initiateAPI(scriptName);
		return "cleanRestarted!";
	}

	@Override
	public String killProcess(final String scriptName) {
		this.utility.initiateAPI(scriptName);
		return "killed process!";
	}

	@Override
	public String getFreeMemory(final String scriptName) {
		return this.utility.getMemory(scriptName);
	}

	@Override
	public void downloadApplicationLog(final HttpSession session, final HttpServletResponse response) {
		this.utility.getApplicationLogFile(session, response);
	}

	@Override
	public void downloadNohubLog(final HttpSession session, final HttpServletResponse response) {
		this.utility.getNohupLogFile(session, response);
	}

	@Override
	public String zipLogs(HttpSession session, HttpServletResponse response) {
		try {
			this.zipUtil.createZipLogs(session, response);
		} catch (Exception e) {
			logger.error("exception caught..");
		}
		return "logs zipped!!";
	}

	@Override
	public String clearLogs(String path) {
		this.utility.deleteFileFolder(path);
		return "done";
	}
}
