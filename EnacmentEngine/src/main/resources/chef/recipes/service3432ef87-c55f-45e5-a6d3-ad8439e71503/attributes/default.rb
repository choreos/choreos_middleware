##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 3432ef87-c55f-45e5-a6d3-ad8439e71503 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['3432ef87-c55f-45e5-a6d3-ad8439e71503']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['3432ef87-c55f-45e5-a6d3-ad8439e71503']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['3432ef87-c55f-45e5-a6d3-ad8439e71503']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['3432ef87-c55f-45e5-a6d3-ad8439e71503']['InstallationDir'] = ENV["HOME"]

