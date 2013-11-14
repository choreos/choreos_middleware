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

#ruby_block "disable-serviceec807701-6356-4c68-bd3c-244f1046fe5d" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[serviceec807701-6356-4c68-bd3c-244f1046fe5d::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['ec807701-6356-4c68-bd3c-244f1046fe5d']['InstallationDir']}/service-ec807701-6356-4c68-bd3c-244f1046fe5d.jar]", :immediately
#end

ruby_block "remove-serviceec807701-6356-4c68-bd3c-244f1046fe5d" do
  block do
  	node.run_list.remove("recipe[serviceec807701-6356-4c68-bd3c-244f1046fe5d::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceec807701-6356-4c68-bd3c-244f1046fe5d::jar]") }
end

ruby_block "remove-deactivate-serviceec807701-6356-4c68-bd3c-244f1046fe5d" do
  block do
    node.run_list.remove("recipe[deactivate-serviceec807701-6356-4c68-bd3c-244f1046fe5d]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceec807701-6356-4c68-bd3c-244f1046fe5d]") }
end
