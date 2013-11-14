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

ruby_block "remove-serviced6ec0cca-0887-4f9b-8dda-53c34b7c0f88" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviced6ec0cca-0887-4f9b-8dda-53c34b7c0f88::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/d6ec0cca-0887-4f9b-8dda-53c34b7c0f88.war]", :immediately
end

ruby_block "remove-serviced6ec0cca-0887-4f9b-8dda-53c34b7c0f88" do
  block do
  	node.run_list.remove("recipe[serviced6ec0cca-0887-4f9b-8dda-53c34b7c0f88::war]")
  end
  only_if { node.run_list.include?("recipe[serviced6ec0cca-0887-4f9b-8dda-53c34b7c0f88::war]") }
end

ruby_block "remove-deactivate-serviced6ec0cca-0887-4f9b-8dda-53c34b7c0f88" do
  block do
    node.run_list.remove("recipe[deactivate-serviced6ec0cca-0887-4f9b-8dda-53c34b7c0f88]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviced6ec0cca-0887-4f9b-8dda-53c34b7c0f88]") }
end
