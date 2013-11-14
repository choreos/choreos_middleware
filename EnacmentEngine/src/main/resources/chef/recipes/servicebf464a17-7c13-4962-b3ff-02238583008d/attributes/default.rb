##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of bf464a17-7c13-4962-b3ff-02238583008d must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['bf464a17-7c13-4962-b3ff-02238583008d']['WarFile'] = ""
default['CHOReOSData']['serviceData']['bf464a17-7c13-4962-b3ff-02238583008d']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['bf464a17-7c13-4962-b3ff-02238583008d']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['bf464a17-7c13-4962-b3ff-02238583008d']['InstallationDir'] = ENV["HOME"]

