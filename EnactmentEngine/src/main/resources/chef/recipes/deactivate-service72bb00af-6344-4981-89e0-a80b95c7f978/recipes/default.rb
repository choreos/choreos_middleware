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

#ruby_block "disable-service72bb00af-6344-4981-89e0-a80b95c7f978" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service72bb00af-6344-4981-89e0-a80b95c7f978::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['72bb00af-6344-4981-89e0-a80b95c7f978']['InstallationDir']}/service-72bb00af-6344-4981-89e0-a80b95c7f978.jar]", :immediately
#end

ruby_block "remove-service72bb00af-6344-4981-89e0-a80b95c7f978" do
  block do
  	node.run_list.remove("recipe[service72bb00af-6344-4981-89e0-a80b95c7f978::jar]")
  end
  only_if { node.run_list.include?("recipe[service72bb00af-6344-4981-89e0-a80b95c7f978::jar]") }
end

ruby_block "remove-deactivate-service72bb00af-6344-4981-89e0-a80b95c7f978" do
  block do
    node.run_list.remove("recipe[deactivate-service72bb00af-6344-4981-89e0-a80b95c7f978]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service72bb00af-6344-4981-89e0-a80b95c7f978]") }
end
