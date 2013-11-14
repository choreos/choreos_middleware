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

ruby_block "disable-serviceff641649-5bd4-4a9d-ab6e-60a344c1ffa9" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceff641649-5bd4-4a9d-ab6e-60a344c1ffa9::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['ff641649-5bd4-4a9d-ab6e-60a344c1ffa9']['InstallationDir']}/service-ff641649-5bd4-4a9d-ab6e-60a344c1ffa9.jar]", :immediately
end

ruby_block "remove-serviceff641649-5bd4-4a9d-ab6e-60a344c1ffa9" do
  block do
  	node.run_list.remove("recipe[serviceff641649-5bd4-4a9d-ab6e-60a344c1ffa9::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceff641649-5bd4-4a9d-ab6e-60a344c1ffa9::jar]") }
end

ruby_block "remove-deactivate-serviceff641649-5bd4-4a9d-ab6e-60a344c1ffa9" do
  block do
    node.run_list.remove("recipe[deactivate-serviceff641649-5bd4-4a9d-ab6e-60a344c1ffa9]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceff641649-5bd4-4a9d-ab6e-60a344c1ffa9]") }
end
