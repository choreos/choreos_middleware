##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of fd89a7fd-9541-442c-af06-4f62ad36e651 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['fd89a7fd-9541-442c-af06-4f62ad36e651']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['fd89a7fd-9541-442c-af06-4f62ad36e651']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['fd89a7fd-9541-442c-af06-4f62ad36e651']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['fd89a7fd-9541-442c-af06-4f62ad36e651']['InstallationDir'] = ENV["HOME"]

