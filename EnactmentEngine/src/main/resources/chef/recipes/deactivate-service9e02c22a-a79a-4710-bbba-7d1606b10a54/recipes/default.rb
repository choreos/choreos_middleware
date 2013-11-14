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

#ruby_block "disable-service9e02c22a-a79a-4710-bbba-7d1606b10a54" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service9e02c22a-a79a-4710-bbba-7d1606b10a54::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['9e02c22a-a79a-4710-bbba-7d1606b10a54']['InstallationDir']}/service-9e02c22a-a79a-4710-bbba-7d1606b10a54.jar]", :immediately
#end

ruby_block "remove-service9e02c22a-a79a-4710-bbba-7d1606b10a54" do
  block do
  	node.run_list.remove("recipe[service9e02c22a-a79a-4710-bbba-7d1606b10a54::jar]")
  end
  only_if { node.run_list.include?("recipe[service9e02c22a-a79a-4710-bbba-7d1606b10a54::jar]") }
end

ruby_block "remove-deactivate-service9e02c22a-a79a-4710-bbba-7d1606b10a54" do
  block do
    node.run_list.remove("recipe[deactivate-service9e02c22a-a79a-4710-bbba-7d1606b10a54]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service9e02c22a-a79a-4710-bbba-7d1606b10a54]") }
end
