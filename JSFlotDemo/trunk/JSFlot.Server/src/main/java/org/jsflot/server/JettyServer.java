package org.jsflot.server;

import java.util.logging.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.xml.XmlConfiguration;

public class JettyServer {
	private static final Logger log = Logger.getLogger(JettyServer.class.getName());
	private static Server jettyServer;
	private static int localPort;

	public static void main(String[] args) throws Exception {
		start();
	}

	private static void start() throws Exception {
		if (jettyServer != null && jettyServer.isRunning()) {
			log.warning("JettyServer.start() ble kalt, men serveren er allerede startet.");
			return;
		}
		configure();
		jettyServer.start();
		localPort = jettyServer.getConnectors()[0].getLocalPort();
		log.info("JettyServer startet paa http://localhost:" + localPort + "/");
	}

	private static void configure() throws Exception {
		Properties properties = new Properties();
		File configFile = new File("config.properties.dev");
		if (!configFile.exists()) {
			configFile = new File("config.properties");
		}
		if (!configFile.exists()) {
			configFile = new File("../config.properties");
		}
		if (!configFile.exists()) {
			configFile = new File("../../config.properties.dev");
		}
		if (!configFile.exists()) {
			configFile = new File("../../config.properties");
		}
		if (configFile.exists()) {
			FileInputStream configStream = new FileInputStream(configFile);
			properties.load(configStream);
			configStream.close();
			log.info("Server properties fra " + configFile.getAbsolutePath());
			for (Enumeration<Object> e = properties.keys(); e.hasMoreElements();) {
				Object property = (String) e.nextElement();
				log.info("\t\t* " + property + "=" + properties.get(property));
			}
		} else {
			String melding = "Fant ikke " + configFile.getAbsolutePath() + ". Kan ikke starte.";
			log.severe(melding);
			throw new RuntimeException(melding);
		}

		setProperties(properties);
		jettyServer = new Server(Integer.parseInt(System.getProperty("jetty.port", "8080")));

		File jettyConfigFile = new File("jetty.xml");
		if (!jettyConfigFile.exists()) {
			jettyConfigFile = new File("../jetty.xml");
		}
		if (!jettyConfigFile.exists()) {
			jettyConfigFile = new File("../../jetty.xml");
		}
		if (jettyConfigFile.exists()) {
			log.info("konfigurerer Jetty med jetty.xml: " + jettyConfigFile.getAbsolutePath());
			XmlConfiguration configuration = new XmlConfiguration(jettyConfigFile.toURL());
			configuration.configure(jettyServer);
		} else {
			String melding = "Fant ikke " + jettyConfigFile.getAbsolutePath() + ". Kan ikke starte.";
			log.severe(melding);
			throw new RuntimeException(melding);
		}

		setWebAppContext();
	}

	private static void setWebAppContext() {
		File repoDir = new File(System.getProperty("basedir", "target/appassembler/repo"));
		String artifactId = null;
		try {
			artifactId = System.getProperty("context.root", repoDir.getParentFile().getParentFile().getName());
		} catch (Exception e) {
			String melding = "artifactId (context.root) er : " + artifactId + ".'basedir' må ha minst to nivåer (f. eks. /stiTil/basedir). Kan ikke starte.";
			log.severe(melding);
			throw new RuntimeException(melding);
		}
		String inplace = System.getProperty("inplace.trueFalse", "false");
		if (inplace.equalsIgnoreCase("true")) {
			jettyServer.addHandler(new WebAppContext(repoDir.getAbsolutePath(), "/"+ artifactId));
		} else {
			if (repoDir.canRead()) {
				Collection<File> warFiles = FileUtils.listFiles(repoDir, new String[] { "war" }, true);
				new WebAppContext();
				if (!warFiles.isEmpty()) {
					for (File warFile : warFiles) {
						jettyServer.addHandler(new WebAppContext(warFile.getAbsolutePath(), "/" + artifactId));
					}
				} else {
					String melding = "Fant ingen webapplikasjoner (.war) i: " + repoDir.getAbsolutePath() + ". Kan ikke starte.";
					log.severe(melding);
					throw new RuntimeException(melding);
				}
			} else {
				String melding = "Kan ikke lese: " + repoDir.getAbsolutePath() + ". Kan ikke starte.";
				log.severe(melding);
				throw new RuntimeException(melding);
			}
		}
	}

	private static void setProperties(Properties properties) {
		Enumeration<Object> propEnum = properties.keys();
		while (propEnum.hasMoreElements()) {
			String property = (String) propEnum.nextElement();
			System.setProperty(property, properties.getProperty(property));
		}
	}

	public static void stop() throws Exception {
		if (jettyServer != null) {
			try {
				jettyServer.stop();
				log.info("JettyServer stoppet paa http://localhost:" + localPort + "/");
			} catch (InterruptedException e) {
				log.severe("Klarte ikke stoppe Jetty server.");
				throw new RuntimeException(e);
			}
		}
	}

	public static Server getJettyServer() {
		return jettyServer;
	}
}
