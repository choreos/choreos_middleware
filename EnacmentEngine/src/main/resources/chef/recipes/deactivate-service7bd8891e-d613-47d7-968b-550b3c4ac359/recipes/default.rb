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

ruby_block "disable-service7bd8891e-d613-47d7-968b-550b3c4ac359" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service7bd8891e-d613-47d7-968b-550b3c4ac359::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['7bd8891e-d613-47d7-968b-550b3c4ac359']['InstallationDir']}/service-7bd8891e-d613-47d7-968b-550b3c4ac359.jar]", :immediately
end

ruby_block "remove-service7bd8891e-d613-47d7-968b-550b3c4ac359" do
  block do
  	node.run_list.remove("recipe[service7bd8891e-d613-47d7-968b-550b3c4ac359::jar]")
  end
  only_if { node.run_list.include?("recipe[service7bd8891e-d613-47d7-968b-550b3c4ac359::jar]") }
end

ruby_block "remove-deactivate-service7bd8891e-d613-47d7-968b-550b3c4ac359" do
  block do
    node.run_list.remove("recipe[deactivate-service7bd8891e-d613-47d7-968b-550b3c4ac359]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service7bd8891e-d613-47d7-968b-550b3c4ac359]") }
end
