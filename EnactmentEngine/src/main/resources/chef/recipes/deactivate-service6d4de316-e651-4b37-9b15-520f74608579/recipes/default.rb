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

ruby_block "remove-service6d4de316-e651-4b37-9b15-520f74608579" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service6d4de316-e651-4b37-9b15-520f74608579::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/6d4de316-e651-4b37-9b15-520f74608579.war]", :immediately
end

ruby_block "remove-service6d4de316-e651-4b37-9b15-520f74608579" do
  block do
  	node.run_list.remove("recipe[service6d4de316-e651-4b37-9b15-520f74608579::war]")
  end
  only_if { node.run_list.include?("recipe[service6d4de316-e651-4b37-9b15-520f74608579::war]") }
end

ruby_block "remove-deactivate-service6d4de316-e651-4b37-9b15-520f74608579" do
  block do
    node.run_list.remove("recipe[deactivate-service6d4de316-e651-4b37-9b15-520f74608579]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service6d4de316-e651-4b37-9b15-520f74608579]") }
end
