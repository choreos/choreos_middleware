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

ruby_block "remove-service62971934-69e0-453b-9dcb-578163b1220e" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service62971934-69e0-453b-9dcb-578163b1220e::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/62971934-69e0-453b-9dcb-578163b1220e.war]", :immediately
end

ruby_block "remove-service62971934-69e0-453b-9dcb-578163b1220e" do
  block do
  	node.run_list.remove("recipe[service62971934-69e0-453b-9dcb-578163b1220e::war]")
  end
  only_if { node.run_list.include?("recipe[service62971934-69e0-453b-9dcb-578163b1220e::war]") }
end

ruby_block "remove-deactivate-service62971934-69e0-453b-9dcb-578163b1220e" do
  block do
    node.run_list.remove("recipe[deactivate-service62971934-69e0-453b-9dcb-578163b1220e]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service62971934-69e0-453b-9dcb-578163b1220e]") }
end
