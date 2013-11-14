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

#ruby_block "disable-service44e091fb-e199-4d9d-ba9a-de6542a7b08c" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service44e091fb-e199-4d9d-ba9a-de6542a7b08c::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['44e091fb-e199-4d9d-ba9a-de6542a7b08c']['InstallationDir']}/service-44e091fb-e199-4d9d-ba9a-de6542a7b08c.jar]", :immediately
#end

ruby_block "remove-service44e091fb-e199-4d9d-ba9a-de6542a7b08c" do
  block do
  	node.run_list.remove("recipe[service44e091fb-e199-4d9d-ba9a-de6542a7b08c::jar]")
  end
  only_if { node.run_list.include?("recipe[service44e091fb-e199-4d9d-ba9a-de6542a7b08c::jar]") }
end

ruby_block "remove-deactivate-service44e091fb-e199-4d9d-ba9a-de6542a7b08c" do
  block do
    node.run_list.remove("recipe[deactivate-service44e091fb-e199-4d9d-ba9a-de6542a7b08c]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service44e091fb-e199-4d9d-ba9a-de6542a7b08c]") }
end
