##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 19d987cd-7eb2-4ccf-8ee0-b115ab22076c must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['19d987cd-7eb2-4ccf-8ee0-b115ab22076c']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['19d987cd-7eb2-4ccf-8ee0-b115ab22076c']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['19d987cd-7eb2-4ccf-8ee0-b115ab22076c']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['19d987cd-7eb2-4ccf-8ee0-b115ab22076c']['InstallationDir'] = ENV["HOME"]

