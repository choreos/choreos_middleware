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

ruby_block "disable-service88db6429-06fe-4238-b8df-f37fd0ba5e4f" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service88db6429-06fe-4238-b8df-f37fd0ba5e4f::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['88db6429-06fe-4238-b8df-f37fd0ba5e4f']['InstallationDir']}/service-88db6429-06fe-4238-b8df-f37fd0ba5e4f.jar]", :immediately
end

ruby_block "remove-service88db6429-06fe-4238-b8df-f37fd0ba5e4f" do
  block do
  	node.run_list.remove("recipe[service88db6429-06fe-4238-b8df-f37fd0ba5e4f::jar]")
  end
  only_if { node.run_list.include?("recipe[service88db6429-06fe-4238-b8df-f37fd0ba5e4f::jar]") }
end

ruby_block "remove-deactivate-service88db6429-06fe-4238-b8df-f37fd0ba5e4f" do
  block do
    node.run_list.remove("recipe[deactivate-service88db6429-06fe-4238-b8df-f37fd0ba5e4f]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service88db6429-06fe-4238-b8df-f37fd0ba5e4f]") }
end
