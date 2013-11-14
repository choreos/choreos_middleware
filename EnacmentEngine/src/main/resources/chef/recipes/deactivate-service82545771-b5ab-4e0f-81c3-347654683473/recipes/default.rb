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

ruby_block "disable-service82545771-b5ab-4e0f-81c3-347654683473" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service82545771-b5ab-4e0f-81c3-347654683473::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['82545771-b5ab-4e0f-81c3-347654683473']['InstallationDir']}/service-82545771-b5ab-4e0f-81c3-347654683473.jar]", :immediately
end

ruby_block "remove-service82545771-b5ab-4e0f-81c3-347654683473" do
  block do
  	node.run_list.remove("recipe[service82545771-b5ab-4e0f-81c3-347654683473::jar]")
  end
  only_if { node.run_list.include?("recipe[service82545771-b5ab-4e0f-81c3-347654683473::jar]") }
end

ruby_block "remove-deactivate-service82545771-b5ab-4e0f-81c3-347654683473" do
  block do
    node.run_list.remove("recipe[deactivate-service82545771-b5ab-4e0f-81c3-347654683473]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service82545771-b5ab-4e0f-81c3-347654683473]") }
end
