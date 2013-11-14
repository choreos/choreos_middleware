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

#ruby_block "disable-service2122d5d0-c730-4c32-a2e8-89316b138d1d" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service2122d5d0-c730-4c32-a2e8-89316b138d1d::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['2122d5d0-c730-4c32-a2e8-89316b138d1d']['InstallationDir']}/service-2122d5d0-c730-4c32-a2e8-89316b138d1d.jar]", :immediately
#end

ruby_block "remove-service2122d5d0-c730-4c32-a2e8-89316b138d1d" do
  block do
  	node.run_list.remove("recipe[service2122d5d0-c730-4c32-a2e8-89316b138d1d::jar]")
  end
  only_if { node.run_list.include?("recipe[service2122d5d0-c730-4c32-a2e8-89316b138d1d::jar]") }
end

ruby_block "remove-deactivate-service2122d5d0-c730-4c32-a2e8-89316b138d1d" do
  block do
    node.run_list.remove("recipe[deactivate-service2122d5d0-c730-4c32-a2e8-89316b138d1d]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service2122d5d0-c730-4c32-a2e8-89316b138d1d]") }
end
