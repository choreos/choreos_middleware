##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 44727d6b-cc95-4fb6-b347-22fcb40d1ba9 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['44727d6b-cc95-4fb6-b347-22fcb40d1ba9']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['44727d6b-cc95-4fb6-b347-22fcb40d1ba9']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['44727d6b-cc95-4fb6-b347-22fcb40d1ba9']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['44727d6b-cc95-4fb6-b347-22fcb40d1ba9']['InstallationDir'] = ENV["HOME"]

