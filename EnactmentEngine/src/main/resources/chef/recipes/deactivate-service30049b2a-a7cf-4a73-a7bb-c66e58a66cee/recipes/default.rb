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

ruby_block "disable-service30049b2a-a7cf-4a73-a7bb-c66e58a66cee" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service30049b2a-a7cf-4a73-a7bb-c66e58a66cee::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['30049b2a-a7cf-4a73-a7bb-c66e58a66cee']['InstallationDir']}/service-30049b2a-a7cf-4a73-a7bb-c66e58a66cee.jar]", :immediately
end

ruby_block "remove-service30049b2a-a7cf-4a73-a7bb-c66e58a66cee" do
  block do
  	node.run_list.remove("recipe[service30049b2a-a7cf-4a73-a7bb-c66e58a66cee::jar]")
  end
  only_if { node.run_list.include?("recipe[service30049b2a-a7cf-4a73-a7bb-c66e58a66cee::jar]") }
end

ruby_block "remove-deactivate-service30049b2a-a7cf-4a73-a7bb-c66e58a66cee" do
  block do
    node.run_list.remove("recipe[deactivate-service30049b2a-a7cf-4a73-a7bb-c66e58a66cee]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service30049b2a-a7cf-4a73-a7bb-c66e58a66cee]") }
end
