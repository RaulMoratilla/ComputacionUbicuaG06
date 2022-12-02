package logica;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log
{
	public static Logger log = LogManager.getLogger("log");
	public static Logger logbasedatos = LogManager.getLogger("logdb");
	public static Logger logmqtt = LogManager.getLogger("logmqtt");
}