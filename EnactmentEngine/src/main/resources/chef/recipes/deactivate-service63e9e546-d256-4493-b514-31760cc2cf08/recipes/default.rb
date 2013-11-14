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

#ruby_block "disable-service63e9e546-d256-4493-b514-31760cc2cf08" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service63e9e546-d256-4493-b514-31760cc2cf08::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['63e9e546-d256-4493-b514-31760cc2cf08']['InstallationDir']}/service-63e9e546-d256-4493-b514-31760cc2cf08.jar]", :immediately
#end

ruby_block "remove-service63e9e546-d256-4493-b514-31760cc2cf08" do
  block do
  	node.run_list.remove("recipe[service63e9e546-d256-4493-b514-31760cc2cf08::jar]")
  end
  only_if { node.run_list.include?("recipe[service63e9e546-d256-4493-b514-31760cc2cf08::jar]") }
end

ruby_block "remove-deactivate-service63e9e546-d256-4493-b514-31760cc2cf08" do
  block do
    node.run_list.remove("recipe[deactivate-service63e9e546-d256-4493-b514-31760cc2cf08]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service63e9e546-d256-4493-b514-31760cc2cf08]") }
end
