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

#ruby_block "disable-service32946118-2cc4-4280-9ca3-73314cb1eff1" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service32946118-2cc4-4280-9ca3-73314cb1eff1::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['32946118-2cc4-4280-9ca3-73314cb1eff1']['InstallationDir']}/service-32946118-2cc4-4280-9ca3-73314cb1eff1.jar]", :immediately
#end

ruby_block "remove-service32946118-2cc4-4280-9ca3-73314cb1eff1" do
  block do
  	node.run_list.remove("recipe[service32946118-2cc4-4280-9ca3-73314cb1eff1::jar]")
  end
  only_if { node.run_list.include?("recipe[service32946118-2cc4-4280-9ca3-73314cb1eff1::jar]") }
end

ruby_block "remove-deactivate-service32946118-2cc4-4280-9ca3-73314cb1eff1" do
  block do
    node.run_list.remove("recipe[deactivate-service32946118-2cc4-4280-9ca3-73314cb1eff1]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service32946118-2cc4-4280-9ca3-73314cb1eff1]") }
end
