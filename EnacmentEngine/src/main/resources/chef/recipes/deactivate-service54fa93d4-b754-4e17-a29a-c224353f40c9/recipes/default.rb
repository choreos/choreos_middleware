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

#ruby_block "disable-service54fa93d4-b754-4e17-a29a-c224353f40c9" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service54fa93d4-b754-4e17-a29a-c224353f40c9::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['54fa93d4-b754-4e17-a29a-c224353f40c9']['InstallationDir']}/service-54fa93d4-b754-4e17-a29a-c224353f40c9.jar]", :immediately
#end

ruby_block "remove-service54fa93d4-b754-4e17-a29a-c224353f40c9" do
  block do
  	node.run_list.remove("recipe[service54fa93d4-b754-4e17-a29a-c224353f40c9::jar]")
  end
  only_if { node.run_list.include?("recipe[service54fa93d4-b754-4e17-a29a-c224353f40c9::jar]") }
end

ruby_block "remove-deactivate-service54fa93d4-b754-4e17-a29a-c224353f40c9" do
  block do
    node.run_list.remove("recipe[deactivate-service54fa93d4-b754-4e17-a29a-c224353f40c9]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service54fa93d4-b754-4e17-a29a-c224353f40c9]") }
end
