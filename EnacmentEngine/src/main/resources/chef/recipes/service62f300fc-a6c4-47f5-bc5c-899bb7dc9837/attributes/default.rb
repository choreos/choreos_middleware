##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 62f300fc-a6c4-47f5-bc5c-899bb7dc9837 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['62f300fc-a6c4-47f5-bc5c-899bb7dc9837']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['62f300fc-a6c4-47f5-bc5c-899bb7dc9837']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['62f300fc-a6c4-47f5-bc5c-899bb7dc9837']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['62f300fc-a6c4-47f5-bc5c-899bb7dc9837']['InstallationDir'] = ENV["HOME"]

