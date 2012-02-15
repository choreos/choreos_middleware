##########################################################################
#									 #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#									 #
#  All ocurrences of $UUID must be replaced with the actual storage $UUID #
#            before uploading the recipe to the chef-server              #
#									 #
##########################################################################


# $UUID of the correlation/storage 
default['storage']['$UUID']['id']		= "$UUID"

# database user for the storage 
default['storage']['$UUID']['dbuser']		= "$USER"

# Preset user password
default['storage']['$UUID']['dbpassword']	= "$PASSWORD"

# Schema created in the database
default['storage']['$UUID']['schema']		= "$SCHEMA"

# Type of the storage 
default['storage']['$UUID']['type']		= "$TYPE"

# Location of the storage deployment script
#default['storage-$UUID']['sql-script']	= ENV['HOME']/$UUID-sql-script.sql
default['storage']['$UUID']['sqlscript']	= "/tmp/$UUID-sql-script.sql"
