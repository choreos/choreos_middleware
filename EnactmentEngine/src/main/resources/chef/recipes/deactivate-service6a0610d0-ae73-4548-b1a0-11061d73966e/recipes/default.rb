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

#ruby_block "disable-service6a0610d0-ae73-4548-b1a0-11061d73966e" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service6a0610d0-ae73-4548-b1a0-11061d73966e::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['6a0610d0-ae73-4548-b1a0-11061d73966e']['InstallationDir']}/service-6a0610d0-ae73-4548-b1a0-11061d73966e.jar]", :immediately
#end

ruby_block "remove-service6a0610d0-ae73-4548-b1a0-11061d73966e" do
  block do
  	node.run_list.remove("recipe[service6a0610d0-ae73-4548-b1a0-11061d73966e::jar]")
  end
  only_if { node.run_list.include?("recipe[service6a0610d0-ae73-4548-b1a0-11061d73966e::jar]") }
end

ruby_block "remove-deactivate-service6a0610d0-ae73-4548-b1a0-11061d73966e" do
  block do
    node.run_list.remove("recipe[deactivate-service6a0610d0-ae73-4548-b1a0-11061d73966e]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service6a0610d0-ae73-4548-b1a0-11061d73966e]") }
end
