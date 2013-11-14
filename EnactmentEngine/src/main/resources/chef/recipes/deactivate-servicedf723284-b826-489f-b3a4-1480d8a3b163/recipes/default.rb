#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#                  #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                  #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#                  #
##########################################################################

ruby_block "disable-servicedf723284-b826-489f-b3a4-1480d8a3b163" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicedf723284-b826-489f-b3a4-1480d8a3b163::jar]") }
  notifies :stop, "service[service_df723284-b826-489f-b3a4-1480d8a3b163_jar]", :immediately
end

ruby_block "remove-servicedf723284-b826-489f-b3a4-1480d8a3b163" do
  block do
  	node.run_list.remove("recipe[servicedf723284-b826-489f-b3a4-1480d8a3b163::jar]")
  end
  only_if { node.run_list.include?("recipe[servicedf723284-b826-489f-b3a4-1480d8a3b163::jar]") }
end

ruby_block "remove-deactivate-servicedf723284-b826-489f-b3a4-1480d8a3b163" do
  block do
    node.run_list.remove("recipe[deactivate-servicedf723284-b826-489f-b3a4-1480d8a3b163]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicedf723284-b826-489f-b3a4-1480d8a3b163]") }
end
