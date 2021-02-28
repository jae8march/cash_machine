package com.company.app.util.listener;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {
	private static final Logger LOG = Logger.getLogger(SessionListener.class);

	public void sessionCreated(HttpSessionEvent sessionEvent) {
		HttpSession session = sessionEvent.getSession();
		LOG.info("Session with id = " + session.getId() + " started.");
		session.setMaxInactiveInterval(600);
	}

	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		HttpSession session = sessionEvent.getSession();
		LOG.info("Session with id = " + session.getId() + " ended.");
	}
}
