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

ruby_block "disable-servicee3a13424-be57-4237-a007-f9c231429638" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicee3a13424-be57-4237-a007-f9c231429638::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['e3a13424-be57-4237-a007-f9c231429638']['InstallationDir']}/service-e3a13424-be57-4237-a007-f9c231429638.jar]", :immediately
end

ruby_block "remove-servicee3a13424-be57-4237-a007-f9c231429638" do
  block do
  	node.run_list.remove("recipe[servicee3a13424-be57-4237-a007-f9c231429638::jar]")
  end
  only_if { node.run_list.include?("recipe[servicee3a13424-be57-4237-a007-f9c231429638::jar]") }
end

ruby_block "remove-deactivate-servicee3a13424-be57-4237-a007-f9c231429638" do
  block do
    node.run_list.remove("recipe[deactivate-servicee3a13424-be57-4237-a007-f9c231429638]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicee3a13424-be57-4237-a007-f9c231429638]") }
end
