##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of db8cd9fd-a6f0-4d3f-8f7b-752b797977df must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['db8cd9fd-a6f0-4d3f-8f7b-752b797977df']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['db8cd9fd-a6f0-4d3f-8f7b-752b797977df']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['db8cd9fd-a6f0-4d3f-8f7b-752b797977df']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['db8cd9fd-a6f0-4d3f-8f7b-752b797977df']['InstallationDir'] = ENV["HOME"]

