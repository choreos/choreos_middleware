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

ruby_block "disable-service74a0f1c2-e8bf-4249-828b-90dbdac3a9d0" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service74a0f1c2-e8bf-4249-828b-90dbdac3a9d0::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['74a0f1c2-e8bf-4249-828b-90dbdac3a9d0']['InstallationDir']}/service-74a0f1c2-e8bf-4249-828b-90dbdac3a9d0.jar]", :immediately
end

ruby_block "remove-service74a0f1c2-e8bf-4249-828b-90dbdac3a9d0" do
  block do
  	node.run_list.remove("recipe[service74a0f1c2-e8bf-4249-828b-90dbdac3a9d0::jar]")
  end
  only_if { node.run_list.include?("recipe[service74a0f1c2-e8bf-4249-828b-90dbdac3a9d0::jar]") }
end

ruby_block "remove-deactivate-service74a0f1c2-e8bf-4249-828b-90dbdac3a9d0" do
  block do
    node.run_list.remove("recipe[deactivate-service74a0f1c2-e8bf-4249-828b-90dbdac3a9d0]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service74a0f1c2-e8bf-4249-828b-90dbdac3a9d0]") }
end
