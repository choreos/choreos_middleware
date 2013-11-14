##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 3442aa2c-74a6-45a6-bfa2-61497415da5e must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['3442aa2c-74a6-45a6-bfa2-61497415da5e']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['3442aa2c-74a6-45a6-bfa2-61497415da5e']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['3442aa2c-74a6-45a6-bfa2-61497415da5e']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['3442aa2c-74a6-45a6-bfa2-61497415da5e']['InstallationDir'] = ENV["HOME"]

