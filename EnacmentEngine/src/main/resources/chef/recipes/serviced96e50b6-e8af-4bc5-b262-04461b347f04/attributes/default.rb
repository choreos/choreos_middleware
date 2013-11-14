##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of d96e50b6-e8af-4bc5-b262-04461b347f04 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['d96e50b6-e8af-4bc5-b262-04461b347f04']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['d96e50b6-e8af-4bc5-b262-04461b347f04']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['d96e50b6-e8af-4bc5-b262-04461b347f04']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['d96e50b6-e8af-4bc5-b262-04461b347f04']['InstallationDir'] = ENV["HOME"]

