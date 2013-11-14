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

ruby_block "disable-service9bcfd231-b220-4553-bc5c-8b8978605dc7" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service9bcfd231-b220-4553-bc5c-8b8978605dc7::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['9bcfd231-b220-4553-bc5c-8b8978605dc7']['InstallationDir']}/service-9bcfd231-b220-4553-bc5c-8b8978605dc7.jar]", :immediately
end

ruby_block "remove-service9bcfd231-b220-4553-bc5c-8b8978605dc7" do
  block do
  	node.run_list.remove("recipe[service9bcfd231-b220-4553-bc5c-8b8978605dc7::jar]")
  end
  only_if { node.run_list.include?("recipe[service9bcfd231-b220-4553-bc5c-8b8978605dc7::jar]") }
end

ruby_block "remove-deactivate-service9bcfd231-b220-4553-bc5c-8b8978605dc7" do
  block do
    node.run_list.remove("recipe[deactivate-service9bcfd231-b220-4553-bc5c-8b8978605dc7]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service9bcfd231-b220-4553-bc5c-8b8978605dc7]") }
end
