#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#                  #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                  #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#                  #
##########################################################################

#ruby_block "disable-servicee34110d4-d4b9-4279-a0b1-45db116a3bc4" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[servicee34110d4-d4b9-4279-a0b1-45db116a3bc4::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['e34110d4-d4b9-4279-a0b1-45db116a3bc4']['InstallationDir']}/service-e34110d4-d4b9-4279-a0b1-45db116a3bc4.jar]", :immediately
#end

ruby_block "remove-servicee34110d4-d4b9-4279-a0b1-45db116a3bc4" do
  block do
  	node.run_list.remove("recipe[servicee34110d4-d4b9-4279-a0b1-45db116a3bc4::jar]")
  end
  only_if { node.run_list.include?("recipe[servicee34110d4-d4b9-4279-a0b1-45db116a3bc4::jar]") }
end

ruby_block "remove-deactivate-servicee34110d4-d4b9-4279-a0b1-45db116a3bc4" do
  block do
    node.run_list.remove("recipe[deactivate-servicee34110d4-d4b9-4279-a0b1-45db116a3bc4]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicee34110d4-d4b9-4279-a0b1-45db116a3bc4]") }
end
