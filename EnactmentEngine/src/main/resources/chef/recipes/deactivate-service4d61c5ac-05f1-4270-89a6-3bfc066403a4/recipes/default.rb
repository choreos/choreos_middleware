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

ruby_block "disable-service4d61c5ac-05f1-4270-89a6-3bfc066403a4" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service4d61c5ac-05f1-4270-89a6-3bfc066403a4::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['4d61c5ac-05f1-4270-89a6-3bfc066403a4']['InstallationDir']}/service-4d61c5ac-05f1-4270-89a6-3bfc066403a4.jar]", :immediately
end

ruby_block "remove-service4d61c5ac-05f1-4270-89a6-3bfc066403a4" do
  block do
  	node.run_list.remove("recipe[service4d61c5ac-05f1-4270-89a6-3bfc066403a4::jar]")
  end
  only_if { node.run_list.include?("recipe[service4d61c5ac-05f1-4270-89a6-3bfc066403a4::jar]") }
end

ruby_block "remove-deactivate-service4d61c5ac-05f1-4270-89a6-3bfc066403a4" do
  block do
    node.run_list.remove("recipe[deactivate-service4d61c5ac-05f1-4270-89a6-3bfc066403a4]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service4d61c5ac-05f1-4270-89a6-3bfc066403a4]") }
end
