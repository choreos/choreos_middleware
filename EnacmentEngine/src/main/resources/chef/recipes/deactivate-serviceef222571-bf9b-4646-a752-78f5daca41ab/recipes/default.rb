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

ruby_block "disable-serviceef222571-bf9b-4646-a752-78f5daca41ab" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceef222571-bf9b-4646-a752-78f5daca41ab::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['ef222571-bf9b-4646-a752-78f5daca41ab']['InstallationDir']}/service-ef222571-bf9b-4646-a752-78f5daca41ab.jar]", :immediately
end

ruby_block "remove-serviceef222571-bf9b-4646-a752-78f5daca41ab" do
  block do
  	node.run_list.remove("recipe[serviceef222571-bf9b-4646-a752-78f5daca41ab::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceef222571-bf9b-4646-a752-78f5daca41ab::jar]") }
end

ruby_block "remove-deactivate-serviceef222571-bf9b-4646-a752-78f5daca41ab" do
  block do
    node.run_list.remove("recipe[deactivate-serviceef222571-bf9b-4646-a752-78f5daca41ab]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceef222571-bf9b-4646-a752-78f5daca41ab]") }
end
