##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of e8671507-7c5f-4805-bfc8-69d78f1cc1c7 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['e8671507-7c5f-4805-bfc8-69d78f1cc1c7']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['e8671507-7c5f-4805-bfc8-69d78f1cc1c7']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['e8671507-7c5f-4805-bfc8-69d78f1cc1c7']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['e8671507-7c5f-4805-bfc8-69d78f1cc1c7']['InstallationDir'] = ENV["HOME"]

