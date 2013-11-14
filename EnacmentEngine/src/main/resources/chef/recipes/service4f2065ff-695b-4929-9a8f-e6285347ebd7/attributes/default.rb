##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 4f2065ff-695b-4929-9a8f-e6285347ebd7 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['4f2065ff-695b-4929-9a8f-e6285347ebd7']['WarFile'] = ""
default['CHOReOSData']['serviceData']['4f2065ff-695b-4929-9a8f-e6285347ebd7']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['4f2065ff-695b-4929-9a8f-e6285347ebd7']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['4f2065ff-695b-4929-9a8f-e6285347ebd7']['InstallationDir'] = ENV["HOME"]

