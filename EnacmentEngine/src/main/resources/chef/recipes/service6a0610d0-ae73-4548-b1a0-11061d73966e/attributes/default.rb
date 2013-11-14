##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 6a0610d0-ae73-4548-b1a0-11061d73966e must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['6a0610d0-ae73-4548-b1a0-11061d73966e']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['6a0610d0-ae73-4548-b1a0-11061d73966e']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['6a0610d0-ae73-4548-b1a0-11061d73966e']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['6a0610d0-ae73-4548-b1a0-11061d73966e']['InstallationDir'] = ENV["HOME"]

