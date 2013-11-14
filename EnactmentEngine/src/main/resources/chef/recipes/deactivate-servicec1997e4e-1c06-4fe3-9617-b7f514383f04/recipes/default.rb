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

ruby_block "disable-servicec1997e4e-1c06-4fe3-9617-b7f514383f04" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicec1997e4e-1c06-4fe3-9617-b7f514383f04::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['c1997e4e-1c06-4fe3-9617-b7f514383f04']['InstallationDir']}/service-c1997e4e-1c06-4fe3-9617-b7f514383f04.jar]", :immediately
end

ruby_block "remove-servicec1997e4e-1c06-4fe3-9617-b7f514383f04" do
  block do
  	node.run_list.remove("recipe[servicec1997e4e-1c06-4fe3-9617-b7f514383f04::jar]")
  end
  only_if { node.run_list.include?("recipe[servicec1997e4e-1c06-4fe3-9617-b7f514383f04::jar]") }
end

ruby_block "remove-deactivate-servicec1997e4e-1c06-4fe3-9617-b7f514383f04" do
  block do
    node.run_list.remove("recipe[deactivate-servicec1997e4e-1c06-4fe3-9617-b7f514383f04]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicec1997e4e-1c06-4fe3-9617-b7f514383f04]") }
end
