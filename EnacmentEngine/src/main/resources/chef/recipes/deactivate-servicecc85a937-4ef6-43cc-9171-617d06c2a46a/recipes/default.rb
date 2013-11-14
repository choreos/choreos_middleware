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

ruby_block "disable-servicecc85a937-4ef6-43cc-9171-617d06c2a46a" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicecc85a937-4ef6-43cc-9171-617d06c2a46a::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['cc85a937-4ef6-43cc-9171-617d06c2a46a']['InstallationDir']}/service-cc85a937-4ef6-43cc-9171-617d06c2a46a.jar]", :immediately
end

ruby_block "remove-servicecc85a937-4ef6-43cc-9171-617d06c2a46a" do
  block do
  	node.run_list.remove("recipe[servicecc85a937-4ef6-43cc-9171-617d06c2a46a::jar]")
  end
  only_if { node.run_list.include?("recipe[servicecc85a937-4ef6-43cc-9171-617d06c2a46a::jar]") }
end

ruby_block "remove-deactivate-servicecc85a937-4ef6-43cc-9171-617d06c2a46a" do
  block do
    node.run_list.remove("recipe[deactivate-servicecc85a937-4ef6-43cc-9171-617d06c2a46a]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicecc85a937-4ef6-43cc-9171-617d06c2a46a]") }
end
