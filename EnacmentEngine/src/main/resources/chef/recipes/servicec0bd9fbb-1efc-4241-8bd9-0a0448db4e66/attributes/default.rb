##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of c0bd9fbb-1efc-4241-8bd9-0a0448db4e66 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['c0bd9fbb-1efc-4241-8bd9-0a0448db4e66']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['c0bd9fbb-1efc-4241-8bd9-0a0448db4e66']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['c0bd9fbb-1efc-4241-8bd9-0a0448db4e66']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['c0bd9fbb-1efc-4241-8bd9-0a0448db4e66']['InstallationDir'] = ENV["HOME"]

