##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 17fd9ae2-0e1d-47eb-acc6-1ecafbe4eb90 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['17fd9ae2-0e1d-47eb-acc6-1ecafbe4eb90']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['17fd9ae2-0e1d-47eb-acc6-1ecafbe4eb90']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['17fd9ae2-0e1d-47eb-acc6-1ecafbe4eb90']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['17fd9ae2-0e1d-47eb-acc6-1ecafbe4eb90']['InstallationDir'] = ENV["HOME"]

