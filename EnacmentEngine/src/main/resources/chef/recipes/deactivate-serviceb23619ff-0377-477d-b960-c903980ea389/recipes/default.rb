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

ruby_block "remove-serviceb23619ff-0377-477d-b960-c903980ea389" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceb23619ff-0377-477d-b960-c903980ea389::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/b23619ff-0377-477d-b960-c903980ea389.war]", :immediately
end

ruby_block "remove-serviceb23619ff-0377-477d-b960-c903980ea389" do
  block do
  	node.run_list.remove("recipe[serviceb23619ff-0377-477d-b960-c903980ea389::war]")
  end
  only_if { node.run_list.include?("recipe[serviceb23619ff-0377-477d-b960-c903980ea389::war]") }
end

ruby_block "remove-deactivate-serviceb23619ff-0377-477d-b960-c903980ea389" do
  block do
    node.run_list.remove("recipe[deactivate-serviceb23619ff-0377-477d-b960-c903980ea389]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceb23619ff-0377-477d-b960-c903980ea389]") }
end
