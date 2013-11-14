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

ruby_block "remove-service4de0bc0d-5f47-445f-94bb-651f9c7a94ff" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service4de0bc0d-5f47-445f-94bb-651f9c7a94ff::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/4de0bc0d-5f47-445f-94bb-651f9c7a94ff.war]", :immediately
end

ruby_block "remove-service4de0bc0d-5f47-445f-94bb-651f9c7a94ff" do
  block do
  	node.run_list.remove("recipe[service4de0bc0d-5f47-445f-94bb-651f9c7a94ff::war]")
  end
  only_if { node.run_list.include?("recipe[service4de0bc0d-5f47-445f-94bb-651f9c7a94ff::war]") }
end

ruby_block "remove-deactivate-service4de0bc0d-5f47-445f-94bb-651f9c7a94ff" do
  block do
    node.run_list.remove("recipe[deactivate-service4de0bc0d-5f47-445f-94bb-651f9c7a94ff]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service4de0bc0d-5f47-445f-94bb-651f9c7a94ff]") }
end
