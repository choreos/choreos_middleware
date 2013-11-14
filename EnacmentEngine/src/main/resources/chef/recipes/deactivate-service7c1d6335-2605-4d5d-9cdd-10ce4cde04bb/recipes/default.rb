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

#ruby_block "disable-service7c1d6335-2605-4d5d-9cdd-10ce4cde04bb" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service7c1d6335-2605-4d5d-9cdd-10ce4cde04bb::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['7c1d6335-2605-4d5d-9cdd-10ce4cde04bb']['InstallationDir']}/service-7c1d6335-2605-4d5d-9cdd-10ce4cde04bb.jar]", :immediately
#end

ruby_block "remove-service7c1d6335-2605-4d5d-9cdd-10ce4cde04bb" do
  block do
  	node.run_list.remove("recipe[service7c1d6335-2605-4d5d-9cdd-10ce4cde04bb::jar]")
  end
  only_if { node.run_list.include?("recipe[service7c1d6335-2605-4d5d-9cdd-10ce4cde04bb::jar]") }
end

ruby_block "remove-deactivate-service7c1d6335-2605-4d5d-9cdd-10ce4cde04bb" do
  block do
    node.run_list.remove("recipe[deactivate-service7c1d6335-2605-4d5d-9cdd-10ce4cde04bb]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service7c1d6335-2605-4d5d-9cdd-10ce4cde04bb]") }
end
