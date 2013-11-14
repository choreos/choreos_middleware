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

ruby_block "remove-serviceb184fa33-5069-4897-8ecf-6ddace87ea25" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceb184fa33-5069-4897-8ecf-6ddace87ea25::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/b184fa33-5069-4897-8ecf-6ddace87ea25.war]", :immediately
end

ruby_block "remove-serviceb184fa33-5069-4897-8ecf-6ddace87ea25" do
  block do
  	node.run_list.remove("recipe[serviceb184fa33-5069-4897-8ecf-6ddace87ea25::war]")
  end
  only_if { node.run_list.include?("recipe[serviceb184fa33-5069-4897-8ecf-6ddace87ea25::war]") }
end

ruby_block "remove-deactivate-serviceb184fa33-5069-4897-8ecf-6ddace87ea25" do
  block do
    node.run_list.remove("recipe[deactivate-serviceb184fa33-5069-4897-8ecf-6ddace87ea25]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceb184fa33-5069-4897-8ecf-6ddace87ea25]") }
end
