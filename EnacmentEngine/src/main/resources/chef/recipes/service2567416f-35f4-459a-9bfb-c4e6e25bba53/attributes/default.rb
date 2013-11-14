##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 2567416f-35f4-459a-9bfb-c4e6e25bba53 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['2567416f-35f4-459a-9bfb-c4e6e25bba53']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['2567416f-35f4-459a-9bfb-c4e6e25bba53']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['2567416f-35f4-459a-9bfb-c4e6e25bba53']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['2567416f-35f4-459a-9bfb-c4e6e25bba53']['InstallationDir'] = ENV["HOME"]

