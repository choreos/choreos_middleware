##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 7a253929-9744-465c-93c9-350a553ec236 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['7a253929-9744-465c-93c9-350a553ec236']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['7a253929-9744-465c-93c9-350a553ec236']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['7a253929-9744-465c-93c9-350a553ec236']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['7a253929-9744-465c-93c9-350a553ec236']['InstallationDir'] = ENV["HOME"]

