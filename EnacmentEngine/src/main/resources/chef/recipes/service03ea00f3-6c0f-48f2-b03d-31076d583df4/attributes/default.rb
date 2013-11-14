##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 03ea00f3-6c0f-48f2-b03d-31076d583df4 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['03ea00f3-6c0f-48f2-b03d-31076d583df4']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['03ea00f3-6c0f-48f2-b03d-31076d583df4']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['03ea00f3-6c0f-48f2-b03d-31076d583df4']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['03ea00f3-6c0f-48f2-b03d-31076d583df4']['InstallationDir'] = ENV["HOME"]

