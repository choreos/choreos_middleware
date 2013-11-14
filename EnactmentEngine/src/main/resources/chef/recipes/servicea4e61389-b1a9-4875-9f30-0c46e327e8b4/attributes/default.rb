##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of a4e61389-b1a9-4875-9f30-0c46e327e8b4 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['a4e61389-b1a9-4875-9f30-0c46e327e8b4']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['a4e61389-b1a9-4875-9f30-0c46e327e8b4']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['a4e61389-b1a9-4875-9f30-0c46e327e8b4']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['a4e61389-b1a9-4875-9f30-0c46e327e8b4']['InstallationDir'] = ENV["HOME"]

