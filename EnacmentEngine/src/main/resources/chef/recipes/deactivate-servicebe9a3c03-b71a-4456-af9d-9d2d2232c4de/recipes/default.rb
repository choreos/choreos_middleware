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

ruby_block "disable-servicebe9a3c03-b71a-4456-af9d-9d2d2232c4de" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicebe9a3c03-b71a-4456-af9d-9d2d2232c4de::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['be9a3c03-b71a-4456-af9d-9d2d2232c4de']['InstallationDir']}/service-be9a3c03-b71a-4456-af9d-9d2d2232c4de.jar]", :immediately
end

ruby_block "remove-servicebe9a3c03-b71a-4456-af9d-9d2d2232c4de" do
  block do
  	node.run_list.remove("recipe[servicebe9a3c03-b71a-4456-af9d-9d2d2232c4de::jar]")
  end
  only_if { node.run_list.include?("recipe[servicebe9a3c03-b71a-4456-af9d-9d2d2232c4de::jar]") }
end

ruby_block "remove-deactivate-servicebe9a3c03-b71a-4456-af9d-9d2d2232c4de" do
  block do
    node.run_list.remove("recipe[deactivate-servicebe9a3c03-b71a-4456-af9d-9d2d2232c4de]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicebe9a3c03-b71a-4456-af9d-9d2d2232c4de]") }
end
