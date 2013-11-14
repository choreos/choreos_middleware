##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of ff641649-5bd4-4a9d-ab6e-60a344c1ffa9 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['ff641649-5bd4-4a9d-ab6e-60a344c1ffa9']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['ff641649-5bd4-4a9d-ab6e-60a344c1ffa9']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['ff641649-5bd4-4a9d-ab6e-60a344c1ffa9']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['ff641649-5bd4-4a9d-ab6e-60a344c1ffa9']['InstallationDir'] = ENV["HOME"]

