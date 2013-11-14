##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of c94752f5-1e10-4d50-9bcd-050d6ec2ce49 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['c94752f5-1e10-4d50-9bcd-050d6ec2ce49']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['c94752f5-1e10-4d50-9bcd-050d6ec2ce49']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['c94752f5-1e10-4d50-9bcd-050d6ec2ce49']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['c94752f5-1e10-4d50-9bcd-050d6ec2ce49']['InstallationDir'] = ENV["HOME"]

