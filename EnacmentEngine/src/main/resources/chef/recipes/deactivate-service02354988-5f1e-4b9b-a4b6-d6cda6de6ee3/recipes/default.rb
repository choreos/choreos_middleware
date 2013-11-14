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

ruby_block "disable-service02354988-5f1e-4b9b-a4b6-d6cda6de6ee3" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service02354988-5f1e-4b9b-a4b6-d6cda6de6ee3::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['02354988-5f1e-4b9b-a4b6-d6cda6de6ee3']['InstallationDir']}/service-02354988-5f1e-4b9b-a4b6-d6cda6de6ee3.jar]", :immediately
end

ruby_block "remove-service02354988-5f1e-4b9b-a4b6-d6cda6de6ee3" do
  block do
  	node.run_list.remove("recipe[service02354988-5f1e-4b9b-a4b6-d6cda6de6ee3::jar]")
  end
  only_if { node.run_list.include?("recipe[service02354988-5f1e-4b9b-a4b6-d6cda6de6ee3::jar]") }
end

ruby_block "remove-deactivate-service02354988-5f1e-4b9b-a4b6-d6cda6de6ee3" do
  block do
    node.run_list.remove("recipe[deactivate-service02354988-5f1e-4b9b-a4b6-d6cda6de6ee3]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service02354988-5f1e-4b9b-a4b6-d6cda6de6ee3]") }
end
