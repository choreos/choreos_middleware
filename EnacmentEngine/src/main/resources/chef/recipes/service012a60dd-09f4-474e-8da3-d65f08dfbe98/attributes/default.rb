##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 012a60dd-09f4-474e-8da3-d65f08dfbe98 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['012a60dd-09f4-474e-8da3-d65f08dfbe98']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['012a60dd-09f4-474e-8da3-d65f08dfbe98']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['012a60dd-09f4-474e-8da3-d65f08dfbe98']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['012a60dd-09f4-474e-8da3-d65f08dfbe98']['InstallationDir'] = ENV["HOME"]

