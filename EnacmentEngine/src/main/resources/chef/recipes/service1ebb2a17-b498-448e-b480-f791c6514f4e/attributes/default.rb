##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 1ebb2a17-b498-448e-b480-f791c6514f4e must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['1ebb2a17-b498-448e-b480-f791c6514f4e']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['1ebb2a17-b498-448e-b480-f791c6514f4e']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['1ebb2a17-b498-448e-b480-f791c6514f4e']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['1ebb2a17-b498-448e-b480-f791c6514f4e']['InstallationDir'] = ENV["HOME"]

