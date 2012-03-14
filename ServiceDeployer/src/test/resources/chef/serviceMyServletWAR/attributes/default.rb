##########################################################################
#									 #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#									 #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#									 #
##########################################################################


# Name of the deployed service 
default['service']['MyServletWAR']['WarFile']		= "myServletWAR.war"
default['service']['MyServletWAR']['URL']		= "http://content.hccfl.edu/pollock/AJava/WAR/myServletWAR.war"

# Set the default log file
default['service']['MyServletWAR']['logFile']		= "/dev/null"

