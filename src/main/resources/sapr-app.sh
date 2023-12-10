#!/bin/bash

LOG_DIR="logs"
LOG_PARAM="-Dlogs.dir='${LOG_DIR}'"
CONF_DIR="conf"

APP_CLASSPATH="lib/*"

RUN_COMMAND="java '${LOG_PARAM}' -cp '${APP_CLASSPATH}' com.ksilisk.sapr.Main --config.path='${CONF_DIR}'"

eval "${RUN_COMMAND}"
