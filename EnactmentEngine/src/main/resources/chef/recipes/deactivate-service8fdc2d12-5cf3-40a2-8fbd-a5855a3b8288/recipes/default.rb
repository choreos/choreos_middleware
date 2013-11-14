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

ruby_block "disable-service8fdc2d12-5cf3-40a2-8fbd-a5855a3b8288" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service8fdc2d12-5cf3-40a2-8fbd-a5855a3b8288::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['8fdc2d12-5cf3-40a2-8fbd-a5855a3b8288']['InstallationDir']}/service-8fdc2d12-5cf3-40a2-8fbd-a5855a3b8288.jar]", :immediately
end

ruby_block "remove-service8fdc2d12-5cf3-40a2-8fbd-a5855a3b8288" do
  block do
  	node.run_list.remove("recipe[service8fdc2d12-5cf3-40a2-8fbd-a5855a3b8288::jar]")
  end
  only_if { node.run_list.include?("recipe[service8fdc2d12-5cf3-40a2-8fbd-a5855a3b8288::jar]") }
end

ruby_block "remove-deactivate-service8fdc2d12-5cf3-40a2-8fbd-a5855a3b8288" do
  block do
    node.run_list.remove("recipe[deactivate-service8fdc2d12-5cf3-40a2-8fbd-a5855a3b8288]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service8fdc2d12-5cf3-40a2-8fbd-a5855a3b8288]") }
end
