##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 6badacba-e1ed-4449-a81e-0786572315d7 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['6badacba-e1ed-4449-a81e-0786572315d7']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['6badacba-e1ed-4449-a81e-0786572315d7']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['6badacba-e1ed-4449-a81e-0786572315d7']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['6badacba-e1ed-4449-a81e-0786572315d7']['InstallationDir'] = ENV["HOME"]

