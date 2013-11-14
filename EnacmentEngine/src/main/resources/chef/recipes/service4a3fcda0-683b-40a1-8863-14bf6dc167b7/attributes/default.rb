##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 4a3fcda0-683b-40a1-8863-14bf6dc167b7 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['4a3fcda0-683b-40a1-8863-14bf6dc167b7']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['4a3fcda0-683b-40a1-8863-14bf6dc167b7']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['4a3fcda0-683b-40a1-8863-14bf6dc167b7']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['4a3fcda0-683b-40a1-8863-14bf6dc167b7']['InstallationDir'] = ENV["HOME"]

