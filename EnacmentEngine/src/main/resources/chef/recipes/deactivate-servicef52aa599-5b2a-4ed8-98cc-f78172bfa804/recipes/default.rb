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

ruby_block "disable-servicef52aa599-5b2a-4ed8-98cc-f78172bfa804" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicef52aa599-5b2a-4ed8-98cc-f78172bfa804::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['f52aa599-5b2a-4ed8-98cc-f78172bfa804']['InstallationDir']}/service-f52aa599-5b2a-4ed8-98cc-f78172bfa804.jar]", :immediately
end

ruby_block "remove-servicef52aa599-5b2a-4ed8-98cc-f78172bfa804" do
  block do
  	node.run_list.remove("recipe[servicef52aa599-5b2a-4ed8-98cc-f78172bfa804::jar]")
  end
  only_if { node.run_list.include?("recipe[servicef52aa599-5b2a-4ed8-98cc-f78172bfa804::jar]") }
end

ruby_block "remove-deactivate-servicef52aa599-5b2a-4ed8-98cc-f78172bfa804" do
  block do
    node.run_list.remove("recipe[deactivate-servicef52aa599-5b2a-4ed8-98cc-f78172bfa804]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicef52aa599-5b2a-4ed8-98cc-f78172bfa804]") }
end
