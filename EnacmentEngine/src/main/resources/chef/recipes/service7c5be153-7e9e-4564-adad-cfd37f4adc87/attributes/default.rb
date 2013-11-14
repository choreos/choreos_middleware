##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 7c5be153-7e9e-4564-adad-cfd37f4adc87 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['7c5be153-7e9e-4564-adad-cfd37f4adc87']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['7c5be153-7e9e-4564-adad-cfd37f4adc87']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['7c5be153-7e9e-4564-adad-cfd37f4adc87']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['7c5be153-7e9e-4564-adad-cfd37f4adc87']['InstallationDir'] = ENV["HOME"]

