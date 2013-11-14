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

ruby_block "disable-service46dc9a56-953f-4a8f-b79a-0f7eed9961f1" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service46dc9a56-953f-4a8f-b79a-0f7eed9961f1::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['46dc9a56-953f-4a8f-b79a-0f7eed9961f1']['InstallationDir']}/service-46dc9a56-953f-4a8f-b79a-0f7eed9961f1.jar]", :immediately
end

ruby_block "remove-service46dc9a56-953f-4a8f-b79a-0f7eed9961f1" do
  block do
  	node.run_list.remove("recipe[service46dc9a56-953f-4a8f-b79a-0f7eed9961f1::jar]")
  end
  only_if { node.run_list.include?("recipe[service46dc9a56-953f-4a8f-b79a-0f7eed9961f1::jar]") }
end

ruby_block "remove-deactivate-service46dc9a56-953f-4a8f-b79a-0f7eed9961f1" do
  block do
    node.run_list.remove("recipe[deactivate-service46dc9a56-953f-4a8f-b79a-0f7eed9961f1]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service46dc9a56-953f-4a8f-b79a-0f7eed9961f1]") }
end
