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

#ruby_block "disable-service002d4353-b4a1-4baa-969d-b701aa578591" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service002d4353-b4a1-4baa-969d-b701aa578591::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['002d4353-b4a1-4baa-969d-b701aa578591']['InstallationDir']}/service-002d4353-b4a1-4baa-969d-b701aa578591.jar]", :immediately
#end

ruby_block "remove-service002d4353-b4a1-4baa-969d-b701aa578591" do
  block do
  	node.run_list.remove("recipe[service002d4353-b4a1-4baa-969d-b701aa578591::jar]")
  end
  only_if { node.run_list.include?("recipe[service002d4353-b4a1-4baa-969d-b701aa578591::jar]") }
end

ruby_block "remove-deactivate-service002d4353-b4a1-4baa-969d-b701aa578591" do
  block do
    node.run_list.remove("recipe[deactivate-service002d4353-b4a1-4baa-969d-b701aa578591]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service002d4353-b4a1-4baa-969d-b701aa578591]") }
end
