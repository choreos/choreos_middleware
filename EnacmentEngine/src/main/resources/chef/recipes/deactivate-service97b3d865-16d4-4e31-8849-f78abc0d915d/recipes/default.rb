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

ruby_block "disable-service97b3d865-16d4-4e31-8849-f78abc0d915d" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service97b3d865-16d4-4e31-8849-f78abc0d915d::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['97b3d865-16d4-4e31-8849-f78abc0d915d']['InstallationDir']}/service-97b3d865-16d4-4e31-8849-f78abc0d915d.jar]", :immediately
end

ruby_block "remove-service97b3d865-16d4-4e31-8849-f78abc0d915d" do
  block do
  	node.run_list.remove("recipe[service97b3d865-16d4-4e31-8849-f78abc0d915d::jar]")
  end
  only_if { node.run_list.include?("recipe[service97b3d865-16d4-4e31-8849-f78abc0d915d::jar]") }
end

ruby_block "remove-deactivate-service97b3d865-16d4-4e31-8849-f78abc0d915d" do
  block do
    node.run_list.remove("recipe[deactivate-service97b3d865-16d4-4e31-8849-f78abc0d915d]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service97b3d865-16d4-4e31-8849-f78abc0d915d]") }
end
