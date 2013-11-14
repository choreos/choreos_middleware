##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of ca0c7031-9cc4-41e6-b95e-22277be565ff must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['ca0c7031-9cc4-41e6-b95e-22277be565ff']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['ca0c7031-9cc4-41e6-b95e-22277be565ff']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['ca0c7031-9cc4-41e6-b95e-22277be565ff']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['ca0c7031-9cc4-41e6-b95e-22277be565ff']['InstallationDir'] = ENV["HOME"]

