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

ruby_block "disable-serviceca93a6af-b055-484a-b3b0-d532e7e0cb75" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceca93a6af-b055-484a-b3b0-d532e7e0cb75::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['ca93a6af-b055-484a-b3b0-d532e7e0cb75']['InstallationDir']}/service-ca93a6af-b055-484a-b3b0-d532e7e0cb75.jar]", :immediately
end

ruby_block "remove-serviceca93a6af-b055-484a-b3b0-d532e7e0cb75" do
  block do
  	node.run_list.remove("recipe[serviceca93a6af-b055-484a-b3b0-d532e7e0cb75::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceca93a6af-b055-484a-b3b0-d532e7e0cb75::jar]") }
end

ruby_block "remove-deactivate-serviceca93a6af-b055-484a-b3b0-d532e7e0cb75" do
  block do
    node.run_list.remove("recipe[deactivate-serviceca93a6af-b055-484a-b3b0-d532e7e0cb75]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceca93a6af-b055-484a-b3b0-d532e7e0cb75]") }
end
