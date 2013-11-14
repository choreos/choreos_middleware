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

ruby_block "remove-service7ec688c9-b875-484c-a9f9-205b3d004032" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service7ec688c9-b875-484c-a9f9-205b3d004032::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/7ec688c9-b875-484c-a9f9-205b3d004032.war]", :immediately
end

ruby_block "remove-service7ec688c9-b875-484c-a9f9-205b3d004032" do
  block do
  	node.run_list.remove("recipe[service7ec688c9-b875-484c-a9f9-205b3d004032::war]")
  end
  only_if { node.run_list.include?("recipe[service7ec688c9-b875-484c-a9f9-205b3d004032::war]") }
end

ruby_block "remove-deactivate-service7ec688c9-b875-484c-a9f9-205b3d004032" do
  block do
    node.run_list.remove("recipe[deactivate-service7ec688c9-b875-484c-a9f9-205b3d004032]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service7ec688c9-b875-484c-a9f9-205b3d004032]") }
end
