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

ruby_block "disable-service51ef2fcd-40d4-456c-b82d-8beeab10f1df" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service51ef2fcd-40d4-456c-b82d-8beeab10f1df::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['51ef2fcd-40d4-456c-b82d-8beeab10f1df']['InstallationDir']}/service-51ef2fcd-40d4-456c-b82d-8beeab10f1df.jar]", :immediately
end

ruby_block "remove-service51ef2fcd-40d4-456c-b82d-8beeab10f1df" do
  block do
  	node.run_list.remove("recipe[service51ef2fcd-40d4-456c-b82d-8beeab10f1df::jar]")
  end
  only_if { node.run_list.include?("recipe[service51ef2fcd-40d4-456c-b82d-8beeab10f1df::jar]") }
end

ruby_block "remove-deactivate-service51ef2fcd-40d4-456c-b82d-8beeab10f1df" do
  block do
    node.run_list.remove("recipe[deactivate-service51ef2fcd-40d4-456c-b82d-8beeab10f1df]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service51ef2fcd-40d4-456c-b82d-8beeab10f1df]") }
end
