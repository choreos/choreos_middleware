##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 6045a3a0-e6e4-4fb1-aed4-0042d2b9b935 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['6045a3a0-e6e4-4fb1-aed4-0042d2b9b935']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['6045a3a0-e6e4-4fb1-aed4-0042d2b9b935']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['6045a3a0-e6e4-4fb1-aed4-0042d2b9b935']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['6045a3a0-e6e4-4fb1-aed4-0042d2b9b935']['InstallationDir'] = ENV["HOME"]

