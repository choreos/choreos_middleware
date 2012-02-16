##########################################################################
#									 #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#									 #
# All ocurrences of $ UUID must be replaced with the actual storage 12345789#
#            before uploading the recipe to the chef-server              #
#									 #
##########################################################################


# 12345789 of the correlation/storage 
default['storage']['12345789']['id']		= "12345789"

# database user for the storage 
default['storage']['12345789']['dbuser']		= "UZER"

# Preset user password
default['storage']['12345789']['dbpassword']	= "CODE"

# Schema created in the database
default['storage']['12345789']['schema']		= "SKEMA"

# Type of the storage 
default['storage']['12345789']['type']		= "MYSQL"

# Location of the storage deployment script
#default['storage-12345789']['sql-script']	= ENV['HOME']/12345789-sql-script.sql
default['storage']['12345789']['sqlscript']	= "/tmp/12345789-sql-script.sql"
