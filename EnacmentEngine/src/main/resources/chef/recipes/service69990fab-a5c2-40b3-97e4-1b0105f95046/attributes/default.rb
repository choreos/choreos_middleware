##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 69990fab-a5c2-40b3-97e4-1b0105f95046 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['69990fab-a5c2-40b3-97e4-1b0105f95046']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['69990fab-a5c2-40b3-97e4-1b0105f95046']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['69990fab-a5c2-40b3-97e4-1b0105f95046']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['69990fab-a5c2-40b3-97e4-1b0105f95046']['InstallationDir'] = ENV["HOME"]

