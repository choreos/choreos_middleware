#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#                  														 #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                  														 #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#                  														 #
##########################################################################

ruby_block "remove-service174d4b77-aaf1-4019-af40-da3dcd18c00c" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service174d4b77-aaf1-4019-af40-da3dcd18c00c::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/174d4b77-aaf1-4019-af40-da3dcd18c00c.war]", :immediately
end

ruby_block "remove-service174d4b77-aaf1-4019-af40-da3dcd18c00c" do
  block do
  	node.run_list.remove("recipe[service174d4b77-aaf1-4019-af40-da3dcd18c00c::war]")
  end
  only_if { node.run_list.include?("recipe[service174d4b77-aaf1-4019-af40-da3dcd18c00c::war]") }
end

ruby_block "remove-deactivate-service174d4b77-aaf1-4019-af40-da3dcd18c00c" do
  block do
    node.run_list.remove("recipe[deactivate-service174d4b77-aaf1-4019-af40-da3dcd18c00c]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service174d4b77-aaf1-4019-af40-da3dcd18c00c]") }
end
