package com.sigma.omwebconfig.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sigma.omwebconfig.impl.OMwebconfigServiceImpl;
import com.sigma.omwebconfig.service.OMwebconfigServices;
import com.sigma.omwebconfig.util.ControllerConstant;

@RestController
@SpringBootApplication
@RequestMapping({ "/controller" })
public class OMController {
	private static final Logger logger = LoggerFactory.getLogger(OMController.class);
	private final OMwebconfigServices services;
	public OMController() {
		this.services = (OMwebconfigServices) new OMwebconfigServiceImpl();
	}

	@RequestMapping(value = { "/start" }, method = { RequestMethod.POST }, produces = { "application/text" })
	public String start() {
		return this.services.start("startOM.sh");
	}

	@RequestMapping(value = { "/stop" }, method = { RequestMethod.POST }, produces = { "application/text" })
	public String stop() {
		return this.services.stop("stopOM.sh");
	}

	@RequestMapping(value = { "/cleanRestart" }, method = { RequestMethod.POST }, produces = { "application/text" })
	public String cleanRestart() {
		return this.services.cleanRestart("cleanRestart.sh");
	}

	@RequestMapping(value = { "/freeMemory" }, method = { RequestMethod.GET }, produces = { "application/text" })
	public String getFreeMemory() {
		return this.services.getFreeMemory("getMemory.sh");
	}

	@RequestMapping(value = { "/applicationLog" }, method = { RequestMethod.GET }, produces = { "application/text" })
	public void getApplicationLog(final HttpSession session, final HttpServletResponse response) {
		this.services.downloadApplicationLog(session, response);
	}

	@RequestMapping(value = { "/nohupLog" }, method = { RequestMethod.GET }, produces = { "application/text" })
	public void getNohupLog(final HttpSession session, final HttpServletResponse response) {
		this.services.downloadNohubLog(session, response);
	}

	@RequestMapping(value = { "/zipLogs" }, method = { RequestMethod.GET }, produces = { "application/zip" })
	public void getZipCleanLogs(final HttpSession session, final HttpServletResponse response) {
		this.services.zipLogs(session, response);
	}

	@RequestMapping(value = { "/clearLogs" }, method = { RequestMethod.POST }, produces = { "application/text" })
	public void getStartClearLogs() {
		this.services.clearLogs(ControllerConstant.DIR_PATH);
	}
	

}
