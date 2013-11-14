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

#ruby_block "disable-servicef2b7636b-4b8e-4653-80b6-c284f5db3cba" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[servicef2b7636b-4b8e-4653-80b6-c284f5db3cba::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['f2b7636b-4b8e-4653-80b6-c284f5db3cba']['InstallationDir']}/service-f2b7636b-4b8e-4653-80b6-c284f5db3cba.jar]", :immediately
#end

ruby_block "remove-servicef2b7636b-4b8e-4653-80b6-c284f5db3cba" do
  block do
  	node.run_list.remove("recipe[servicef2b7636b-4b8e-4653-80b6-c284f5db3cba::jar]")
  end
  only_if { node.run_list.include?("recipe[servicef2b7636b-4b8e-4653-80b6-c284f5db3cba::jar]") }
end

ruby_block "remove-deactivate-servicef2b7636b-4b8e-4653-80b6-c284f5db3cba" do
  block do
    node.run_list.remove("recipe[deactivate-servicef2b7636b-4b8e-4653-80b6-c284f5db3cba]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicef2b7636b-4b8e-4653-80b6-c284f5db3cba]") }
end
