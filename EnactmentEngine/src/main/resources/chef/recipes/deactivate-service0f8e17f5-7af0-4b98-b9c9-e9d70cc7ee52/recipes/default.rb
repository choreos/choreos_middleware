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

ruby_block "remove-service0f8e17f5-7af0-4b98-b9c9-e9d70cc7ee52" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service0f8e17f5-7af0-4b98-b9c9-e9d70cc7ee52::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/0f8e17f5-7af0-4b98-b9c9-e9d70cc7ee52.war]", :immediately
end

ruby_block "remove-service0f8e17f5-7af0-4b98-b9c9-e9d70cc7ee52" do
  block do
  	node.run_list.remove("recipe[service0f8e17f5-7af0-4b98-b9c9-e9d70cc7ee52::war]")
  end
  only_if { node.run_list.include?("recipe[service0f8e17f5-7af0-4b98-b9c9-e9d70cc7ee52::war]") }
end

ruby_block "remove-deactivate-service0f8e17f5-7af0-4b98-b9c9-e9d70cc7ee52" do
  block do
    node.run_list.remove("recipe[deactivate-service0f8e17f5-7af0-4b98-b9c9-e9d70cc7ee52]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service0f8e17f5-7af0-4b98-b9c9-e9d70cc7ee52]") }
end
