##########################################################################
#									 #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#									 #
#  All ocurrences of $UUID must be replaced with the actual storage UUID #
#            before uploading the recipe to the chef-server              #
#									 #
##########################################################################


# UUID of the correlation/storage 
node['storage']['$UUID']['uuid']	= $UUID

# database user for the storage 
node['storage']['$UUID']['db-user']	= $UUID

# Preset user password
node['storage']['$UUID']['db-user']	= $UUID

# Schema created in the database
node['storage']['$UUID']['schema']	= $UUID

# Type of the storage 
node['storage']['$UUID']['type']	= $UUID

# Location of the storage deployment script
node['storage']['$UUID']['sql-script']	= ENV['HOME']/create-database-$UUID-sql-script.sql
