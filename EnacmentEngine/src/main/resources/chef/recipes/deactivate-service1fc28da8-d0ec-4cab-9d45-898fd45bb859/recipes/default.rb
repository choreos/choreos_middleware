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

ruby_block "remove-service1fc28da8-d0ec-4cab-9d45-898fd45bb859" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service1fc28da8-d0ec-4cab-9d45-898fd45bb859::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/1fc28da8-d0ec-4cab-9d45-898fd45bb859.war]", :immediately
end

ruby_block "remove-service1fc28da8-d0ec-4cab-9d45-898fd45bb859" do
  block do
  	node.run_list.remove("recipe[service1fc28da8-d0ec-4cab-9d45-898fd45bb859::war]")
  end
  only_if { node.run_list.include?("recipe[service1fc28da8-d0ec-4cab-9d45-898fd45bb859::war]") }
end

ruby_block "remove-deactivate-service1fc28da8-d0ec-4cab-9d45-898fd45bb859" do
  block do
    node.run_list.remove("recipe[deactivate-service1fc28da8-d0ec-4cab-9d45-898fd45bb859]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service1fc28da8-d0ec-4cab-9d45-898fd45bb859]") }
end
