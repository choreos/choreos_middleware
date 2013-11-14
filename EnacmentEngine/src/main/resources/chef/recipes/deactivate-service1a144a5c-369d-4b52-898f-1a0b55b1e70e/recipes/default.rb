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

ruby_block "remove-service1a144a5c-369d-4b52-898f-1a0b55b1e70e" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service1a144a5c-369d-4b52-898f-1a0b55b1e70e::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/1a144a5c-369d-4b52-898f-1a0b55b1e70e.war]", :immediately
end

ruby_block "remove-service1a144a5c-369d-4b52-898f-1a0b55b1e70e" do
  block do
  	node.run_list.remove("recipe[service1a144a5c-369d-4b52-898f-1a0b55b1e70e::war]")
  end
  only_if { node.run_list.include?("recipe[service1a144a5c-369d-4b52-898f-1a0b55b1e70e::war]") }
end

ruby_block "remove-deactivate-service1a144a5c-369d-4b52-898f-1a0b55b1e70e" do
  block do
    node.run_list.remove("recipe[deactivate-service1a144a5c-369d-4b52-898f-1a0b55b1e70e]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service1a144a5c-369d-4b52-898f-1a0b55b1e70e]") }
end
