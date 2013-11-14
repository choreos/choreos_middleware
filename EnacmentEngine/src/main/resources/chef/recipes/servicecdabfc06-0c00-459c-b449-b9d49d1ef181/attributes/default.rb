##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of cdabfc06-0c00-459c-b449-b9d49d1ef181 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['cdabfc06-0c00-459c-b449-b9d49d1ef181']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['cdabfc06-0c00-459c-b449-b9d49d1ef181']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['cdabfc06-0c00-459c-b449-b9d49d1ef181']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['cdabfc06-0c00-459c-b449-b9d49d1ef181']['InstallationDir'] = ENV["HOME"]

