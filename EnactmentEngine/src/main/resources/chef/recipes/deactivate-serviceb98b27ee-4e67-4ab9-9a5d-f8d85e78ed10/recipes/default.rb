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

#ruby_block "disable-serviceb98b27ee-4e67-4ab9-9a5d-f8d85e78ed10" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[serviceb98b27ee-4e67-4ab9-9a5d-f8d85e78ed10::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['b98b27ee-4e67-4ab9-9a5d-f8d85e78ed10']['InstallationDir']}/service-b98b27ee-4e67-4ab9-9a5d-f8d85e78ed10.jar]", :immediately
#end

ruby_block "remove-serviceb98b27ee-4e67-4ab9-9a5d-f8d85e78ed10" do
  block do
  	node.run_list.remove("recipe[serviceb98b27ee-4e67-4ab9-9a5d-f8d85e78ed10::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceb98b27ee-4e67-4ab9-9a5d-f8d85e78ed10::jar]") }
end

ruby_block "remove-deactivate-serviceb98b27ee-4e67-4ab9-9a5d-f8d85e78ed10" do
  block do
    node.run_list.remove("recipe[deactivate-serviceb98b27ee-4e67-4ab9-9a5d-f8d85e78ed10]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceb98b27ee-4e67-4ab9-9a5d-f8d85e78ed10]") }
end
