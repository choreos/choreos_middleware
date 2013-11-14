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

ruby_block "remove-service1cbe108f-3653-4224-8e4f-e0b74ca8586b" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service1cbe108f-3653-4224-8e4f-e0b74ca8586b::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/1cbe108f-3653-4224-8e4f-e0b74ca8586b.war]", :immediately
end

ruby_block "remove-service1cbe108f-3653-4224-8e4f-e0b74ca8586b" do
  block do
  	node.run_list.remove("recipe[service1cbe108f-3653-4224-8e4f-e0b74ca8586b::war]")
  end
  only_if { node.run_list.include?("recipe[service1cbe108f-3653-4224-8e4f-e0b74ca8586b::war]") }
end

ruby_block "remove-deactivate-service1cbe108f-3653-4224-8e4f-e0b74ca8586b" do
  block do
    node.run_list.remove("recipe[deactivate-service1cbe108f-3653-4224-8e4f-e0b74ca8586b]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service1cbe108f-3653-4224-8e4f-e0b74ca8586b]") }
end
