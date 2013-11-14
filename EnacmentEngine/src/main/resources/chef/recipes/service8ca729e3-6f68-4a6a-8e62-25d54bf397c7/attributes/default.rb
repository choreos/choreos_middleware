##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 8ca729e3-6f68-4a6a-8e62-25d54bf397c7 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['8ca729e3-6f68-4a6a-8e62-25d54bf397c7']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['8ca729e3-6f68-4a6a-8e62-25d54bf397c7']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['8ca729e3-6f68-4a6a-8e62-25d54bf397c7']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['8ca729e3-6f68-4a6a-8e62-25d54bf397c7']['InstallationDir'] = ENV["HOME"]

