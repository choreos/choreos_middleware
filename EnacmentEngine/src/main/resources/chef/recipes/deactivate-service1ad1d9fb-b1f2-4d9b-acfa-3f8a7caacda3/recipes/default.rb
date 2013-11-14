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

#ruby_block "disable-service1ad1d9fb-b1f2-4d9b-acfa-3f8a7caacda3" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service1ad1d9fb-b1f2-4d9b-acfa-3f8a7caacda3::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['1ad1d9fb-b1f2-4d9b-acfa-3f8a7caacda3']['InstallationDir']}/service-1ad1d9fb-b1f2-4d9b-acfa-3f8a7caacda3.jar]", :immediately
#end

ruby_block "remove-service1ad1d9fb-b1f2-4d9b-acfa-3f8a7caacda3" do
  block do
  	node.run_list.remove("recipe[service1ad1d9fb-b1f2-4d9b-acfa-3f8a7caacda3::jar]")
  end
  only_if { node.run_list.include?("recipe[service1ad1d9fb-b1f2-4d9b-acfa-3f8a7caacda3::jar]") }
end

ruby_block "remove-deactivate-service1ad1d9fb-b1f2-4d9b-acfa-3f8a7caacda3" do
  block do
    node.run_list.remove("recipe[deactivate-service1ad1d9fb-b1f2-4d9b-acfa-3f8a7caacda3]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service1ad1d9fb-b1f2-4d9b-acfa-3f8a7caacda3]") }
end
