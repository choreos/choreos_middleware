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

#ruby_block "disable-service76db9ee7-0810-4091-9154-c27763debca3" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service76db9ee7-0810-4091-9154-c27763debca3::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['76db9ee7-0810-4091-9154-c27763debca3']['InstallationDir']}/service-76db9ee7-0810-4091-9154-c27763debca3.jar]", :immediately
#end

ruby_block "remove-service76db9ee7-0810-4091-9154-c27763debca3" do
  block do
  	node.run_list.remove("recipe[service76db9ee7-0810-4091-9154-c27763debca3::jar]")
  end
  only_if { node.run_list.include?("recipe[service76db9ee7-0810-4091-9154-c27763debca3::jar]") }
end

ruby_block "remove-deactivate-service76db9ee7-0810-4091-9154-c27763debca3" do
  block do
    node.run_list.remove("recipe[deactivate-service76db9ee7-0810-4091-9154-c27763debca3]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service76db9ee7-0810-4091-9154-c27763debca3]") }
end
