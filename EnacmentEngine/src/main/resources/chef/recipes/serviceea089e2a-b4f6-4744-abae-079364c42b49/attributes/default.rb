##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of ea089e2a-b4f6-4744-abae-079364c42b49 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['ea089e2a-b4f6-4744-abae-079364c42b49']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['ea089e2a-b4f6-4744-abae-079364c42b49']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['ea089e2a-b4f6-4744-abae-079364c42b49']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['ea089e2a-b4f6-4744-abae-079364c42b49']['InstallationDir'] = ENV["HOME"]

