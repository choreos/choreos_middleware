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

ruby_block "disable-service5a4f5cc1-5ed1-4db1-9ffa-b8bbb94ba0f4" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service5a4f5cc1-5ed1-4db1-9ffa-b8bbb94ba0f4::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['5a4f5cc1-5ed1-4db1-9ffa-b8bbb94ba0f4']['InstallationDir']}/service-5a4f5cc1-5ed1-4db1-9ffa-b8bbb94ba0f4.jar]", :immediately
end

ruby_block "remove-service5a4f5cc1-5ed1-4db1-9ffa-b8bbb94ba0f4" do
  block do
  	node.run_list.remove("recipe[service5a4f5cc1-5ed1-4db1-9ffa-b8bbb94ba0f4::jar]")
  end
  only_if { node.run_list.include?("recipe[service5a4f5cc1-5ed1-4db1-9ffa-b8bbb94ba0f4::jar]") }
end

ruby_block "remove-deactivate-service5a4f5cc1-5ed1-4db1-9ffa-b8bbb94ba0f4" do
  block do
    node.run_list.remove("recipe[deactivate-service5a4f5cc1-5ed1-4db1-9ffa-b8bbb94ba0f4]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service5a4f5cc1-5ed1-4db1-9ffa-b8bbb94ba0f4]") }
end
