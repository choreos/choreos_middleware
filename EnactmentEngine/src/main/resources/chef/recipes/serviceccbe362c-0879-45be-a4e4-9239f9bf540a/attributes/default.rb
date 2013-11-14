##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of ccbe362c-0879-45be-a4e4-9239f9bf540a must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['ccbe362c-0879-45be-a4e4-9239f9bf540a']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['ccbe362c-0879-45be-a4e4-9239f9bf540a']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['ccbe362c-0879-45be-a4e4-9239f9bf540a']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['ccbe362c-0879-45be-a4e4-9239f9bf540a']['InstallationDir'] = ENV["HOME"]

