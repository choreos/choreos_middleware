##########################################################################
#									 #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#									 #
# All ocurrences of $NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#									 #
##########################################################################


# Name of the deployed service 
default['service']['$NAME']['WarFile']	    = "$WARFILE"
default['service']['$NAME']['URL']          = "$URL"

# Set the default path to tomcat's webapps folder  
default['service']['$NAME']['webappsPath']  = "/var/lib/tomcat6/webapps"

# Set the default log filewar_depl
default['service']['$NAME']['logFile']      = "/dev/stdout"

# Set the default log file
default['service']['$NAME']['bashscript']   = "war_deploy_script_service_$NAME.sh"

