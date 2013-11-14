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

ruby_block "remove-service62a3c144-54fc-4cc6-a2ba-1f72f5277d41" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service62a3c144-54fc-4cc6-a2ba-1f72f5277d41::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/62a3c144-54fc-4cc6-a2ba-1f72f5277d41.war]", :immediately
end

ruby_block "remove-service62a3c144-54fc-4cc6-a2ba-1f72f5277d41" do
  block do
  	node.run_list.remove("recipe[service62a3c144-54fc-4cc6-a2ba-1f72f5277d41::war]")
  end
  only_if { node.run_list.include?("recipe[service62a3c144-54fc-4cc6-a2ba-1f72f5277d41::war]") }
end

ruby_block "remove-deactivate-service62a3c144-54fc-4cc6-a2ba-1f72f5277d41" do
  block do
    node.run_list.remove("recipe[deactivate-service62a3c144-54fc-4cc6-a2ba-1f72f5277d41]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service62a3c144-54fc-4cc6-a2ba-1f72f5277d41]") }
end
