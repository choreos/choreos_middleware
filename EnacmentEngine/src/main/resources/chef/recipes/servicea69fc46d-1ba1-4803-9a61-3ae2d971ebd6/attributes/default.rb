##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of a69fc46d-1ba1-4803-9a61-3ae2d971ebd6 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['a69fc46d-1ba1-4803-9a61-3ae2d971ebd6']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['a69fc46d-1ba1-4803-9a61-3ae2d971ebd6']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['a69fc46d-1ba1-4803-9a61-3ae2d971ebd6']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['a69fc46d-1ba1-4803-9a61-3ae2d971ebd6']['InstallationDir'] = ENV["HOME"]

