##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of cc7a922c-71b5-4599-8d50-4412de314614 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['cc7a922c-71b5-4599-8d50-4412de314614']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['cc7a922c-71b5-4599-8d50-4412de314614']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['cc7a922c-71b5-4599-8d50-4412de314614']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['cc7a922c-71b5-4599-8d50-4412de314614']['InstallationDir'] = ENV["HOME"]

