##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 5ac79038-56e1-41e1-9906-4b3847e1a098 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['5ac79038-56e1-41e1-9906-4b3847e1a098']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['5ac79038-56e1-41e1-9906-4b3847e1a098']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['5ac79038-56e1-41e1-9906-4b3847e1a098']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['5ac79038-56e1-41e1-9906-4b3847e1a098']['InstallationDir'] = ENV["HOME"]

