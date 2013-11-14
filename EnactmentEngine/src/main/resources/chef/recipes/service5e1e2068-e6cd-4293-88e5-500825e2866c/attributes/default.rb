##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 5e1e2068-e6cd-4293-88e5-500825e2866c must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['5e1e2068-e6cd-4293-88e5-500825e2866c']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['5e1e2068-e6cd-4293-88e5-500825e2866c']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['5e1e2068-e6cd-4293-88e5-500825e2866c']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['5e1e2068-e6cd-4293-88e5-500825e2866c']['InstallationDir'] = ENV["HOME"]

