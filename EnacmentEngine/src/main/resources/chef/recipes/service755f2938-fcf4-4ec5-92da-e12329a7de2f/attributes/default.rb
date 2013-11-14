##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 755f2938-fcf4-4ec5-92da-e12329a7de2f must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['755f2938-fcf4-4ec5-92da-e12329a7de2f']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['755f2938-fcf4-4ec5-92da-e12329a7de2f']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['755f2938-fcf4-4ec5-92da-e12329a7de2f']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['755f2938-fcf4-4ec5-92da-e12329a7de2f']['InstallationDir'] = ENV["HOME"]

