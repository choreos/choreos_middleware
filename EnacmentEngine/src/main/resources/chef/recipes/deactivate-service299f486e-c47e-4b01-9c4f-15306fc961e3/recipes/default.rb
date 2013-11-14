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

#ruby_block "disable-service299f486e-c47e-4b01-9c4f-15306fc961e3" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service299f486e-c47e-4b01-9c4f-15306fc961e3::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['299f486e-c47e-4b01-9c4f-15306fc961e3']['InstallationDir']}/service-299f486e-c47e-4b01-9c4f-15306fc961e3.jar]", :immediately
#end

ruby_block "remove-service299f486e-c47e-4b01-9c4f-15306fc961e3" do
  block do
  	node.run_list.remove("recipe[service299f486e-c47e-4b01-9c4f-15306fc961e3::jar]")
  end
  only_if { node.run_list.include?("recipe[service299f486e-c47e-4b01-9c4f-15306fc961e3::jar]") }
end

ruby_block "remove-deactivate-service299f486e-c47e-4b01-9c4f-15306fc961e3" do
  block do
    node.run_list.remove("recipe[deactivate-service299f486e-c47e-4b01-9c4f-15306fc961e3]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service299f486e-c47e-4b01-9c4f-15306fc961e3]") }
end
