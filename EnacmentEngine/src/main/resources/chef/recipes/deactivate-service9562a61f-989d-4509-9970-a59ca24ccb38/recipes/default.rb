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

#ruby_block "disable-service9562a61f-989d-4509-9970-a59ca24ccb38" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service9562a61f-989d-4509-9970-a59ca24ccb38::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['9562a61f-989d-4509-9970-a59ca24ccb38']['InstallationDir']}/service-9562a61f-989d-4509-9970-a59ca24ccb38.jar]", :immediately
#end

ruby_block "remove-service9562a61f-989d-4509-9970-a59ca24ccb38" do
  block do
  	node.run_list.remove("recipe[service9562a61f-989d-4509-9970-a59ca24ccb38::jar]")
  end
  only_if { node.run_list.include?("recipe[service9562a61f-989d-4509-9970-a59ca24ccb38::jar]") }
end

ruby_block "remove-deactivate-service9562a61f-989d-4509-9970-a59ca24ccb38" do
  block do
    node.run_list.remove("recipe[deactivate-service9562a61f-989d-4509-9970-a59ca24ccb38]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service9562a61f-989d-4509-9970-a59ca24ccb38]") }
end
