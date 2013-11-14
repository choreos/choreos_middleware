##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of c1997e4e-1c06-4fe3-9617-b7f514383f04 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['c1997e4e-1c06-4fe3-9617-b7f514383f04']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['c1997e4e-1c06-4fe3-9617-b7f514383f04']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['c1997e4e-1c06-4fe3-9617-b7f514383f04']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['c1997e4e-1c06-4fe3-9617-b7f514383f04']['InstallationDir'] = ENV["HOME"]

