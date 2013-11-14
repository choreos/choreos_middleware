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

#ruby_block "disable-serviceb9f4d913-4243-4a6a-8377-e8e1aa675031" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[serviceb9f4d913-4243-4a6a-8377-e8e1aa675031::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['b9f4d913-4243-4a6a-8377-e8e1aa675031']['InstallationDir']}/service-b9f4d913-4243-4a6a-8377-e8e1aa675031.jar]", :immediately
#end

ruby_block "remove-serviceb9f4d913-4243-4a6a-8377-e8e1aa675031" do
  block do
  	node.run_list.remove("recipe[serviceb9f4d913-4243-4a6a-8377-e8e1aa675031::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceb9f4d913-4243-4a6a-8377-e8e1aa675031::jar]") }
end

ruby_block "remove-deactivate-serviceb9f4d913-4243-4a6a-8377-e8e1aa675031" do
  block do
    node.run_list.remove("recipe[deactivate-serviceb9f4d913-4243-4a6a-8377-e8e1aa675031]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceb9f4d913-4243-4a6a-8377-e8e1aa675031]") }
end
