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

ruby_block "disable-serviceacd95818-d12e-414e-a462-58904283afba" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceacd95818-d12e-414e-a462-58904283afba::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['acd95818-d12e-414e-a462-58904283afba']['InstallationDir']}/service-acd95818-d12e-414e-a462-58904283afba.jar]", :immediately
end

ruby_block "remove-serviceacd95818-d12e-414e-a462-58904283afba" do
  block do
  	node.run_list.remove("recipe[serviceacd95818-d12e-414e-a462-58904283afba::jar]")
  end
  only_if { node.run_list.include?("recipe[serviceacd95818-d12e-414e-a462-58904283afba::jar]") }
end

ruby_block "remove-deactivate-serviceacd95818-d12e-414e-a462-58904283afba" do
  block do
    node.run_list.remove("recipe[deactivate-serviceacd95818-d12e-414e-a462-58904283afba]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceacd95818-d12e-414e-a462-58904283afba]") }
end
