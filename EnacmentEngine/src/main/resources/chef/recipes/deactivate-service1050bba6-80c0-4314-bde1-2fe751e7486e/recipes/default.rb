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

ruby_block "remove-service1050bba6-80c0-4314-bde1-2fe751e7486e" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service1050bba6-80c0-4314-bde1-2fe751e7486e::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/1050bba6-80c0-4314-bde1-2fe751e7486e.war]", :immediately
end

ruby_block "remove-service1050bba6-80c0-4314-bde1-2fe751e7486e" do
  block do
  	node.run_list.remove("recipe[service1050bba6-80c0-4314-bde1-2fe751e7486e::war]")
  end
  only_if { node.run_list.include?("recipe[service1050bba6-80c0-4314-bde1-2fe751e7486e::war]") }
end

ruby_block "remove-deactivate-service1050bba6-80c0-4314-bde1-2fe751e7486e" do
  block do
    node.run_list.remove("recipe[deactivate-service1050bba6-80c0-4314-bde1-2fe751e7486e]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service1050bba6-80c0-4314-bde1-2fe751e7486e]") }
end
