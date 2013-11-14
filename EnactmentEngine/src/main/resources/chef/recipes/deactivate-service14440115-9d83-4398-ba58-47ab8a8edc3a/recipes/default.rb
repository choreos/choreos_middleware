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

ruby_block "disable-service14440115-9d83-4398-ba58-47ab8a8edc3a" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service14440115-9d83-4398-ba58-47ab8a8edc3a::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['14440115-9d83-4398-ba58-47ab8a8edc3a']['InstallationDir']}/service-14440115-9d83-4398-ba58-47ab8a8edc3a.jar]", :immediately
end

ruby_block "remove-service14440115-9d83-4398-ba58-47ab8a8edc3a" do
  block do
  	node.run_list.remove("recipe[service14440115-9d83-4398-ba58-47ab8a8edc3a::jar]")
  end
  only_if { node.run_list.include?("recipe[service14440115-9d83-4398-ba58-47ab8a8edc3a::jar]") }
end

ruby_block "remove-deactivate-service14440115-9d83-4398-ba58-47ab8a8edc3a" do
  block do
    node.run_list.remove("recipe[deactivate-service14440115-9d83-4398-ba58-47ab8a8edc3a]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service14440115-9d83-4398-ba58-47ab8a8edc3a]") }
end
