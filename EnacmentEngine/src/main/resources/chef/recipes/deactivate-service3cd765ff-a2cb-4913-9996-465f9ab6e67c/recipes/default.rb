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

ruby_block "remove-service3cd765ff-a2cb-4913-9996-465f9ab6e67c" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service3cd765ff-a2cb-4913-9996-465f9ab6e67c::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/3cd765ff-a2cb-4913-9996-465f9ab6e67c.war]", :immediately
end

ruby_block "remove-service3cd765ff-a2cb-4913-9996-465f9ab6e67c" do
  block do
  	node.run_list.remove("recipe[service3cd765ff-a2cb-4913-9996-465f9ab6e67c::war]")
  end
  only_if { node.run_list.include?("recipe[service3cd765ff-a2cb-4913-9996-465f9ab6e67c::war]") }
end

ruby_block "remove-deactivate-service3cd765ff-a2cb-4913-9996-465f9ab6e67c" do
  block do
    node.run_list.remove("recipe[deactivate-service3cd765ff-a2cb-4913-9996-465f9ab6e67c]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service3cd765ff-a2cb-4913-9996-465f9ab6e67c]") }
end
