##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 713e97cf-aba6-460a-ac75-324039a041fa must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['713e97cf-aba6-460a-ac75-324039a041fa']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['713e97cf-aba6-460a-ac75-324039a041fa']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['713e97cf-aba6-460a-ac75-324039a041fa']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['713e97cf-aba6-460a-ac75-324039a041fa']['InstallationDir'] = ENV["HOME"]

