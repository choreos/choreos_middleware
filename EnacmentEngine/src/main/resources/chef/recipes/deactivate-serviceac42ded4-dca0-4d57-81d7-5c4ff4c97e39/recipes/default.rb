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

#ruby_block "disable-serviceac42ded4-dca0-4d57-81d7-5c4ff4c97e39" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[serviceac42ded4-dca0-4d57-81d7-5c4ff4c97e39::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['ac42ded4-dca0-4d57-81d7-5c4ff4c97e39']['InstallationDir']}/service-ac42ded4-dca0-4d57-81d7-5c4ff4c97e39.jar]", :immediately
#end

ruby_block "remove-serviceac42ded4-dca0-4d57-81d7-5c4ff4c97e39" do
  block do
  	node.run_list.remove("recipe[serviceac42ded4-dca0-4d57-81d7-5c4ff4c97e39::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceac42ded4-dca0-4d57-81d7-5c4ff4c97e39::jar]") }
end

ruby_block "remove-deactivate-serviceac42ded4-dca0-4d57-81d7-5c4ff4c97e39" do
  block do
    node.run_list.remove("recipe[deactivate-serviceac42ded4-dca0-4d57-81d7-5c4ff4c97e39]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceac42ded4-dca0-4d57-81d7-5c4ff4c97e39]") }
end
