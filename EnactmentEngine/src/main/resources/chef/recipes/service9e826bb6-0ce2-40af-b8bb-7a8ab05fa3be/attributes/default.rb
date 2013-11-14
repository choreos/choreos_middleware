##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 9e826bb6-0ce2-40af-b8bb-7a8ab05fa3be must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['9e826bb6-0ce2-40af-b8bb-7a8ab05fa3be']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['9e826bb6-0ce2-40af-b8bb-7a8ab05fa3be']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['9e826bb6-0ce2-40af-b8bb-7a8ab05fa3be']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['9e826bb6-0ce2-40af-b8bb-7a8ab05fa3be']['InstallationDir'] = ENV["HOME"]

