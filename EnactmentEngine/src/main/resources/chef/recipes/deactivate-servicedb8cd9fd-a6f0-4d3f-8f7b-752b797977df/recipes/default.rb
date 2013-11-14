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

ruby_block "remove-servicedb8cd9fd-a6f0-4d3f-8f7b-752b797977df" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicedb8cd9fd-a6f0-4d3f-8f7b-752b797977df::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/db8cd9fd-a6f0-4d3f-8f7b-752b797977df.war]", :immediately
end

ruby_block "remove-servicedb8cd9fd-a6f0-4d3f-8f7b-752b797977df" do
  block do
  	node.run_list.remove("recipe[servicedb8cd9fd-a6f0-4d3f-8f7b-752b797977df::war]")
  end
  only_if { node.run_list.include?("recipe[servicedb8cd9fd-a6f0-4d3f-8f7b-752b797977df::war]") }
end

ruby_block "remove-deactivate-servicedb8cd9fd-a6f0-4d3f-8f7b-752b797977df" do
  block do
    node.run_list.remove("recipe[deactivate-servicedb8cd9fd-a6f0-4d3f-8f7b-752b797977df]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicedb8cd9fd-a6f0-4d3f-8f7b-752b797977df]") }
end
