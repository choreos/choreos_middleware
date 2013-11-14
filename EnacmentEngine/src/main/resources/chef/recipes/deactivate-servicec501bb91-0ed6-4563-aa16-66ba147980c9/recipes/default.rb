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

#ruby_block "disable-servicec501bb91-0ed6-4563-aa16-66ba147980c9" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[servicec501bb91-0ed6-4563-aa16-66ba147980c9::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['c501bb91-0ed6-4563-aa16-66ba147980c9']['InstallationDir']}/service-c501bb91-0ed6-4563-aa16-66ba147980c9.jar]", :immediately
#end

ruby_block "remove-servicec501bb91-0ed6-4563-aa16-66ba147980c9" do
  block do
  	node.run_list.remove("recipe[servicec501bb91-0ed6-4563-aa16-66ba147980c9::jar]")
  end
  only_if { node.run_list.include?("recipe[servicec501bb91-0ed6-4563-aa16-66ba147980c9::jar]") }
end

ruby_block "remove-deactivate-servicec501bb91-0ed6-4563-aa16-66ba147980c9" do
  block do
    node.run_list.remove("recipe[deactivate-servicec501bb91-0ed6-4563-aa16-66ba147980c9]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicec501bb91-0ed6-4563-aa16-66ba147980c9]") }
end
