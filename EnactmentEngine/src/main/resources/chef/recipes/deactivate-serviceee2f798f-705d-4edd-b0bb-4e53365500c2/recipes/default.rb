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

ruby_block "remove-serviceee2f798f-705d-4edd-b0bb-4e53365500c2" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceee2f798f-705d-4edd-b0bb-4e53365500c2::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/ee2f798f-705d-4edd-b0bb-4e53365500c2.war]", :immediately
end

ruby_block "remove-serviceee2f798f-705d-4edd-b0bb-4e53365500c2" do
  block do
  	node.run_list.remove("recipe[serviceee2f798f-705d-4edd-b0bb-4e53365500c2::war]")
  end
  only_if { node.run_list.include?("recipe[serviceee2f798f-705d-4edd-b0bb-4e53365500c2::war]") }
end

ruby_block "remove-deactivate-serviceee2f798f-705d-4edd-b0bb-4e53365500c2" do
  block do
    node.run_list.remove("recipe[deactivate-serviceee2f798f-705d-4edd-b0bb-4e53365500c2]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceee2f798f-705d-4edd-b0bb-4e53365500c2]") }
end
