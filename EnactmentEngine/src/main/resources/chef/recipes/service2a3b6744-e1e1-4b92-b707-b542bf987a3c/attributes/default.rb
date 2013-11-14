##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 2a3b6744-e1e1-4b92-b707-b542bf987a3c must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['2a3b6744-e1e1-4b92-b707-b542bf987a3c']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['2a3b6744-e1e1-4b92-b707-b542bf987a3c']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['2a3b6744-e1e1-4b92-b707-b542bf987a3c']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['2a3b6744-e1e1-4b92-b707-b542bf987a3c']['InstallationDir'] = ENV["HOME"]

