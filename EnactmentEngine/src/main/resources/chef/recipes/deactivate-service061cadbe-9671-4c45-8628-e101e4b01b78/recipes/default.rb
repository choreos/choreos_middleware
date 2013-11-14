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

ruby_block "remove-service061cadbe-9671-4c45-8628-e101e4b01b78" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service061cadbe-9671-4c45-8628-e101e4b01b78::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/061cadbe-9671-4c45-8628-e101e4b01b78.war]", :immediately
end

ruby_block "remove-service061cadbe-9671-4c45-8628-e101e4b01b78" do
  block do
  	node.run_list.remove("recipe[service061cadbe-9671-4c45-8628-e101e4b01b78::war]")
  end
  only_if { node.run_list.include?("recipe[service061cadbe-9671-4c45-8628-e101e4b01b78::war]") }
end

ruby_block "remove-deactivate-service061cadbe-9671-4c45-8628-e101e4b01b78" do
  block do
    node.run_list.remove("recipe[deactivate-service061cadbe-9671-4c45-8628-e101e4b01b78]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service061cadbe-9671-4c45-8628-e101e4b01b78]") }
end
