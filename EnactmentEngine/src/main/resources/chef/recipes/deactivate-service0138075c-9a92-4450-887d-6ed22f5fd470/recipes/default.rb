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

#ruby_block "disable-service0138075c-9a92-4450-887d-6ed22f5fd470" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service0138075c-9a92-4450-887d-6ed22f5fd470::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['0138075c-9a92-4450-887d-6ed22f5fd470']['InstallationDir']}/service-0138075c-9a92-4450-887d-6ed22f5fd470.jar]", :immediately
#end

ruby_block "remove-service0138075c-9a92-4450-887d-6ed22f5fd470" do
  block do
  	node.run_list.remove("recipe[service0138075c-9a92-4450-887d-6ed22f5fd470::jar]")
  end
  only_if { node.run_list.include?("recipe[service0138075c-9a92-4450-887d-6ed22f5fd470::jar]") }
end

ruby_block "remove-deactivate-service0138075c-9a92-4450-887d-6ed22f5fd470" do
  block do
    node.run_list.remove("recipe[deactivate-service0138075c-9a92-4450-887d-6ed22f5fd470]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service0138075c-9a92-4450-887d-6ed22f5fd470]") }
end
