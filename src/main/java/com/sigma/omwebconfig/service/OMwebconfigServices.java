package com.sigma.omwebconfig.service;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface OMwebconfigServices {
	/**
	 * 
	 * @param scriptName
	 * @return
	 */
	String start(final String scriptName);

	/**
	 * 
	 * @param scriptName
	 * @return
	 */
	String stop(final String scriptName);

	/**
	 * 
	 * @param scriptName
	 * @return
	 */
	String cleanRestart(final String scriptName);

	/**
	 * 
	 * @param scriptName
	 * @return
	 */
	String killProcess(final String scriptName);

	/**
	 * 
	 * @param scriptName
	 * @return
	 */
	String getFreeMemory(final String scriptName);

	/**
	 * 
	 * @param scriptName
	 * @return
	 */
	String clearLogs(final String scriptName);

	/**
	 * 
	 * @param session
	 * @param response
	 */
	void downloadApplicationLog(final HttpSession session, final HttpServletResponse response);

	/**
	 * 
	 * @param session
	 * @param response
	 */
	void downloadNohubLog(final HttpSession session, final HttpServletResponse response);

	/**
	 * 
	 * @param session
	 * @param response
	 * @return
	 */
	String zipLogs(final HttpSession session, final HttpServletResponse response);
}
