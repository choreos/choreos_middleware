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

ruby_block "remove-service57c4c417-6376-4999-8d50-0478909bf885" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service57c4c417-6376-4999-8d50-0478909bf885::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/57c4c417-6376-4999-8d50-0478909bf885.war]", :immediately
end

ruby_block "remove-service57c4c417-6376-4999-8d50-0478909bf885" do
  block do
  	node.run_list.remove("recipe[service57c4c417-6376-4999-8d50-0478909bf885::war]")
  end
  only_if { node.run_list.include?("recipe[service57c4c417-6376-4999-8d50-0478909bf885::war]") }
end

ruby_block "remove-deactivate-service57c4c417-6376-4999-8d50-0478909bf885" do
  block do
    node.run_list.remove("recipe[deactivate-service57c4c417-6376-4999-8d50-0478909bf885]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service57c4c417-6376-4999-8d50-0478909bf885]") }
end
