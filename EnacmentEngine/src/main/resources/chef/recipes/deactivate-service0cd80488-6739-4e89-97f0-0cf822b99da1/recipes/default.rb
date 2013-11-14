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

ruby_block "disable-service0cd80488-6739-4e89-97f0-0cf822b99da1" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service0cd80488-6739-4e89-97f0-0cf822b99da1::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['0cd80488-6739-4e89-97f0-0cf822b99da1']['InstallationDir']}/service-0cd80488-6739-4e89-97f0-0cf822b99da1.jar]", :immediately
end

ruby_block "remove-service0cd80488-6739-4e89-97f0-0cf822b99da1" do
  block do
  	node.run_list.remove("recipe[service0cd80488-6739-4e89-97f0-0cf822b99da1::jar]")
  end
  only_if { node.run_list.include?("recipe[service0cd80488-6739-4e89-97f0-0cf822b99da1::jar]") }
end

ruby_block "remove-deactivate-service0cd80488-6739-4e89-97f0-0cf822b99da1" do
  block do
    node.run_list.remove("recipe[deactivate-service0cd80488-6739-4e89-97f0-0cf822b99da1]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service0cd80488-6739-4e89-97f0-0cf822b99da1]") }
end
