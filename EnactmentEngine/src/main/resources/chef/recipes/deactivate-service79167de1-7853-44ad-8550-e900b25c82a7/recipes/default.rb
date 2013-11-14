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

#ruby_block "disable-service79167de1-7853-44ad-8550-e900b25c82a7" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service79167de1-7853-44ad-8550-e900b25c82a7::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['79167de1-7853-44ad-8550-e900b25c82a7']['InstallationDir']}/service-79167de1-7853-44ad-8550-e900b25c82a7.jar]", :immediately
#end

ruby_block "remove-service79167de1-7853-44ad-8550-e900b25c82a7" do
  block do
  	node.run_list.remove("recipe[service79167de1-7853-44ad-8550-e900b25c82a7::jar]")
  end
  only_if { node.run_list.include?("recipe[service79167de1-7853-44ad-8550-e900b25c82a7::jar]") }
end

ruby_block "remove-deactivate-service79167de1-7853-44ad-8550-e900b25c82a7" do
  block do
    node.run_list.remove("recipe[deactivate-service79167de1-7853-44ad-8550-e900b25c82a7]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service79167de1-7853-44ad-8550-e900b25c82a7]") }
end
