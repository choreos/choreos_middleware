##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of a5f8ba3b-f643-4617-a60e-7413cfe7bab7 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['a5f8ba3b-f643-4617-a60e-7413cfe7bab7']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['a5f8ba3b-f643-4617-a60e-7413cfe7bab7']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['a5f8ba3b-f643-4617-a60e-7413cfe7bab7']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['a5f8ba3b-f643-4617-a60e-7413cfe7bab7']['InstallationDir'] = ENV["HOME"]

