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

#ruby_block "disable-servicec40b2938-5d50-48a0-b92a-53e349d38499" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[servicec40b2938-5d50-48a0-b92a-53e349d38499::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['c40b2938-5d50-48a0-b92a-53e349d38499']['InstallationDir']}/service-c40b2938-5d50-48a0-b92a-53e349d38499.jar]", :immediately
#end

ruby_block "remove-servicec40b2938-5d50-48a0-b92a-53e349d38499" do
  block do
  	node.run_list.remove("recipe[servicec40b2938-5d50-48a0-b92a-53e349d38499::jar]")
  end
  only_if { node.run_list.include?("recipe[servicec40b2938-5d50-48a0-b92a-53e349d38499::jar]") }
end

ruby_block "remove-deactivate-servicec40b2938-5d50-48a0-b92a-53e349d38499" do
  block do
    node.run_list.remove("recipe[deactivate-servicec40b2938-5d50-48a0-b92a-53e349d38499]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicec40b2938-5d50-48a0-b92a-53e349d38499]") }
end
