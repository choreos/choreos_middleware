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

ruby_block "disable-service3ce54e12-eded-42e7-a489-d4aa941beb95" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service3ce54e12-eded-42e7-a489-d4aa941beb95::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['3ce54e12-eded-42e7-a489-d4aa941beb95']['InstallationDir']}/service-3ce54e12-eded-42e7-a489-d4aa941beb95.jar]", :immediately
end

ruby_block "remove-service3ce54e12-eded-42e7-a489-d4aa941beb95" do
  block do
  	node.run_list.remove("recipe[service3ce54e12-eded-42e7-a489-d4aa941beb95::jar]")
  end
  only_if { node.run_list.include?("recipe[service3ce54e12-eded-42e7-a489-d4aa941beb95::jar]") }
end

ruby_block "remove-deactivate-service3ce54e12-eded-42e7-a489-d4aa941beb95" do
  block do
    node.run_list.remove("recipe[deactivate-service3ce54e12-eded-42e7-a489-d4aa941beb95]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service3ce54e12-eded-42e7-a489-d4aa941beb95]") }
end
