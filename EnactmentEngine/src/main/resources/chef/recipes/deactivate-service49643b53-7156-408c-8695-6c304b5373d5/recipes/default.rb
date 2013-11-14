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

ruby_block "disable-service49643b53-7156-408c-8695-6c304b5373d5" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service49643b53-7156-408c-8695-6c304b5373d5::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['49643b53-7156-408c-8695-6c304b5373d5']['InstallationDir']}/service-49643b53-7156-408c-8695-6c304b5373d5.jar]", :immediately
end

ruby_block "remove-service49643b53-7156-408c-8695-6c304b5373d5" do
  block do
  	node.run_list.remove("recipe[service49643b53-7156-408c-8695-6c304b5373d5::jar]")
  end
  only_if { node.run_list.include?("recipe[service49643b53-7156-408c-8695-6c304b5373d5::jar]") }
end

ruby_block "remove-deactivate-service49643b53-7156-408c-8695-6c304b5373d5" do
  block do
    node.run_list.remove("recipe[deactivate-service49643b53-7156-408c-8695-6c304b5373d5]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service49643b53-7156-408c-8695-6c304b5373d5]") }
end
