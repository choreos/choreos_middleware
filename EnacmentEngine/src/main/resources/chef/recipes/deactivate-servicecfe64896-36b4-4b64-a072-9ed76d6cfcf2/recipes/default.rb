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

ruby_block "disable-servicecfe64896-36b4-4b64-a072-9ed76d6cfcf2" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicecfe64896-36b4-4b64-a072-9ed76d6cfcf2::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['cfe64896-36b4-4b64-a072-9ed76d6cfcf2']['InstallationDir']}/service-cfe64896-36b4-4b64-a072-9ed76d6cfcf2.jar]", :immediately
end

ruby_block "remove-servicecfe64896-36b4-4b64-a072-9ed76d6cfcf2" do
  block do
  	node.run_list.remove("recipe[servicecfe64896-36b4-4b64-a072-9ed76d6cfcf2::jar]")
  end
  only_if { node.run_list.include?("recipe[servicecfe64896-36b4-4b64-a072-9ed76d6cfcf2::jar]") }
end

ruby_block "remove-deactivate-servicecfe64896-36b4-4b64-a072-9ed76d6cfcf2" do
  block do
    node.run_list.remove("recipe[deactivate-servicecfe64896-36b4-4b64-a072-9ed76d6cfcf2]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicecfe64896-36b4-4b64-a072-9ed76d6cfcf2]") }
end
