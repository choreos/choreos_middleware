##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 94ea3419-0383-4880-9fd4-76d5ac867e25 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['94ea3419-0383-4880-9fd4-76d5ac867e25']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['94ea3419-0383-4880-9fd4-76d5ac867e25']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['94ea3419-0383-4880-9fd4-76d5ac867e25']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['94ea3419-0383-4880-9fd4-76d5ac867e25']['InstallationDir'] = ENV["HOME"]

