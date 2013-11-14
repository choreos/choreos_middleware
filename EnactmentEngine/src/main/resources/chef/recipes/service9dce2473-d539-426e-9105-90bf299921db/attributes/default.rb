##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 9dce2473-d539-426e-9105-90bf299921db must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['9dce2473-d539-426e-9105-90bf299921db']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['9dce2473-d539-426e-9105-90bf299921db']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['9dce2473-d539-426e-9105-90bf299921db']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['9dce2473-d539-426e-9105-90bf299921db']['InstallationDir'] = ENV["HOME"]

