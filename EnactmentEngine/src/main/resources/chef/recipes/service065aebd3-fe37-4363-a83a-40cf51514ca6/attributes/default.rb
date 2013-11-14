##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 065aebd3-fe37-4363-a83a-40cf51514ca6 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['065aebd3-fe37-4363-a83a-40cf51514ca6']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['065aebd3-fe37-4363-a83a-40cf51514ca6']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['065aebd3-fe37-4363-a83a-40cf51514ca6']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['065aebd3-fe37-4363-a83a-40cf51514ca6']['InstallationDir'] = ENV["HOME"]

