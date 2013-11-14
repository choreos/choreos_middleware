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

ruby_block "disable-service62f300fc-a6c4-47f5-bc5c-899bb7dc9837" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service62f300fc-a6c4-47f5-bc5c-899bb7dc9837::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['62f300fc-a6c4-47f5-bc5c-899bb7dc9837']['InstallationDir']}/service-62f300fc-a6c4-47f5-bc5c-899bb7dc9837.jar]", :immediately
end

ruby_block "remove-service62f300fc-a6c4-47f5-bc5c-899bb7dc9837" do
  block do
  	node.run_list.remove("recipe[service62f300fc-a6c4-47f5-bc5c-899bb7dc9837::jar]")
  end
  only_if { node.run_list.include?("recipe[service62f300fc-a6c4-47f5-bc5c-899bb7dc9837::jar]") }
end

ruby_block "remove-deactivate-service62f300fc-a6c4-47f5-bc5c-899bb7dc9837" do
  block do
    node.run_list.remove("recipe[deactivate-service62f300fc-a6c4-47f5-bc5c-899bb7dc9837]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service62f300fc-a6c4-47f5-bc5c-899bb7dc9837]") }
end
