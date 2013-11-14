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

ruby_block "remove-servicee6aec36f-0f60-4a06-bec6-3462f431b8cd" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicee6aec36f-0f60-4a06-bec6-3462f431b8cd::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/e6aec36f-0f60-4a06-bec6-3462f431b8cd.war]", :immediately
end

ruby_block "remove-servicee6aec36f-0f60-4a06-bec6-3462f431b8cd" do
  block do
  	node.run_list.remove("recipe[servicee6aec36f-0f60-4a06-bec6-3462f431b8cd::war]")
  end
  only_if { node.run_list.include?("recipe[servicee6aec36f-0f60-4a06-bec6-3462f431b8cd::war]") }
end

ruby_block "remove-deactivate-servicee6aec36f-0f60-4a06-bec6-3462f431b8cd" do
  block do
    node.run_list.remove("recipe[deactivate-servicee6aec36f-0f60-4a06-bec6-3462f431b8cd]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicee6aec36f-0f60-4a06-bec6-3462f431b8cd]") }
end
