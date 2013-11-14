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

#ruby_block "disable-service17fd9ae2-0e1d-47eb-acc6-1ecafbe4eb90" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service17fd9ae2-0e1d-47eb-acc6-1ecafbe4eb90::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['17fd9ae2-0e1d-47eb-acc6-1ecafbe4eb90']['InstallationDir']}/service-17fd9ae2-0e1d-47eb-acc6-1ecafbe4eb90.jar]", :immediately
#end

ruby_block "remove-service17fd9ae2-0e1d-47eb-acc6-1ecafbe4eb90" do
  block do
  	node.run_list.remove("recipe[service17fd9ae2-0e1d-47eb-acc6-1ecafbe4eb90::jar]")
  end
  only_if { node.run_list.include?("recipe[service17fd9ae2-0e1d-47eb-acc6-1ecafbe4eb90::jar]") }
end

ruby_block "remove-deactivate-service17fd9ae2-0e1d-47eb-acc6-1ecafbe4eb90" do
  block do
    node.run_list.remove("recipe[deactivate-service17fd9ae2-0e1d-47eb-acc6-1ecafbe4eb90]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service17fd9ae2-0e1d-47eb-acc6-1ecafbe4eb90]") }
end
