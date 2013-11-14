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

ruby_block "remove-service409871a6-b649-4e19-a294-8585d085d28c" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service409871a6-b649-4e19-a294-8585d085d28c::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/409871a6-b649-4e19-a294-8585d085d28c.war]", :immediately
end

ruby_block "remove-service409871a6-b649-4e19-a294-8585d085d28c" do
  block do
  	node.run_list.remove("recipe[service409871a6-b649-4e19-a294-8585d085d28c::war]")
  end
  only_if { node.run_list.include?("recipe[service409871a6-b649-4e19-a294-8585d085d28c::war]") }
end

ruby_block "remove-deactivate-service409871a6-b649-4e19-a294-8585d085d28c" do
  block do
    node.run_list.remove("recipe[deactivate-service409871a6-b649-4e19-a294-8585d085d28c]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service409871a6-b649-4e19-a294-8585d085d28c]") }
end
