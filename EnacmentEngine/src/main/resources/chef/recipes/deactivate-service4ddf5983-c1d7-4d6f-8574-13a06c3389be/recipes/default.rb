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

ruby_block "disable-service4ddf5983-c1d7-4d6f-8574-13a06c3389be" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service4ddf5983-c1d7-4d6f-8574-13a06c3389be::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['4ddf5983-c1d7-4d6f-8574-13a06c3389be']['InstallationDir']}/service-4ddf5983-c1d7-4d6f-8574-13a06c3389be.jar]", :immediately
end

ruby_block "remove-service4ddf5983-c1d7-4d6f-8574-13a06c3389be" do
  block do
  	node.run_list.remove("recipe[service4ddf5983-c1d7-4d6f-8574-13a06c3389be::jar]")
  end
  only_if { node.run_list.include?("recipe[service4ddf5983-c1d7-4d6f-8574-13a06c3389be::jar]") }
end

ruby_block "remove-deactivate-service4ddf5983-c1d7-4d6f-8574-13a06c3389be" do
  block do
    node.run_list.remove("recipe[deactivate-service4ddf5983-c1d7-4d6f-8574-13a06c3389be]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service4ddf5983-c1d7-4d6f-8574-13a06c3389be]") }
end
