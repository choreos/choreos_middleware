##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 37826ebc-dba3-45a8-a638-aa55e89d1340 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['37826ebc-dba3-45a8-a638-aa55e89d1340']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['37826ebc-dba3-45a8-a638-aa55e89d1340']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['37826ebc-dba3-45a8-a638-aa55e89d1340']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['37826ebc-dba3-45a8-a638-aa55e89d1340']['InstallationDir'] = ENV["HOME"]

