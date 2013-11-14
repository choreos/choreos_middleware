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

ruby_block "disable-service1d74dba6-529f-4009-a177-e88a4d47afc2" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service1d74dba6-529f-4009-a177-e88a4d47afc2::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['1d74dba6-529f-4009-a177-e88a4d47afc2']['InstallationDir']}/service-1d74dba6-529f-4009-a177-e88a4d47afc2.jar]", :immediately
end

ruby_block "remove-service1d74dba6-529f-4009-a177-e88a4d47afc2" do
  block do
  	node.run_list.remove("recipe[service1d74dba6-529f-4009-a177-e88a4d47afc2::jar]")
  end
  only_if { node.run_list.include?("recipe[service1d74dba6-529f-4009-a177-e88a4d47afc2::jar]") }
end

ruby_block "remove-deactivate-service1d74dba6-529f-4009-a177-e88a4d47afc2" do
  block do
    node.run_list.remove("recipe[deactivate-service1d74dba6-529f-4009-a177-e88a4d47afc2]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service1d74dba6-529f-4009-a177-e88a4d47afc2]") }
end
