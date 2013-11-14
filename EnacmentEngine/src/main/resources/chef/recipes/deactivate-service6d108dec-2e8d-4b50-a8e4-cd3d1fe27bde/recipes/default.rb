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

ruby_block "disable-service6d108dec-2e8d-4b50-a8e4-cd3d1fe27bde" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service6d108dec-2e8d-4b50-a8e4-cd3d1fe27bde::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['6d108dec-2e8d-4b50-a8e4-cd3d1fe27bde']['InstallationDir']}/service-6d108dec-2e8d-4b50-a8e4-cd3d1fe27bde.jar]", :immediately
end

ruby_block "remove-service6d108dec-2e8d-4b50-a8e4-cd3d1fe27bde" do
  block do
  	node.run_list.remove("recipe[service6d108dec-2e8d-4b50-a8e4-cd3d1fe27bde::jar]")
  end
  only_if { node.run_list.include?("recipe[service6d108dec-2e8d-4b50-a8e4-cd3d1fe27bde::jar]") }
end

ruby_block "remove-deactivate-service6d108dec-2e8d-4b50-a8e4-cd3d1fe27bde" do
  block do
    node.run_list.remove("recipe[deactivate-service6d108dec-2e8d-4b50-a8e4-cd3d1fe27bde]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service6d108dec-2e8d-4b50-a8e4-cd3d1fe27bde]") }
end
