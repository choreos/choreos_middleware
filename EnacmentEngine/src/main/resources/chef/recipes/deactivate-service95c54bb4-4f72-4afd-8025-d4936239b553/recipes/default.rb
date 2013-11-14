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

ruby_block "remove-service95c54bb4-4f72-4afd-8025-d4936239b553" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service95c54bb4-4f72-4afd-8025-d4936239b553::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/95c54bb4-4f72-4afd-8025-d4936239b553.war]", :immediately
end

ruby_block "remove-service95c54bb4-4f72-4afd-8025-d4936239b553" do
  block do
  	node.run_list.remove("recipe[service95c54bb4-4f72-4afd-8025-d4936239b553::war]")
  end
  only_if { node.run_list.include?("recipe[service95c54bb4-4f72-4afd-8025-d4936239b553::war]") }
end

ruby_block "remove-deactivate-service95c54bb4-4f72-4afd-8025-d4936239b553" do
  block do
    node.run_list.remove("recipe[deactivate-service95c54bb4-4f72-4afd-8025-d4936239b553]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service95c54bb4-4f72-4afd-8025-d4936239b553]") }
end
