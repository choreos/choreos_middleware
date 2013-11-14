##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 220053fc-89d3-4988-8887-db079fa3a7ba must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['220053fc-89d3-4988-8887-db079fa3a7ba']['WarFile'] = ""
default['CHOReOSData']['serviceData']['220053fc-89d3-4988-8887-db079fa3a7ba']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['220053fc-89d3-4988-8887-db079fa3a7ba']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['220053fc-89d3-4988-8887-db079fa3a7ba']['InstallationDir'] = ENV["HOME"]

