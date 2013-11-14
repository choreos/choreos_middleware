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

#ruby_block "disable-service40805be9-bd11-45d2-9d2d-2b1b9dc48e93" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service40805be9-bd11-45d2-9d2d-2b1b9dc48e93::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['40805be9-bd11-45d2-9d2d-2b1b9dc48e93']['InstallationDir']}/service-40805be9-bd11-45d2-9d2d-2b1b9dc48e93.jar]", :immediately
#end

ruby_block "remove-service40805be9-bd11-45d2-9d2d-2b1b9dc48e93" do
  block do
  	node.run_list.remove("recipe[service40805be9-bd11-45d2-9d2d-2b1b9dc48e93::jar]")
  end
  only_if { node.run_list.include?("recipe[service40805be9-bd11-45d2-9d2d-2b1b9dc48e93::jar]") }
end

ruby_block "remove-deactivate-service40805be9-bd11-45d2-9d2d-2b1b9dc48e93" do
  block do
    node.run_list.remove("recipe[deactivate-service40805be9-bd11-45d2-9d2d-2b1b9dc48e93]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service40805be9-bd11-45d2-9d2d-2b1b9dc48e93]") }
end
