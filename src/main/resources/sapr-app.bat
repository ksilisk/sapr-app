set LOG_DIR=logs
set LOG_PARAM=-Dlogs.dir=%LOG_DIR%
set CONF_DIR=conf

set APP_CLASSPATH=lib\*

call java %LOG_PARAM% -cp %APP_CLASSPATH% com.ksilisk.sapr.Main --config.path=%CONF_DIR%