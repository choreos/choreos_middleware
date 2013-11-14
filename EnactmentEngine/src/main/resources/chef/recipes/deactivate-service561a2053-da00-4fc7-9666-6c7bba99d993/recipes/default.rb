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

ruby_block "remove-service561a2053-da00-4fc7-9666-6c7bba99d993" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service561a2053-da00-4fc7-9666-6c7bba99d993::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/561a2053-da00-4fc7-9666-6c7bba99d993.war]", :immediately
end

ruby_block "remove-service561a2053-da00-4fc7-9666-6c7bba99d993" do
  block do
  	node.run_list.remove("recipe[service561a2053-da00-4fc7-9666-6c7bba99d993::war]")
  end
  only_if { node.run_list.include?("recipe[service561a2053-da00-4fc7-9666-6c7bba99d993::war]") }
end

ruby_block "remove-deactivate-service561a2053-da00-4fc7-9666-6c7bba99d993" do
  block do
    node.run_list.remove("recipe[deactivate-service561a2053-da00-4fc7-9666-6c7bba99d993]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service561a2053-da00-4fc7-9666-6c7bba99d993]") }
end
