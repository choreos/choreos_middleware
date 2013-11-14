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

ruby_block "disable-service539a0d95-1f8c-4ce8-9e85-354729d4fde8" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service539a0d95-1f8c-4ce8-9e85-354729d4fde8::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['539a0d95-1f8c-4ce8-9e85-354729d4fde8']['InstallationDir']}/service-539a0d95-1f8c-4ce8-9e85-354729d4fde8.jar]", :immediately
end

ruby_block "remove-service539a0d95-1f8c-4ce8-9e85-354729d4fde8" do
  block do
  	node.run_list.remove("recipe[service539a0d95-1f8c-4ce8-9e85-354729d4fde8::jar]")
  end
  only_if { node.run_list.include?("recipe[service539a0d95-1f8c-4ce8-9e85-354729d4fde8::jar]") }
end

ruby_block "remove-deactivate-service539a0d95-1f8c-4ce8-9e85-354729d4fde8" do
  block do
    node.run_list.remove("recipe[deactivate-service539a0d95-1f8c-4ce8-9e85-354729d4fde8]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service539a0d95-1f8c-4ce8-9e85-354729d4fde8]") }
end
