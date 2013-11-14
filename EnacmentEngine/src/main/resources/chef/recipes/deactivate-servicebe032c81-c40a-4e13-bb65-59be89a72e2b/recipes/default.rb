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

ruby_block "remove-servicebe032c81-c40a-4e13-bb65-59be89a72e2b" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicebe032c81-c40a-4e13-bb65-59be89a72e2b::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/be032c81-c40a-4e13-bb65-59be89a72e2b.war]", :immediately
end

ruby_block "remove-servicebe032c81-c40a-4e13-bb65-59be89a72e2b" do
  block do
  	node.run_list.remove("recipe[servicebe032c81-c40a-4e13-bb65-59be89a72e2b::war]")
  end
  only_if { node.run_list.include?("recipe[servicebe032c81-c40a-4e13-bb65-59be89a72e2b::war]") }
end

ruby_block "remove-deactivate-servicebe032c81-c40a-4e13-bb65-59be89a72e2b" do
  block do
    node.run_list.remove("recipe[deactivate-servicebe032c81-c40a-4e13-bb65-59be89a72e2b]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicebe032c81-c40a-4e13-bb65-59be89a72e2b]") }
end
