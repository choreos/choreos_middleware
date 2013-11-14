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

ruby_block "disable-servicef10b86ff-43bb-48fa-a3cd-4a7499cde3ea" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicef10b86ff-43bb-48fa-a3cd-4a7499cde3ea::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['f10b86ff-43bb-48fa-a3cd-4a7499cde3ea']['InstallationDir']}/service-f10b86ff-43bb-48fa-a3cd-4a7499cde3ea.jar]", :immediately
end

ruby_block "remove-servicef10b86ff-43bb-48fa-a3cd-4a7499cde3ea" do
  block do
  	node.run_list.remove("recipe[servicef10b86ff-43bb-48fa-a3cd-4a7499cde3ea::jar]")
  end
  only_if { node.run_list.include?("recipe[servicef10b86ff-43bb-48fa-a3cd-4a7499cde3ea::jar]") }
end

ruby_block "remove-deactivate-servicef10b86ff-43bb-48fa-a3cd-4a7499cde3ea" do
  block do
    node.run_list.remove("recipe[deactivate-servicef10b86ff-43bb-48fa-a3cd-4a7499cde3ea]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicef10b86ff-43bb-48fa-a3cd-4a7499cde3ea]") }
end
