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

ruby_block "remove-servicedf338b9d-86e6-4ee1-8618-4e66bcbf102f" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicedf338b9d-86e6-4ee1-8618-4e66bcbf102f::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/df338b9d-86e6-4ee1-8618-4e66bcbf102f.war]", :immediately
end

ruby_block "remove-servicedf338b9d-86e6-4ee1-8618-4e66bcbf102f" do
  block do
  	node.run_list.remove("recipe[servicedf338b9d-86e6-4ee1-8618-4e66bcbf102f::war]")
  end
  only_if { node.run_list.include?("recipe[servicedf338b9d-86e6-4ee1-8618-4e66bcbf102f::war]") }
end

ruby_block "remove-deactivate-servicedf338b9d-86e6-4ee1-8618-4e66bcbf102f" do
  block do
    node.run_list.remove("recipe[deactivate-servicedf338b9d-86e6-4ee1-8618-4e66bcbf102f]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicedf338b9d-86e6-4ee1-8618-4e66bcbf102f]") }
end
