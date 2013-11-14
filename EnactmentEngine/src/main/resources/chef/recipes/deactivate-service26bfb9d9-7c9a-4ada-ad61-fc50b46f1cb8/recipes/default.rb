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

ruby_block "disable-service26bfb9d9-7c9a-4ada-ad61-fc50b46f1cb8" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service26bfb9d9-7c9a-4ada-ad61-fc50b46f1cb8::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['26bfb9d9-7c9a-4ada-ad61-fc50b46f1cb8']['InstallationDir']}/service-26bfb9d9-7c9a-4ada-ad61-fc50b46f1cb8.jar]", :immediately
end

ruby_block "remove-service26bfb9d9-7c9a-4ada-ad61-fc50b46f1cb8" do
  block do
  	node.run_list.remove("recipe[service26bfb9d9-7c9a-4ada-ad61-fc50b46f1cb8::jar]")
  end
  only_if { node.run_list.include?("recipe[service26bfb9d9-7c9a-4ada-ad61-fc50b46f1cb8::jar]") }
end

ruby_block "remove-deactivate-service26bfb9d9-7c9a-4ada-ad61-fc50b46f1cb8" do
  block do
    node.run_list.remove("recipe[deactivate-service26bfb9d9-7c9a-4ada-ad61-fc50b46f1cb8]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service26bfb9d9-7c9a-4ada-ad61-fc50b46f1cb8]") }
end
