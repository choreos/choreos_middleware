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

#ruby_block "disable-service4f2065ff-695b-4929-9a8f-e6285347ebd7" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service4f2065ff-695b-4929-9a8f-e6285347ebd7::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['4f2065ff-695b-4929-9a8f-e6285347ebd7']['InstallationDir']}/service-4f2065ff-695b-4929-9a8f-e6285347ebd7.jar]", :immediately
#end

ruby_block "remove-service4f2065ff-695b-4929-9a8f-e6285347ebd7" do
  block do
  	node.run_list.remove("recipe[service4f2065ff-695b-4929-9a8f-e6285347ebd7::jar]")
  end
  only_if { node.run_list.include?("recipe[service4f2065ff-695b-4929-9a8f-e6285347ebd7::jar]") }
end

ruby_block "remove-deactivate-service4f2065ff-695b-4929-9a8f-e6285347ebd7" do
  block do
    node.run_list.remove("recipe[deactivate-service4f2065ff-695b-4929-9a8f-e6285347ebd7]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service4f2065ff-695b-4929-9a8f-e6285347ebd7]") }
end
