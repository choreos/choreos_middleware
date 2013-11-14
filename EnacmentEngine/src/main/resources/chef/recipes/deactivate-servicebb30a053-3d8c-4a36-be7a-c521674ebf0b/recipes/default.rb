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

ruby_block "remove-servicebb30a053-3d8c-4a36-be7a-c521674ebf0b" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicebb30a053-3d8c-4a36-be7a-c521674ebf0b::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/bb30a053-3d8c-4a36-be7a-c521674ebf0b.war]", :immediately
end

ruby_block "remove-servicebb30a053-3d8c-4a36-be7a-c521674ebf0b" do
  block do
  	node.run_list.remove("recipe[servicebb30a053-3d8c-4a36-be7a-c521674ebf0b::war]")
  end
  only_if { node.run_list.include?("recipe[servicebb30a053-3d8c-4a36-be7a-c521674ebf0b::war]") }
end

ruby_block "remove-deactivate-servicebb30a053-3d8c-4a36-be7a-c521674ebf0b" do
  block do
    node.run_list.remove("recipe[deactivate-servicebb30a053-3d8c-4a36-be7a-c521674ebf0b]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicebb30a053-3d8c-4a36-be7a-c521674ebf0b]") }
end
