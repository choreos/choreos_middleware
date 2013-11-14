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

ruby_block "remove-servicea35054ce-afeb-48c0-a4e7-4f3d79ffb95c" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicea35054ce-afeb-48c0-a4e7-4f3d79ffb95c::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/a35054ce-afeb-48c0-a4e7-4f3d79ffb95c.war]", :immediately
end

ruby_block "remove-servicea35054ce-afeb-48c0-a4e7-4f3d79ffb95c" do
  block do
  	node.run_list.remove("recipe[servicea35054ce-afeb-48c0-a4e7-4f3d79ffb95c::war]")
  end
  only_if { node.run_list.include?("recipe[servicea35054ce-afeb-48c0-a4e7-4f3d79ffb95c::war]") }
end

ruby_block "remove-deactivate-servicea35054ce-afeb-48c0-a4e7-4f3d79ffb95c" do
  block do
    node.run_list.remove("recipe[deactivate-servicea35054ce-afeb-48c0-a4e7-4f3d79ffb95c]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicea35054ce-afeb-48c0-a4e7-4f3d79ffb95c]") }
end
