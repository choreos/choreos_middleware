##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of df6f7b76-c0d2-45e3-b216-8b599b27436d must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['df6f7b76-c0d2-45e3-b216-8b599b27436d']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['df6f7b76-c0d2-45e3-b216-8b599b27436d']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['df6f7b76-c0d2-45e3-b216-8b599b27436d']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['df6f7b76-c0d2-45e3-b216-8b599b27436d']['InstallationDir'] = ENV["HOME"]

