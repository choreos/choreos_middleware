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

ruby_block "remove-service189fc2f6-cdbc-44f9-b6ee-140ef4cb5e8f" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service189fc2f6-cdbc-44f9-b6ee-140ef4cb5e8f::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/189fc2f6-cdbc-44f9-b6ee-140ef4cb5e8f.war]", :immediately
end

ruby_block "remove-service189fc2f6-cdbc-44f9-b6ee-140ef4cb5e8f" do
  block do
  	node.run_list.remove("recipe[service189fc2f6-cdbc-44f9-b6ee-140ef4cb5e8f::war]")
  end
  only_if { node.run_list.include?("recipe[service189fc2f6-cdbc-44f9-b6ee-140ef4cb5e8f::war]") }
end

ruby_block "remove-deactivate-service189fc2f6-cdbc-44f9-b6ee-140ef4cb5e8f" do
  block do
    node.run_list.remove("recipe[deactivate-service189fc2f6-cdbc-44f9-b6ee-140ef4cb5e8f]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service189fc2f6-cdbc-44f9-b6ee-140ef4cb5e8f]") }
end
