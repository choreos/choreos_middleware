##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 1fc28da8-d0ec-4cab-9d45-898fd45bb859 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['1fc28da8-d0ec-4cab-9d45-898fd45bb859']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['1fc28da8-d0ec-4cab-9d45-898fd45bb859']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['1fc28da8-d0ec-4cab-9d45-898fd45bb859']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['1fc28da8-d0ec-4cab-9d45-898fd45bb859']['InstallationDir'] = ENV["HOME"]

