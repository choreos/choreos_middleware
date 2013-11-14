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

ruby_block "remove-servicee9ee370d-fcf0-4428-82e4-e429f5b73d12" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicee9ee370d-fcf0-4428-82e4-e429f5b73d12::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/e9ee370d-fcf0-4428-82e4-e429f5b73d12.war]", :immediately
end

ruby_block "remove-servicee9ee370d-fcf0-4428-82e4-e429f5b73d12" do
  block do
  	node.run_list.remove("recipe[servicee9ee370d-fcf0-4428-82e4-e429f5b73d12::war]")
  end
  only_if { node.run_list.include?("recipe[servicee9ee370d-fcf0-4428-82e4-e429f5b73d12::war]") }
end

ruby_block "remove-deactivate-servicee9ee370d-fcf0-4428-82e4-e429f5b73d12" do
  block do
    node.run_list.remove("recipe[deactivate-servicee9ee370d-fcf0-4428-82e4-e429f5b73d12]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicee9ee370d-fcf0-4428-82e4-e429f5b73d12]") }
end
