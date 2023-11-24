#!/bin/bash

LOG_DIR="logs"
LOG_PARAM="-Dlogs.dir='${LOG_DIR}'"

APP_CLASSPATH="lib/*"

RUN_COMMAND="java '${LOG_PARAM}' -cp '${APP_CLASSPATH}' com.ksilisk.sapr.Main"

eval ${RUN_COMMAND}
