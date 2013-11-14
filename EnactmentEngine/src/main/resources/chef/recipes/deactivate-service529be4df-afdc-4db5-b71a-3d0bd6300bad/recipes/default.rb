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

ruby_block "remove-service529be4df-afdc-4db5-b71a-3d0bd6300bad" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service529be4df-afdc-4db5-b71a-3d0bd6300bad::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/529be4df-afdc-4db5-b71a-3d0bd6300bad.war]", :immediately
end

ruby_block "remove-service529be4df-afdc-4db5-b71a-3d0bd6300bad" do
  block do
  	node.run_list.remove("recipe[service529be4df-afdc-4db5-b71a-3d0bd6300bad::war]")
  end
  only_if { node.run_list.include?("recipe[service529be4df-afdc-4db5-b71a-3d0bd6300bad::war]") }
end

ruby_block "remove-deactivate-service529be4df-afdc-4db5-b71a-3d0bd6300bad" do
  block do
    node.run_list.remove("recipe[deactivate-service529be4df-afdc-4db5-b71a-3d0bd6300bad]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service529be4df-afdc-4db5-b71a-3d0bd6300bad]") }
end
