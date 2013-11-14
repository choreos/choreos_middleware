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

#ruby_block "disable-service59601051-1f4e-42b0-b0f5-a262dc68164c" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service59601051-1f4e-42b0-b0f5-a262dc68164c::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['59601051-1f4e-42b0-b0f5-a262dc68164c']['InstallationDir']}/service-59601051-1f4e-42b0-b0f5-a262dc68164c.jar]", :immediately
#end

ruby_block "remove-service59601051-1f4e-42b0-b0f5-a262dc68164c" do
  block do
  	node.run_list.remove("recipe[service59601051-1f4e-42b0-b0f5-a262dc68164c::jar]")
  end
  only_if { node.run_list.include?("recipe[service59601051-1f4e-42b0-b0f5-a262dc68164c::jar]") }
end

ruby_block "remove-deactivate-service59601051-1f4e-42b0-b0f5-a262dc68164c" do
  block do
    node.run_list.remove("recipe[deactivate-service59601051-1f4e-42b0-b0f5-a262dc68164c]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service59601051-1f4e-42b0-b0f5-a262dc68164c]") }
end
