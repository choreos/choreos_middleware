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

ruby_block "disable-service4202c818-9cc5-41a5-99ce-28e57e00d309" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service4202c818-9cc5-41a5-99ce-28e57e00d309::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['4202c818-9cc5-41a5-99ce-28e57e00d309']['InstallationDir']}/service-4202c818-9cc5-41a5-99ce-28e57e00d309.jar]", :immediately
end

ruby_block "remove-service4202c818-9cc5-41a5-99ce-28e57e00d309" do
  block do
  	node.run_list.remove("recipe[service4202c818-9cc5-41a5-99ce-28e57e00d309::jar]")
  end
  only_if { node.run_list.include?("recipe[service4202c818-9cc5-41a5-99ce-28e57e00d309::jar]") }
end

ruby_block "remove-deactivate-service4202c818-9cc5-41a5-99ce-28e57e00d309" do
  block do
    node.run_list.remove("recipe[deactivate-service4202c818-9cc5-41a5-99ce-28e57e00d309]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service4202c818-9cc5-41a5-99ce-28e57e00d309]") }
end
