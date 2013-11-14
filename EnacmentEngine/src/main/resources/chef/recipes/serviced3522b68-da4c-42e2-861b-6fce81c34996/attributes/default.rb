##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of d3522b68-da4c-42e2-861b-6fce81c34996 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['d3522b68-da4c-42e2-861b-6fce81c34996']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['d3522b68-da4c-42e2-861b-6fce81c34996']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['d3522b68-da4c-42e2-861b-6fce81c34996']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['d3522b68-da4c-42e2-861b-6fce81c34996']['InstallationDir'] = ENV["HOME"]

