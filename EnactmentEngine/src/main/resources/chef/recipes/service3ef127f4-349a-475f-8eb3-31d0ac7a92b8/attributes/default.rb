##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 3ef127f4-349a-475f-8eb3-31d0ac7a92b8 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['3ef127f4-349a-475f-8eb3-31d0ac7a92b8']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['3ef127f4-349a-475f-8eb3-31d0ac7a92b8']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['3ef127f4-349a-475f-8eb3-31d0ac7a92b8']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['3ef127f4-349a-475f-8eb3-31d0ac7a92b8']['InstallationDir'] = ENV["HOME"]

