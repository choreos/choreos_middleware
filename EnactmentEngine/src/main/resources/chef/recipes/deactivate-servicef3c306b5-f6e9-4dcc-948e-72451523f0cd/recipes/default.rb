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

#ruby_block "disable-servicef3c306b5-f6e9-4dcc-948e-72451523f0cd" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[servicef3c306b5-f6e9-4dcc-948e-72451523f0cd::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['f3c306b5-f6e9-4dcc-948e-72451523f0cd']['InstallationDir']}/service-f3c306b5-f6e9-4dcc-948e-72451523f0cd.jar]", :immediately
#end

ruby_block "remove-servicef3c306b5-f6e9-4dcc-948e-72451523f0cd" do
  block do
  	node.run_list.remove("recipe[servicef3c306b5-f6e9-4dcc-948e-72451523f0cd::jar]")
  end
  only_if { node.run_list.include?("recipe[servicef3c306b5-f6e9-4dcc-948e-72451523f0cd::jar]") }
end

ruby_block "remove-deactivate-servicef3c306b5-f6e9-4dcc-948e-72451523f0cd" do
  block do
    node.run_list.remove("recipe[deactivate-servicef3c306b5-f6e9-4dcc-948e-72451523f0cd]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicef3c306b5-f6e9-4dcc-948e-72451523f0cd]") }
end
