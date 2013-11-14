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

ruby_block "disable-serviceceb11533-4a66-499a-824b-669f856500c8" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceceb11533-4a66-499a-824b-669f856500c8::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['ceb11533-4a66-499a-824b-669f856500c8']['InstallationDir']}/service-ceb11533-4a66-499a-824b-669f856500c8.jar]", :immediately
end

ruby_block "remove-serviceceb11533-4a66-499a-824b-669f856500c8" do
  block do
  	node.run_list.remove("recipe[serviceceb11533-4a66-499a-824b-669f856500c8::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceceb11533-4a66-499a-824b-669f856500c8::jar]") }
end

ruby_block "remove-deactivate-serviceceb11533-4a66-499a-824b-669f856500c8" do
  block do
    node.run_list.remove("recipe[deactivate-serviceceb11533-4a66-499a-824b-669f856500c8]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceceb11533-4a66-499a-824b-669f856500c8]") }
end
